package com.chesedbank.account.service.impl;

import com.chesedbank.account.config.AccountNumberGenerator;
import com.chesedbank.account.dto.request.AccountCreateRequest;
import com.chesedbank.account.dto.response.AccountResponse;
import com.chesedbank.account.dto.search.AccountSearchCriteria;
import com.chesedbank.account.dto.search.AccountSpecification;
import com.chesedbank.account.entity.Account;
import com.chesedbank.account.entity.Status;
import com.chesedbank.account.exception.AccountNotFoundException;
import com.chesedbank.account.mapper.AccountMapper;
import com.chesedbank.account.repository.AccountRepository;
import com.chesedbank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final AccountNumberGenerator accountNumberGenerator;

    @Transactional
    @Override
    public AccountResponse createAccount(AccountCreateRequest request) {
        //transform request to entity
        Account account = mapper.toEntity(request);

        //set account number
        String accountNumberGenerated = checkAccountNumberIsUnique();

        account.setAccountNumber(accountNumberGenerated);

        //set balance
        //already set into account entity

        //set status
        //already set into account entity


        //save and return response
        return mapper.toResponse(repository.save(account));
    }

    @Override
    public AccountResponse getAccountByPublicId(UUID publicIdAccount) {
        return mapper.toResponse(findAccountWithPublicIdAccount(publicIdAccount));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AccountResponse> getAccounts(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional
    @Override
    public void changeStatus(UUID publicIdCustomer, UUID publicIdAccount, Status newStatus) {

        //get account or throw exception if not present
        Account account = repository.findByPublicIdAccountAndPublicIdCustomer(publicIdAccount, publicIdCustomer)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + publicIdAccount + " for customer ID: " + publicIdCustomer));

        //check current status and new status
        checkCurrentStatusAndNewStatus(account, newStatus);

        //set new status
        account.setStatus(newStatus);

        //save changes
        repository.save(account);
    }

    @Override
    public Page<AccountResponse> searchAccounts(AccountSearchCriteria criteria,Pageable pageable) {
        return repository.findAll(AccountSpecification.withFilters(criteria), pageable)
                .map(mapper::toResponse);
    }

    private void checkCurrentStatusAndNewStatus(
            Account account,
            Status newStatus
    ) {

        //cover case when newStatus is null
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "New status cannot be null."
            );
        }

        Status currentStatus = account.getStatus();

        //cover case when currentStatus is equal to newStatus
        if (currentStatus == newStatus) {
            throw new IllegalStateException(
                    "Account is already in status: " + newStatus
            );
        }

        //cover case when currentStatus is CLOSED
        if (currentStatus == Status.CLOSED) {
            throw new IllegalStateException(
                    "Cannot change the status of a closed account."
            );
        }

        //cover case when newStatus is CLOSED and balance is not zero
        if (newStatus == Status.CLOSED
                && account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException(
                    "Cannot close an account with a non-zero balance."
            );
        }

        boolean allowed = switch (currentStatus) {
            case PENDING -> newStatus == Status.ACTIVE
                    || newStatus == Status.BLOCKED
                    || (newStatus == Status.CLOSED && account.getBalance().compareTo(BigDecimal.ZERO) == 0);
            case ACTIVE -> newStatus == Status.BLOCKED
                    || (newStatus == Status.CLOSED && account.getBalance().compareTo(BigDecimal.ZERO) == 0);
            case BLOCKED -> newStatus == Status.ACTIVE
                    || (newStatus == Status.CLOSED && account.getBalance().compareTo(BigDecimal.ZERO) == 0);
            default -> throw new IllegalStateException("Unexpected value: " + currentStatus);
        };

        if (!allowed) {
            throw new IllegalStateException(
                    "Transition from " + currentStatus
                            + " to " + newStatus + " is not allowed."
            );
        }
    }

    private Account findAccountWithPublicIdAccount(UUID publicIdAccount){

        return repository.findByPublicIdAccount(publicIdAccount).orElseThrow(
                ()->new AccountNotFoundException("Account not found with ID: "+publicIdAccount)
        );
    }

    private String checkAccountNumberIsUnique() {

        String accountNumberGenerated;
        int maxAttempts = 10;
        boolean accountNumberExist;

        for (int attempts = 1; attempts <= maxAttempts; attempts++){

            //generate an account-number
            accountNumberGenerated = accountNumberGenerator.generateAccountNumber();

            //check if account-number exist
            accountNumberExist = repository.existsByAccountNumber(accountNumberGenerated);

            //return account-number-generated if not exist in the db
            if (!accountNumberExist){
                return accountNumberGenerated;
            }
        }

        //throw exception if maxAttempts hit
        throw new IllegalStateException(
                "Unable to generate a unique account number after "
                        + maxAttempts + " attempts."
        );
    }

}