package com.chesedbank.customer.controller;

import com.chesedbank.customer.dto.CustomerAddressCreateRequest;
import com.chesedbank.customer.dto.CustomerAddressResponse;
import com.chesedbank.customer.dto.CustomerAddressUpdateRequest;
import com.chesedbank.customer.service.CustomerAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer/{publicIdCustomer}/addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @PostMapping
    public ResponseEntity<CustomerAddressResponse> createAddress(
            @PathVariable UUID publicIdCustomer,
            @Valid @RequestBody CustomerAddressCreateRequest request) {

        CustomerAddressResponse response =
                customerAddressService.createAddress(
                        publicIdCustomer,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerAddressResponse>> getAddressesByCustomer(
            @PathVariable UUID publicIdCustomer) {

        List<CustomerAddressResponse> response =
                customerAddressService.getAddressesByCustomer(
                        publicIdCustomer
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{publicIdAddress}")
    public ResponseEntity<CustomerAddressResponse> getAddressByPublicId(
            @PathVariable UUID publicIdCustomer,
            @PathVariable UUID publicIdAddress) {

        CustomerAddressResponse response =
                customerAddressService.getAddressByPublicId(
                        publicIdCustomer,
                        publicIdAddress
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{publicIdAddress}")
    public ResponseEntity<CustomerAddressResponse> updateAddress(
            @PathVariable UUID publicIdCustomer,
            @PathVariable UUID publicIdAddress,
            @Valid @RequestBody CustomerAddressUpdateRequest request) {

        CustomerAddressResponse response =
                customerAddressService.updateAddress(
                        publicIdCustomer,
                        publicIdAddress,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{publicIdAddress}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID publicIdCustomer,
            @PathVariable UUID publicIdAddress) {

        customerAddressService.deleteAddress(
                publicIdCustomer,
                publicIdAddress
        );

        return ResponseEntity.noContent().build();
    }
}