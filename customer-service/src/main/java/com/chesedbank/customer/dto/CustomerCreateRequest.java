package com.chesedbank.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequest(
        @NotBlank(message = "firstname should not be empty")
        @Size(max = 50, message = "firstname should not be over 50 characters")
        String firstName,

        @NotBlank(message = "lastname should not be empty")
        @Size(max = 50, message = "lastname should not be over 50 characters")
        String lastName,

        @NotBlank(message = "email should not be empty")
        @Email(message = "email is invalid")
        @Size(max = 50, message = "email should not be over 50 characters")
        String email,

        @Size(max = 15, message = "phone should not have more than 15 characters ")
        String phone
){}
