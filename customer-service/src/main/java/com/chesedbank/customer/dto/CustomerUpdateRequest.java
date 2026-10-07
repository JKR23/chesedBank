package com.chesedbank.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerUpdateRequest(
        @NotBlank(message = "firstname should not be empty")
        @Size(max = 50, message = "firstname should not be over 50 characters")
        String firstName,

        @NotBlank(message = "lastname should not be empty")
        @Size(max = 50, message = "lastname should not be over 50 characters")
        String lastName,

        @Size(max = 15, message = "phone should not have more than 15 characters ")
        String phone
) {
}
