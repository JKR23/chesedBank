package com.chesedbank.account.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "public_id_account", nullable = false, updatable = false, unique = true)
    private UUID publicIdAccount;

    @Column(name = "public_id_customer", nullable = false)
    private UUID publicIdCustomer;

    @Column(name = "account_number", nullable = false, updatable = false, unique = true, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 30)
    private AccountType accountType;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Builder.Default //accept to initialize
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; //initialize account

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        //generate public id account
        if(this.publicIdAccount==null){
            this.publicIdAccount = UUID.randomUUID();
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
