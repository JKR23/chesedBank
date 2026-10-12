package com.chesedbank.account.dto.search;

import com.chesedbank.account.entity.AccountType;
import com.chesedbank.account.entity.Currency;
import com.chesedbank.account.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountSearchCriteria(
        UUID publicIdAccount,
        UUID publicIdCustomer,
        String accountNumber,
        AccountType accountType,
        Currency currency,
        BigDecimal balance,
        Status status,
        BigDecimal minBalance,
        BigDecimal maxBalance,
        LocalDateTime createdAfter,
        LocalDateTime createdBefore
) {
}
