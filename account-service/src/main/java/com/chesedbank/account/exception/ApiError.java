package com.chesedbank.account.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,

        int status,

        String error,

        String message,

        //fieldName and errorMessage, for example: "accountNumber" : "must not be null"
        Map<String, String> fields
) {
}
