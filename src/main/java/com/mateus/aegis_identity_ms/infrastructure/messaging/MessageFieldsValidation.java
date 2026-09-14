package com.mateus.aegis_identity_ms.infrastructure.messaging;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class MessageFieldsValidation {
    public static String requiredText(JsonNode transaction, String field) {
        JsonNode value = transaction.get(field);
        if (value == null || !value.isTextual() || value.asText().isBlank()) {
            throw new IllegalArgumentException("Transaction field '" + field + "' is required");
        }
        return value.asText();
    }

    public static BigDecimal requiredDecimal(JsonNode transaction, String field) {
        JsonNode value = transaction.get(field);
        if (value == null || !value.isNumber()) {
            throw new IllegalArgumentException("Transaction field '" + field + "' is required");
        }
        return value.decimalValue();
    }
}
