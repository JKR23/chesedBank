package com.chesedbank.account.dto;

import com.chesedbank.account.entity.AccountType;
import com.chesedbank.account.entity.Currency;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AccountCreateRequest(
        @NotNull(message = "publicIdCustomer is required")
        UUID publicIdCustomer,

        @NotNull(message = "accountType is required")
        AccountType accountType,

        @NotNull(message = "currency is required")
        Currency currency
) {
}
