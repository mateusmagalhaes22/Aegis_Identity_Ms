package com.mateus.aegis_identity_ms.domain.repository;

import com.mateus.aegis_identity_ms.domain.model.ValidatedTransaction;

public interface ValidatedTransactionRepository {
    boolean existsById(String id);

    void save(ValidatedTransaction validatedTransaction);
}
