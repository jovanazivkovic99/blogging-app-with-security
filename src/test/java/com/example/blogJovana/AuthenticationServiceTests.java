package com.example.blogJovana;

import com.example.blogJovana.exceptions.UserAlreadyExistsException;
import com.example.blogJovana.mapper.UserMapper;
import com.example.blogJovana.model.TokenBlacklist;
import com.example.blogJovana.model.User;
import com.example.blogJovana.repository.TokenBlacklistRepository;
import com.example.blogJovana.repository.UserRepository;
import com.example.blogJovana.request.LoginRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.AuthenticationResponse;
import com.example.blogJovana.response.RegistrationResponse;
import com.example.blogJovana.service.impl.AuthenticationServiceImpl;
import com.example.blogJovana.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtServiceImpl jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private TokenBlacklistRepository tokenBlacklistRepository;

    @ParameterizedTest
    @MethodSource("com.example.blogJovana.params.AuthenticationServiceTestParams#registerParameters")
    void register_ok(RegistrationRequest request, RegistrationResponse expectedResponse, User expectedUser) {
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toUser(request)).thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(jwtService.generateToken(expectedUser)).thenReturn("token");
        when(userMapper.toRegistrationResponse(expectedUser)).thenReturn(expectedResponse);


        RegistrationResponse actualResponse = authenticationService.register(request);

        assertEquals(expectedResponse.firstName(), actualResponse.firstName());
        assertEquals(expectedResponse.lastName(), actualResponse.lastName());
        assertEquals(expectedResponse.email(), actualResponse.email());
        assertEquals(User.Role.ROLE_USER, expectedUser.getRole());
        verify(userRepository, times(1)).save(expectedUser);
    }

    @ParameterizedTest
    @MethodSource("com.example.blogJovana.params.AuthenticationServiceTestParams#loginParameters")
    void login_ok(LoginRequest request, String expectedJwt, User user, UsernamePasswordAuthenticationToken authenticationToken) {
        when(userDetailsService.loadUserByUsername(request.email())).thenReturn(user);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(mock(Authentication.class));
        when(jwtService.generateToken(user)).thenReturn(expectedJwt);

        AuthenticationResponse actualResponse = authenticationService.login(request);

        assertEquals(expectedJwt, actualResponse.token());
    }

    @ParameterizedTest
    @MethodSource("com.example.blogJovana.params.AuthenticationServiceTestParams#registerAdminParameters")
    void registerAdmin_ok(RegistrationRequest request, RegistrationResponse expectedResponse, User expectedUser) {
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toUser(request)).thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(jwtService.generateToken(expectedUser)).thenReturn("token");
        when(userMapper.toRegistrationResponse(expectedUser)).thenReturn(expectedResponse);


        RegistrationResponse actualResponse = authenticationService.registerAdmin(request);

        assertEquals(expectedResponse.firstName(), actualResponse.firstName());
        assertEquals(expectedResponse.lastName(), actualResponse.lastName());
        assertEquals(expectedResponse.email(), actualResponse.email());
        assertEquals(User.Role.ROLE_ADMIN, expectedUser.getRole());
    }

    @Test
    void logout_ok() {
        String token = "jwtToken";
        authenticationService.logout(token);
        verify(tokenBlacklistRepository).save(argThat(blacklistedToken -> token.equals(blacklistedToken.getToken())));
    }

    @Test
    void registerAdmin_whenUserAlreadyExists_thenThrowException() {
        RegistrationRequest request = new RegistrationRequest("Jane", "Doe", "jane.doe@example.com", "adminPass");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.registerAdmin(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_whenUserAlreadyExists_thenThrowException() {
        RegistrationRequest request = new RegistrationRequest("Jane", "Doe", "jane.doe@example.com", "adminPass");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(request));

        verify(userRepository, never()).save(any(User.class));
    }

}
