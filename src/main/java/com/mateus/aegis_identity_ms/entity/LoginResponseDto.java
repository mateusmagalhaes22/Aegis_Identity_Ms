package com.mateus.aegis_identity_ms.entity;

import lombok.Data;

@Data
public class LoginResponseDto {
    
    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
