package com.mateus.aegis_identity_ms.presentation.dto;

public record CreateUserDTO (
    String name,
    String email,
    String password,
    String cpf
) {}
