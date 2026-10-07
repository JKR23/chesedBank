package com.chesedbank.customer.mapper;

import com.chesedbank.customer.dto.CustomerAddressCreateRequest;
import com.chesedbank.customer.dto.CustomerAddressResponse;
import com.chesedbank.customer.dto.CustomerAddressUpdateRequest;
import com.chesedbank.customer.entity.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {
    @Mapping(target = "idAddress", ignore = true)
    @Mapping(target = "publicIdAddress", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CustomerAddress toEntity(CustomerAddressCreateRequest request);

    CustomerAddressResponse toResponse(CustomerAddress customerAddress);

    @Mapping(target = "idAddress", ignore = true)
    @Mapping(target = "publicIdAddress", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCustomerAddress(CustomerAddressUpdateRequest request, @MappingTarget CustomerAddress customerAddress);
}
