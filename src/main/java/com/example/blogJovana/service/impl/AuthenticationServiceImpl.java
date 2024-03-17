package com.example.blogJovana.service.impl;

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
import com.example.blogJovana.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        if (isUserRegistered(request.email()))
            throw new UserAlreadyExistsException(request.email());
        var user = userMapper.toUser(request);
        user.setRole(User.Role.ROLE_USER);

        var savedUser = userRepository.save(user);
        jwtService.generateToken(savedUser);
        return userMapper.toRegistrationResponse(savedUser);
    }

    @Override
    public RegistrationResponse registerAdmin(RegistrationRequest request) {
        if (isUserRegistered(request.email()))
            throw new UserAlreadyExistsException(request.email());
        var adminUser = userMapper.toUser(request);
        adminUser.setRole(User.Role.ROLE_ADMIN);

        var savedAdminUser = userRepository.save(adminUser);
        jwtService.generateToken(savedAdminUser);
        return userMapper.toRegistrationResponse(savedAdminUser);
    }

    private boolean isUserRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String jwt = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }

    @Override
    public void logout(String token) {
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        tokenBlacklistRepository.save(blacklistedToken);
    }
}
