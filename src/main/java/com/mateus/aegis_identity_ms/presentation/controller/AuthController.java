package com.mateus.aegis_identity_ms.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mateus.aegis_identity_ms.application.useCase.CreateUserUseCase;
import com.mateus.aegis_identity_ms.application.useCase.LoginUseCase;
import com.mateus.aegis_identity_ms.presentation.dto.CreateUserDTO;
import com.mateus.aegis_identity_ms.presentation.dto.CreateUserResponseDto;
import com.mateus.aegis_identity_ms.presentation.dto.LoginRequestDto;
import com.mateus.aegis_identity_ms.presentation.dto.LoginResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    private final CreateUserUseCase createUserUseCase;

    public AuthController(LoginUseCase loginUseCase, CreateUserUseCase createUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDTO user) {
        try {
            CreateUserResponseDto response = createUserUseCase.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        try {
            LoginResponseDto response = loginUseCase.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}