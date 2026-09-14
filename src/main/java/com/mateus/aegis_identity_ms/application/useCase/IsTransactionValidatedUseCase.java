package com.mateus.aegis_identity_ms.application.useCase;

import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.repository.ValidatedTransactionRepository;

@Service
public class IsTransactionValidatedUseCase {

    private final ValidatedTransactionRepository validatedTransactionRepository;

    public IsTransactionValidatedUseCase(com.mateus.aegis_identity_ms.domain.repository.ValidatedTransactionRepository validatedTransactionRepository) {
        this.validatedTransactionRepository = validatedTransactionRepository;
    }
    
    public boolean IsTransactionValidated(String transactionId) {
        return validatedTransactionRepository.existsById(transactionId);
    }
}
