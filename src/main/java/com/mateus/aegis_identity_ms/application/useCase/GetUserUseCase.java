package com.mateus.aegis_identity_ms.application.useCase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.infrastructure.persistence.UserRepositoryImplementation;

@Service
public class GetUserUseCase {
    
    private final UserRepositoryImplementation userRepository;

    public GetUserUseCase(UserRepositoryImplementation userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
