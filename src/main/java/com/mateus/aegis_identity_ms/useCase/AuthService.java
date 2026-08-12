package com.mateus.aegis_identity_ms.useCase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.entity.LoginRequestDto;
import com.mateus.aegis_identity_ms.entity.LoginResponseDto;
import com.mateus.aegis_identity_ms.entity.User;
import com.mateus.aegis_identity_ms.infrastructure.UserRepository;
import com.mateus.aegis_identity_ms.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
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
