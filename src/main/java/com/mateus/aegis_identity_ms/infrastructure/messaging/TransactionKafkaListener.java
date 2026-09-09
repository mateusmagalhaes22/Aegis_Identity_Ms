package com.mateus.aegis_identity_ms.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateus.aegis_identity_ms.application.useCase.ApplyTransactionToBalanceUseCase;

@Component
public class TransactionKafkaListener {

    private final ObjectMapper objectMapper;
    private final ApplyTransactionToBalanceUseCase applyTransactionToBalance;
    private final KafkaTemplate<String, TransactionValidationDto> kafkaTemplate;
    private final String validationTopic;

    public TransactionKafkaListener(
            ObjectMapper objectMapper,
            ApplyTransactionToBalanceUseCase applyTransactionToBalance,
            KafkaTemplate<String, TransactionValidationDto> kafkaTemplate,
            @Value("${app.kafka.transaction-validation-topic:transaction-validation}") String validationTopic) {
        this.objectMapper = objectMapper;
        this.applyTransactionToBalance = applyTransactionToBalance;
        this.kafkaTemplate = kafkaTemplate;
        this.validationTopic = validationTopic;
    }

    @KafkaListener(topics = "${app.kafka.transactions-topic:transactions}")
    public void consume(String message) throws Exception {
        System.out.println("Received message: " + message);
        JsonNode transaction = objectMapper.readTree(message);
        String transactionId = requiredText(transaction, "id");
        UUID userId = UUID.fromString(requiredText(transaction, "userId"));
        BigDecimal amount = requiredDecimal(transaction, "amount");
        try {
            applyTransactionToBalance.apply(userId, amount);
            System.out.println("Transaction applied successfully for transactionId: " + transactionId);
            kafkaTemplate.send(validationTopic,
                new TransactionValidationDto("success", "Transaction applied successfully", transactionId));
        } catch (Exception e) {
            System.out.println("Failed to apply transaction for transactionId: " + transactionId + ". Error: " + e.getMessage());
            kafkaTemplate.send(validationTopic,
                new TransactionValidationDto("failure", e.getMessage(), transactionId));
        }
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