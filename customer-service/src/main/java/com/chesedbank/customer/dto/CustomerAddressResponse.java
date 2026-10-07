package com.chesedbank.customer.dto;

import com.chesedbank.customer.entity.AddressType;

import java.util.UUID;

public record CustomerAddressResponse(
        UUID publicIdAddress,
        String streetNumber,
        String street,
        String city,
        String province,
        String postalCode,
        String country,
        AddressType addressType
) {

}
