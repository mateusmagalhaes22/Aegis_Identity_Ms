package com.mateus.aegis_identity_ms.entity;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
