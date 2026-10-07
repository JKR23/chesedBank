package com.chesedbank.customer.service.impl;

import com.chesedbank.customer.dto.CustomerCreateRequest;
import com.chesedbank.customer.dto.CustomerResponse;
import com.chesedbank.customer.dto.CustomerUpdateRequest;
import com.chesedbank.customer.entity.Customer;
import com.chesedbank.customer.mapper.CustomerMapper;
import com.chesedbank.customer.repository.CustomerRepository;
import com.chesedbank.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        //transform request to Entity
        Customer customer = mapper.toEntity(request);

        //save Entity
        Customer savedCustomer = repository.save(customer);

        //return response
        return mapper.toResponse(savedCustomer);
    }

    //readOnly : because the method is just for read
    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponse> getCustomers(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::toResponse);
    }

    //readOnly : because the method is just for read
    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomerByPublicId(UUID publicIdCustomer) {
        //find customer or throw exception if not present
        Customer customer = findCustomerWithPublicId(publicIdCustomer);

        //return response
        return mapper.toResponse(customer);
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(UUID publicIdCustomer, CustomerUpdateRequest request) {

        //find customer or throw exception if not present
        Customer customer = findCustomerWithPublicId(publicIdCustomer);

        //update customer
        mapper.updateEntity(request,customer);

        //get customer updated
        Customer updatedCustomer = repository.save(customer);

        //return response
        return mapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID publicIdCustomer) {
        //get customer or throw exception if not present
        Customer customer =findCustomerWithPublicId(publicIdCustomer);

        //delete customer
        repository.delete(customer); //use boolean instead of deleting user completely
    }

    private Customer findCustomerWithPublicId(UUID publicIdCustomer){
        //get customer or throw exception if not present
        return repository.findByPublicIdCustomer(publicIdCustomer)
                .orElseThrow(()->new RuntimeException("Customer doesn't exist with ID: "+publicIdCustomer));
    }
}
