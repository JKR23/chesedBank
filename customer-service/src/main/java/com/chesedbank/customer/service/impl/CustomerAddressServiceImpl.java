package com.chesedbank.customer.service.impl;

import com.chesedbank.customer.dto.CustomerAddressCreateRequest;
import com.chesedbank.customer.dto.CustomerAddressResponse;
import com.chesedbank.customer.dto.CustomerAddressUpdateRequest;
import com.chesedbank.customer.entity.Customer;
import com.chesedbank.customer.entity.CustomerAddress;
import com.chesedbank.customer.exception.CustomerAddressNotFoundException;
import com.chesedbank.customer.exception.CustomerNotFoundException;
import com.chesedbank.customer.mapper.CustomerAddressMapper;
import com.chesedbank.customer.repository.CustomerAddressRepository;
import com.chesedbank.customer.repository.CustomerRepository;
import com.chesedbank.customer.service.CustomerAddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerAddressMapper addressMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerAddressResponse createAddress(UUID publicIdCustomer, CustomerAddressCreateRequest request) {
        //find customer first
        Customer customer = findCustomerWithPublicId(publicIdCustomer);

        //transform to Entity
        CustomerAddress customerAddress = addressMapper.toEntity(request);

        //set customer in address
        customerAddress.setCustomer(customer);

        //saved address
        CustomerAddress savedCustomerAddress = customerAddressRepository.save(customerAddress);

        //return response
        return addressMapper.toResponse(savedCustomerAddress);
    }

    /**
     * find all addresses of the specified customer
     * */
    @Override
    public List<CustomerAddressResponse> getAddressesByCustomer(UUID publicIdCustomer) {

        //find customer first
        Customer customer = findCustomerWithPublicId(publicIdCustomer);

        return customerAddressRepository.findAllByCustomer(customer).stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerAddressResponse getAddressByPublicId(UUID publicIdCustomer, UUID publicIdAddress) {

        //verify customer exist
        findCustomerWithPublicId(publicIdCustomer);

        //find address with publicId of address and customer
        CustomerAddress customerAddress =
                findCustomerAddressWithPublicIdCustomerAndAddress(publicIdAddress,publicIdCustomer);

        //return response
        return addressMapper.toResponse(customerAddress);
    }

    @Override
    public CustomerAddressResponse updateAddress(UUID publicIdCustomer, UUID publicIdAddress, CustomerAddressUpdateRequest request) {
        //verify customer exist
        findCustomerWithPublicId(publicIdCustomer);

        //find address belongs to that user
        CustomerAddress customerAddress = findCustomerAddressWithPublicIdCustomerAndAddress(publicIdAddress,publicIdCustomer);

        //update
        addressMapper.updateCustomerAddress(request, customerAddress);

        //save the updatedAddress
        CustomerAddress updatedCustomerAddress = customerAddressRepository.save(customerAddress);

        //return response
        return addressMapper.toResponse(updatedCustomerAddress);
    }

    @Override
    public void deleteAddress(UUID publicIdCustomer, UUID publicIdAddress) {
        //first find the relevant customer : reassure customer exist
        findCustomerWithPublicId(publicIdCustomer);

        //second find address of the customer
        CustomerAddress customerAddress = findCustomerAddressWithPublicIdCustomerAndAddress(publicIdAddress,publicIdCustomer);

        //delete
        customerAddressRepository.delete(customerAddress);
    }

    /**
     * find the address of a customer
     * from public id address and public id of customer
     * by comparing both id with those in the table Address*/
    private CustomerAddress findCustomerAddressWithPublicIdCustomerAndAddress(UUID publicIdAddress ,
                                                                              UUID publicIdCustomer){

        return  customerAddressRepository.findByPublicIdAddressAndCustomer_PublicIdCustomer(publicIdAddress, publicIdCustomer)
                .orElseThrow(()-> new CustomerAddressNotFoundException("No address found with ID: "+publicIdAddress)
        );
    }

    private Customer findCustomerWithPublicId(UUID publicIdCustomer) {
        return customerRepository
                .findByPublicIdCustomer(publicIdCustomer)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer doesn't exist with ID: "
                                        + publicIdCustomer
                        )
                );
    }

}
