package com.example.blogJovana.service.impl;

import com.example.blogJovana.mapper.UserMapper;
import com.example.blogJovana.repository.UserRepository;
import com.example.blogJovana.request.AuthenticationRequest;
import com.example.blogJovana.request.RegistrationRequest;
import com.example.blogJovana.response.AuthenticationResponse;
import com.example.blogJovana.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) {
        var user = userMapper.toUser(request);

        var savedUser = userRepository.save(user);
        var jwt = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(jwt);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        user = userMapper.toUser(request);
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }
}
