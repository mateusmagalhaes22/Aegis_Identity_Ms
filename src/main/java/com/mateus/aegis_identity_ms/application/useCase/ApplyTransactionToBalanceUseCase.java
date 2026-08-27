package com.mateus.aegis_identity_ms.application.useCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mateus.aegis_identity_ms.domain.model.Account;
import com.mateus.aegis_identity_ms.domain.model.User;
import com.mateus.aegis_identity_ms.domain.repository.UserRepository;

@Service
public class ApplyTransactionToBalanceUseCase {

    private final UserRepository userRepository;

    public ApplyTransactionToBalanceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void apply(UUID userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Account account = user.getAccount();
        if (account == null) {
            throw new IllegalStateException("User has no account: " + userId);
        }

        BigDecimal currentBalance = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
        account.setBalance(currentBalance.add(amount));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}