package com.chesedbank.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long idCustomer;

    @Column(
            name = "public_id_customer",
            nullable = false,
            updatable = false,
            unique = true
    )
    private UUID publicIdCustomer;

    @Column(
            name = "first_name",
            nullable = false,
            length = 50
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 50
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            length = 50,
            unique = true,
            updatable = false
    )
    private String email;

    @Column(
            name = "phone",
            length = 15
    )
    private String phone;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    // One customer can have zero or several addresses
    // mappedBy = "customer": uses the "customer" field in CustomerAddress
    // CascadeType.ALL: operations on Customer are propagated to its addresses
    // orphanRemoval = true: an address removed from this collection is deleted
    //new ArrayList<>(): for having an empty collection instead of null
    @Builder.Default //initialize List
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CustomerAddress> addresses = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        //if publicId is null, generate it
        if(this.publicIdCustomer==null){
            this.publicIdCustomer = UUID.randomUUID();
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
