package com.example.blogJovana.service;

import com.example.blogJovana.request.AuthenticationRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
