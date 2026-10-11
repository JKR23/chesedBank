package com.chesedbank.account.dto;

import com.chesedbank.account.entity.AccountType;
import com.chesedbank.account.entity.Currency;
import com.chesedbank.account.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID publicIdAccount,
        UUID publicIdCustomer,
        String accountNumber,
        AccountType accountType,
        Currency currency,
        BigDecimal balance,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
