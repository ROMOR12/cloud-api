package com.roberto.cloud_api.application.facade;

import com.roberto.cloud_api.application.dto.request.AuthRequest;
import com.roberto.cloud_api.application.dto.response.AuthResponse;
import com.roberto.cloud_api.application.usecase.auth.LoginUseCase;
import com.roberto.cloud_api.application.usecase.auth.RegisterUseCase;
import org.springframework.stereotype.Component;

@Component
public class AuthFacade {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public AuthFacade(LoginUseCase loginUseCase, RegisterUseCase registerUseCase){
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    public AuthResponse register(AuthRequest request){
        return registerUseCase.execute(request);
    }

    public AuthResponse login(AuthRequest request){
        return loginUseCase.execute(request);
    }
}
