package com.chesedbank.customer.repository;

import com.chesedbank.customer.dto.CustomerAddressResponse;
import com.chesedbank.customer.entity.Customer;
import com.chesedbank.customer.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Long> {
    Optional<CustomerAddress> findByPublicIdAddress(UUID publicIdAddress);

    /**
     * List all customer addresses
     **/
    List<CustomerAddress> findAllByCustomer(Customer customer);

    /**
     * publicIdCustomer confirm customer and
     * publicIdAddress get a specific address of customer
     **/
    Optional<CustomerAddress> findByPublicIdAddressAndCustomer_PublicIdCustomer(
            UUID publicIdAddress,
            UUID publicIdCustomer
    );
}
