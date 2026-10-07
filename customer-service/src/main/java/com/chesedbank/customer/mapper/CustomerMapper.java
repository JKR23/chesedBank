package com.chesedbank.customer.mapper;

import com.chesedbank.customer.dto.CustomerCreateRequest;
import com.chesedbank.customer.dto.CustomerResponse;
import com.chesedbank.customer.dto.CustomerUpdateRequest;
import com.chesedbank.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//componentModel = "spring" allows us to inject this class
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "idCustomer", ignore = true)
    @Mapping(target = "publicIdCustomer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    Customer toEntity(CustomerCreateRequest request);

    CustomerResponse toResponse(Customer customer);

    //MappingTarget : targeting an existing customer
    //@Mapping: tell Map-struct to ignore those fields
    @Mapping(target = "idCustomer", ignore = true)
    @Mapping(target = "publicIdCustomer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateEntity(CustomerUpdateRequest request, @MappingTarget Customer customer);
}
