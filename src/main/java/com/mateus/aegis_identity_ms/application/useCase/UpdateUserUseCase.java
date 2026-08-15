package com.mateus.aegis_identity_ms.application.useCase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mateus.aegis_identity_ms.domain.model.Account;
import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.domain.repository.UserRepository;
import com.mateus.aegis_identity_ms.infrastructure.persistence.UserRepositoryImplementation;
import com.mateus.aegis_identity_ms.presentation.dto.UpdateUserDTO;

@Service
@Transactional
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCase(UserRepositoryImplementation userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User update(UpdateUserDTO userDto, UUID id) {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setId(id);
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setCpf(userDto.cpf());
        user.setAccount(new Account(userDto.balance(), userDto.status(), user.getAccount() != null ? user.getAccount().getCreatedAt() : now));
        user.setUpdatedAt(now);
        return userRepository.save(user);
    }

}
