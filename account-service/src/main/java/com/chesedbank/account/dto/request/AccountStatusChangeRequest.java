package com.chesedbank.account.dto.request;

import com.chesedbank.account.entity.Status;
import jakarta.validation.constraints.NotNull;

public record AccountStatusChangeRequest(
        @NotNull Status newStatus
) {
}
