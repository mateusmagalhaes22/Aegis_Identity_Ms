package com.mateus.aegis_identity_ms.infrastructure.messaging;

public record TransactionValidationDto(String status, String message, String transactionId) {
}
