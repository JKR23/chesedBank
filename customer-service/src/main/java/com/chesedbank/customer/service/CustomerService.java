package com.chesedbank.customer.service;

import com.chesedbank.customer.dto.CustomerCreateRequest;
import com.chesedbank.customer.dto.CustomerResponse;

import com.chesedbank.customer.dto.CustomerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
    Page<CustomerResponse> getCustomers(Pageable pageable);
    CustomerResponse getCustomerByPublicId(UUID publicIdCustomer);
    CustomerResponse updateCustomer(UUID publicIdCustomer, CustomerUpdateRequest request);
    void deleteCustomer(UUID publicIdCustomer);

    //Page<CustomerResponse> searchCustomers(SearchCriteria criteria);
}

