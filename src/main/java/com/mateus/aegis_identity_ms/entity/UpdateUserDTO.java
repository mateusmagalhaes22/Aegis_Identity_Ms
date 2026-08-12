package com.mateus.aegis_identity_ms.entity;

import java.math.BigDecimal;

public record UpdateUserDTO (
    String name,
    String email,
    String password,
    String cpf,
    AccountStatus status,
    BigDecimal balance
) {}
