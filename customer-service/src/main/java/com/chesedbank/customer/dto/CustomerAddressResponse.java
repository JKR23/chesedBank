package com.chesedbank.customer.dto;

import java.util.UUID;

public record CustomerAddressResponse(
        UUID publicIdAddress,
        String streetNumber,
        String street,
        String city,
        String province,
        String postalCode,
        String country,
        String addressType
) {

}
