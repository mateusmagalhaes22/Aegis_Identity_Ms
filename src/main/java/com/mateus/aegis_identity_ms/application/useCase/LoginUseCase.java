package com.mateus.aegis_identity_ms.application.useCase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.infrastructure.persistence.UserRepositoryImplementation;
import com.mateus.aegis_identity_ms.infrastructure.security.JwtUtil;
import com.mateus.aegis_identity_ms.presentation.dto.LoginRequestDto;
import com.mateus.aegis_identity_ms.presentation.dto.LoginResponseDto;

@Service
public class LoginUseCase {

    private final UserRepositoryImplementation userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(UserRepositoryImplementation userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponseDto(token);
    }

}
