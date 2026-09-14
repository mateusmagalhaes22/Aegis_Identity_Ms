package com.mateus.aegis_identity_ms.domain.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record ValidatedTransaction(
    @Id
    String id,
    Date validatedAt
) {
}
