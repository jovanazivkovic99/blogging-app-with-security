package com.example.blogJovana.params;

import com.example.blogJovana.model.User;
import com.example.blogJovana.request.LoginRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.RegistrationResponse;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class AuthenticationServiceTestParams {

    public static Stream<Arguments> registerParameters() {
        RegistrationRequest request = new RegistrationRequest("John", "Doe", "john.doe@example.com", "password");
        RegistrationResponse expectedResponse = new RegistrationResponse("John", "Doe", "john.doe@example.com");
        User user = new User();
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(request.password());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(User.Role.ROLE_USER);
        return Stream.of(Arguments.of(request, expectedResponse, user));
    }

    public static Stream<Arguments> registerAdminParameters() {
        RegistrationRequest request = new RegistrationRequest("Admin", "User", "admin@example.com", "adminPassword");
        RegistrationResponse expectedResponse = new RegistrationResponse("Admin", "User", "admin@example.com");
        User user = new User();
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(request.password());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(User.Role.ROLE_ADMIN);
        return Stream.of(Arguments.of(request, expectedResponse, user));
    }

    public static Stream<Arguments> loginParameters() {
        LoginRequest request = new LoginRequest("john.doe@example.com", "password");
        User user = new User();
        user.setEmail(request.email());
        user.setFirstName(request.password());
        String expectedJwt = "token";
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(),
                request.password());
        return Stream.of(Arguments.of(request, expectedJwt, user, authenticationToken));
    }
}
