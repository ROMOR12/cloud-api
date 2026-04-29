package com.roberto.cloud_api.presentation.controller;

import com.roberto.cloud_api.application.dto.request.AuthRequest;
import com.roberto.cloud_api.application.dto.response.AuthResponse;
import com.roberto.cloud_api.application.facade.AuthFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the main door to our API.
 * It handles user registration and login.
 * When someone logs in successfully, it hands them a JWT token so they can access the rest of the app safely.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthFacade authFacade;

    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authFacade.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authFacade.login(request));
    }
}
