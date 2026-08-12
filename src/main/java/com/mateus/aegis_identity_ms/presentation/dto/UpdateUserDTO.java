package com.mateus.aegis_identity_ms.presentation.dto;

import java.math.BigDecimal;

import com.mateus.aegis_identity_ms.domain.model.AccountStatus;

public record UpdateUserDTO (
    String name,
    String email,
    String password,
    String cpf,
    AccountStatus status,
    BigDecimal balance
) {}
