package com.roberto.cloud_api.application.usecase.auth;

import com.roberto.cloud_api.application.dto.request.AuthRequest;
import com.roberto.cloud_api.application.dto.response.AuthResponse;
import com.roberto.cloud_api.domain.exception.ResourceNotFoundException;
import com.roberto.cloud_api.infrastructure.repository.UserRepository;
import com.roberto.cloud_api.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginUseCase(UserRepository userRepository,
                        AuthenticationManager authenticationManager,
                        JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUsername()));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}

