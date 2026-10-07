package com.chesedbank.customer.controller;

import com.chesedbank.customer.dto.CustomerCreateRequest;
import com.chesedbank.customer.dto.CustomerResponse;

import com.chesedbank.customer.dto.CustomerUpdateRequest;
import com.chesedbank.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request){

        //create customer
        CustomerResponse response = service.createCustomer(request);

        //return response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getCustomers(Pageable pageable){
        //get customers
        Page<CustomerResponse> responses = service.getCustomers(pageable);

        //return responses
        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/{publicIdCustomer}")
    public ResponseEntity<CustomerResponse> getCustomerByPublicId(@PathVariable UUID publicIdCustomer){
        //get customer
        CustomerResponse response = service.getCustomerByPublicId(publicIdCustomer);

        //return response
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{publicIdCustomer}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID publicIdCustomer,
                                                           @Valid @RequestBody CustomerUpdateRequest request){
        //update customer
        CustomerResponse response = service.updateCustomer(publicIdCustomer, request);

        //return response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{publicIdCustomer}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID publicIdCustomer){

        //delete user
        service.deleteCustomer(publicIdCustomer);

        return ResponseEntity.ok().build();
    }
}
