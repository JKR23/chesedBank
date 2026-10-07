package com.chesedbank.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer_address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false)
    private Long idAddress;

    @Column(name = "public_id_address", nullable = false, updatable = false, unique = true)
    private UUID publicIdAddress;

    //zero or several address belongs to a user
    //FetchType.LAZY: do not get all customer object
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @Column(name = "street_number", length = 10, nullable = false)
    private String streetNumber;

    @Column(name = "street", length = 255, nullable = false)
    private String street;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "province", length = 100)
    private String province;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    //EnumType.STRING : in the db (e.g : HOME,WORK,MAILING,OTHER instead of 0,1,2...)
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 30)
    private AddressType addressType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        if(this.publicIdAddress==null){
            this.publicIdAddress = UUID.randomUUID();
        }

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;

        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
