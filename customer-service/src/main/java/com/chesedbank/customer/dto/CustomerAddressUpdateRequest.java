package com.chesedbank.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerAddressUpdateRequest(

        //publicIdCustomer will come from the url customer connected

        @NotBlank(message = "street number should not be empty")
        @Size(max = 10, message = "street number should not be over 10 characters")
        String streetNumber,

        @NotBlank(message = "street should not be empty")
        @Size(max = 255, message = "street should not be over 255 characters")
        String street,

        @NotBlank(message = "city should not be empty")
        @Size(max = 100, message = "city should not be over 100 characters")
        String city,

        @Size(max = 100, message = "province should not be over 100 characters")
        String province,

        @Size(max = 20, message = "postal code should not be over 20 characters")
        String postalCode,

        @NotBlank(message = "country should not be empty")
        @Size(max = 100, message = "country should not be over 100 characters")
        String country,

        @NotBlank(message = "address type should not be empty")
        @Size(max = 30, message = "address type should not be over 30 characters")
        String addressType
) {

}
