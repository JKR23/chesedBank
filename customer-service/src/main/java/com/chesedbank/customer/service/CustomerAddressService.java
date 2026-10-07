package com.chesedbank.customer.service;

import com.chesedbank.customer.dto.CustomerAddressCreateRequest;
import com.chesedbank.customer.dto.CustomerAddressResponse;
import com.chesedbank.customer.dto.CustomerAddressUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface CustomerAddressService {
    CustomerAddressResponse createAddress (UUID publicIdCustomer, CustomerAddressCreateRequest request);
    List<CustomerAddressResponse> getAddressesByCustomer(UUID publicIdCustomer);

    /**
     * publicIdCustomer confirm customer and
     * publicIdAddress get a specific address of customer
     **/
    CustomerAddressResponse getAddressByPublicId(UUID publicIdCustomer, UUID publicIdAddress);

    /**
     * publicIdCustomer confirm customer
     * publicIdAddress confirm customer address
     * request information to update*/
    CustomerAddressResponse updateAddress(UUID publicIdCustomer,
                                          UUID publicIdAddress,
                                          CustomerAddressUpdateRequest request);

    /**
     * publicIdCustomer confirm customer
     * publicIdAddress confirm customer address and get specific address
     **/
    void deleteAddress(UUID publicIdCustomer, UUID publicIdAddress);
}
