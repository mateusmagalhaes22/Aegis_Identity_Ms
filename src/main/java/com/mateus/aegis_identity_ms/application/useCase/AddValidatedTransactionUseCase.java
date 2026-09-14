package com.mateus.aegis_identity_ms.application.useCase;

import org.springframework.stereotype.Service;

import com.mateus.aegis_identity_ms.domain.model.ValidatedTransaction;
import com.mateus.aegis_identity_ms.domain.repository.ValidatedTransactionRepository;

@Service
public class AddValidatedTransactionUseCase {
    private final ValidatedTransactionRepository validatedTransactionRepository;

    public AddValidatedTransactionUseCase(ValidatedTransactionRepository validatedTransactionRepository) {
        this.validatedTransactionRepository = validatedTransactionRepository;
    }
    
    public void add(String transactionId) {
        if (!validatedTransactionRepository.existsById(transactionId)) {
            validatedTransactionRepository.save(new ValidatedTransaction(transactionId));
        }
    }
}
