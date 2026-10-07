package com.chesedbank.customer.dto;

import java.util.List;
import java.util.UUID;

public record CustomerResponse(
        UUID publicIdCustomer,
        String firstName,
        String lastName,
        String email,
        String phone,

        //map-struct will map the CustomerAddress to response automatically
        List<CustomerAddressResponse> addresses
) { }
