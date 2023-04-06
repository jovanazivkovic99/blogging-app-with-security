package com.example.blogJovana.service;

import com.example.blogJovana.request.LoginRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.AuthenticationResponse;
import com.example.blogJovana.response.RegistrationResponse;

public interface AuthenticationService {
    RegistrationResponse register(RegistrationRequest request);

    RegistrationResponse registerAdmin(RegistrationRequest request);

    AuthenticationResponse login(LoginRequest request);

    void logout(String token);
}
