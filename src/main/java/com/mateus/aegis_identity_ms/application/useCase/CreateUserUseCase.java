package com.mateus.aegis_identity_ms.application.useCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.model.AccountStatus;
import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.infrastructure.persistence.UserRepositoryImplementation;
import com.mateus.aegis_identity_ms.presentation.dto.CreateUserDTO;
import com.mateus.aegis_identity_ms.presentation.dto.CreateUserResponseDto;

@Service
public class CreateUserUseCase {

    private final UserRepositoryImplementation userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepositoryImplementation userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponseDto create(CreateUserDTO userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new RuntimeException("Email already registered.");
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(AccountStatus.ACTIVE);
        user.setCreatedAt(now);
        userRepository.save(user);

        return new CreateUserResponseDto(user.getName(), user.getEmail());
    }

    public User save(CreateUserDTO userDto) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setCreatedAt(now);
        return userRepository.save(user);
    }
}
