package com.mateus.aegis_identity_ms.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Account {

    public Account(BigDecimal balance, AccountStatus status, LocalDateTime createdAt) {
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    private BigDecimal balance;

    private AccountStatus status;

    private LocalDateTime createdAt;
    
}
