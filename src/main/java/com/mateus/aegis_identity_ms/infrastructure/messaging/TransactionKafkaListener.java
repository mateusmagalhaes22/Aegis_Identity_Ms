package com.mateus.aegis_identity_ms.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateus.aegis_identity_ms.application.useCase.AddValidatedTransactionUseCase;
import com.mateus.aegis_identity_ms.application.useCase.ApplyTransactionToBalanceUseCase;
import com.mateus.aegis_identity_ms.application.useCase.IsTransactionValidatedUseCase;

@Component
public class TransactionKafkaListener {

    private final ObjectMapper objectMapper;
    private final ApplyTransactionToBalanceUseCase applyTransactionToBalance;
    private final IsTransactionValidatedUseCase isTransactionValidatedUseCase;
    private final AddValidatedTransactionUseCase addValidatedTransactionUseCase;
    private final KafkaTemplate<String, TransactionValidationDto> kafkaTemplate;
    private final String validationTopic;

    public TransactionKafkaListener(
            ObjectMapper objectMapper,
            ApplyTransactionToBalanceUseCase applyTransactionToBalance,
            IsTransactionValidatedUseCase isTransactionValidatedUseCase,
            AddValidatedTransactionUseCase addValidatedTransactionUseCase,
            KafkaTemplate<String, TransactionValidationDto> kafkaTemplate,
            @Value("${app.kafka.transaction-validation-topic:transaction-validation}") String validationTopic) {
        this.objectMapper = objectMapper;
        this.applyTransactionToBalance = applyTransactionToBalance;
        this.kafkaTemplate = kafkaTemplate;
        this.validationTopic = validationTopic;
        this.isTransactionValidatedUseCase = isTransactionValidatedUseCase;
        this.addValidatedTransactionUseCase = addValidatedTransactionUseCase;
    }

    @KafkaListener(topics = "${app.kafka.transaction-fraud-analysis-topic:transaction-fraud-analysis}")
    public void consume(String message) throws Exception {
        JsonNode transaction = objectMapper.readTree(message);
        String transactionId = MessageFieldsValidation.requiredText(transaction, "id");
        UUID userId = UUID.fromString(MessageFieldsValidation.requiredText(transaction, "userId"));
        BigDecimal amount = MessageFieldsValidation.requiredDecimal(transaction, "amount");
        String fraudStatus = MessageFieldsValidation.requiredText(transaction, "status");

        if (isTransactionValidatedUseCase.IsTransactionValidated(transactionId)) {
            kafkaTemplate.send(validationTopic,
                new TransactionValidationDto("failure", "Transaction already validated", transactionId));
        } else if ("REJECTED".equals(fraudStatus)) {
            kafkaTemplate.send(validationTopic,
                new TransactionValidationDto("failure", "Fraudulent transaction detected", transactionId));
        } else {
            addValidatedTransactionUseCase.add(transactionId);
            try {
                applyTransactionToBalance.apply(userId, amount);
                kafkaTemplate.send(validationTopic,
                    new TransactionValidationDto("success", "Transaction applied successfully", transactionId));
            } catch (Exception e) {
                kafkaTemplate.send(validationTopic,
                    new TransactionValidationDto("failure", e.getMessage(), transactionId));
            }
        }
    }
}