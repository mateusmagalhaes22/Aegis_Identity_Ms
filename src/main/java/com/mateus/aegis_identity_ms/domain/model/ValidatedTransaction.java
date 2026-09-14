package com.mateus.aegis_identity_ms.domain.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ValidatedTransaction {
    @Id
    private String id;
    private Date validatedAt;

    public ValidatedTransaction(String id) {
        this.id = id;
        this.validatedAt = new Date();
    }
}
