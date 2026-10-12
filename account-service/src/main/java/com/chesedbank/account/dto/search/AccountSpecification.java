package com.chesedbank.account.dto.search;

import com.chesedbank.account.entity.Account;
import com.chesedbank.account.entity.AccountType;
import com.chesedbank.account.entity.Currency;
import com.chesedbank.account.entity.Status;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountSpecification {

    //private constructor to prevent(refuse) instantiation
    private AccountSpecification() {}

    public static Specification<Account> withFilters(AccountSearchCriteria criteria) {

        //cover case where criteria is null, return illegal argument exception
        if (criteria == null) {
            throw new IllegalArgumentException(
                    "Search criteria cannot be null");
        }


        return Specification.where(hasPublicIdAccount(criteria.publicIdAccount()))
                .and(hasPublicIdCustomer(criteria.publicIdCustomer()))
                .and(hasAccountNumber(criteria.accountNumber()))
                .and(hasAccountType(criteria.accountType()))
                .and(hasCurrency(criteria.currency()))
                .and(hasStatus(criteria.status()))
                .and(hasExactBalance(criteria.balance()))
                .and(hasMinBalance(criteria.minBalance()))
                .and(hasMaxBalance(criteria.maxBalance()))
                .and(createdAfter(criteria.createdAfter()))
                .and(createdBefore(criteria.createdBefore()));
    }

    // ---------- Individual specs ----------
    public static Specification<Account> hasPublicIdAccount(UUID publicIdAccount) {
        return (root, query, cb) -> {
            if (publicIdAccount == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("publicIdAccount"), publicIdAccount);
        };
    }

    public static Specification<Account> hasPublicIdCustomer(UUID publicIdCustomer) {
        return (root, query, cb) -> {
            if (publicIdCustomer == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("publicIdCustomer"), publicIdCustomer);
        };
    }

    public static Specification<Account> hasAccountNumber(String accountNumber) {
        return (root, query, cb) -> {
            if (accountNumber == null || accountNumber.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("accountNumber"), accountNumber.trim());
        };
    }

    public static Specification<Account> hasAccountType(AccountType accountType) {
        return (root, query, cb) -> {
            if (accountType == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("accountType"), accountType);
        };
    }

    public static Specification<Account> hasCurrency(Currency currency) {
        return (root, query, cb) -> {
            if (currency == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("currency"), currency);
        };
    }

    public static Specification<Account> hasStatus(Status status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Account> hasExactBalance(BigDecimal balance) {
        return (root, query, cb) -> {
            if (balance == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("balance"), balance);
        };
    }

    public static Specification<Account> hasMinBalance(BigDecimal minBalance) {
        return (root, query, cb) -> {
            if (minBalance == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("balance"), minBalance);
        };
    }

    public static Specification<Account> hasMaxBalance(BigDecimal maxBalance) {
        return (root, query, cb) -> {
            if (maxBalance == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("balance"), maxBalance);
        };
    }

    public static Specification<Account> createdAfter(LocalDateTime createdAfter) {
        return (root, query, cb) -> {
            if (createdAfter == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("createdAt"), createdAfter);
        };
    }

    public static Specification<Account> createdBefore(LocalDateTime createdBefore) {
        return (root, query, cb) -> {
            if (createdBefore == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), createdBefore);
        };
    }
}