package com.chesedbank.customer.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID publicIdCustomer,
        String firstName,
        String lastName,
        String email,
        String phone
) { }
