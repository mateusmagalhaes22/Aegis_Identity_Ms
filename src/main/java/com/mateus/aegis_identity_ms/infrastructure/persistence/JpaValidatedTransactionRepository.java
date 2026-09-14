package com.mateus.aegis_identity_ms.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mateus.aegis_identity_ms.domain.model.ValidatedTransaction;

public interface JpaValidatedTransactionRepository extends JpaRepository<ValidatedTransaction, String> {
}
