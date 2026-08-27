package com.mateus.aegis_identity_ms.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateus.aegis_identity_ms.application.useCase.ApplyTransactionToBalanceUseCase;

@Component
public class TransactionKafkaListener {

    private final ObjectMapper objectMapper;
    private final ApplyTransactionToBalanceUseCase applyTransactionToBalance;

    public TransactionKafkaListener(
            ObjectMapper objectMapper,
            ApplyTransactionToBalanceUseCase applyTransactionToBalance) {
        this.objectMapper = objectMapper;
        this.applyTransactionToBalance = applyTransactionToBalance;
    }

    @KafkaListener(topics = "${app.kafka.transactions-topic:transactions}")
    public void consume(String message) throws Exception {
        JsonNode transaction = objectMapper.readTree(message);
        UUID userId = UUID.fromString(requiredText(transaction, "userId"));
        BigDecimal amount = requiredDecimal(transaction, "amount");
        applyTransactionToBalance.apply(userId, amount);
    }

    private String requiredText(JsonNode transaction, String field) {
        JsonNode value = transaction.get(field);
        if (value == null || !value.isTextual() || value.asText().isBlank()) {
            throw new IllegalArgumentException("Transaction field '" + field + "' is required");
        }
        return value.asText();
    }

    private BigDecimal requiredDecimal(JsonNode transaction, String field) {
        JsonNode value = transaction.get(field);
        if (value == null || !value.isNumber()) {
            throw new IllegalArgumentException("Transaction field '" + field + "' is required");
        }
        return value.decimalValue();
    }
}