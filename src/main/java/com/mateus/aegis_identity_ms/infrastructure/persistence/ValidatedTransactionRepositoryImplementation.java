package com.mateus.aegis_identity_ms.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.mateus.aegis_identity_ms.domain.repository.ValidatedTransactionRepository;

@Repository
public class ValidatedTransactionRepositoryImplementation implements ValidatedTransactionRepository {
    private final JpaValidatedTransactionRepository jpaValidatedTransactionRepository;

    public ValidatedTransactionRepositoryImplementation(JpaValidatedTransactionRepository jpaValidatedTransactionRepository) {
        this.jpaValidatedTransactionRepository = jpaValidatedTransactionRepository;
    }

    @Override
    public boolean existsById(String id) {
        return jpaValidatedTransactionRepository.existsById(id);
    }

    @Override
    public void save(com.mateus.aegis_identity_ms.domain.model.ValidatedTransaction validatedTransaction) {
        jpaValidatedTransactionRepository.save(validatedTransaction);
    }
    
}
