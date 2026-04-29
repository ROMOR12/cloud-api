package com.roberto.cloud_api.application.usecase.auth;

import com.roberto.cloud_api.application.dto.request.AuthRequest;
import com.roberto.cloud_api.application.dto.response.AuthResponse;
import com.roberto.cloud_api.domain.exception.UserAlreadyExistsException;
import com.roberto.cloud_api.domain.model.User;
import com.roberto.cloud_api.infrastructure.repository.UserRepository;
import com.roberto.cloud_api.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterUseCase(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Error: Username '" + request.getUsername() + "' is already in use.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
