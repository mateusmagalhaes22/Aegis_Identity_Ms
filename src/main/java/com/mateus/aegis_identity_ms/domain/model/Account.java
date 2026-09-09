package com.mateus.aegis_identity_ms.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Account {

    public Account(BigDecimal balance, AccountStatus status, LocalDateTime createdAt) {
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    private BigDecimal balance;

    private AccountStatus status;

    private LocalDateTime createdAt;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal add) {
        this.balance = add;
    }
    
}
