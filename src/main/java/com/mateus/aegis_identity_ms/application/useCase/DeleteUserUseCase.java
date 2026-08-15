package com.mateus.aegis_identity_ms.application.useCase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.repository.UserRepository;
import com.mateus.aegis_identity_ms.infrastructure.persistence.UserRepositoryImplementation;

@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepositoryImplementation userRepository) {
        this.userRepository = userRepository;
    }
    
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
