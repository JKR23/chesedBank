package com.chesedbank.account.service;

import com.chesedbank.account.dto.request.AccountCreateRequest;
import com.chesedbank.account.dto.response.AccountResponse;
import com.chesedbank.account.dto.search.AccountSearchCriteria;
import com.chesedbank.account.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    AccountResponse createAccount(AccountCreateRequest request);
    AccountResponse getAccountByPublicId(UUID publicIdAccount);
    Page<AccountResponse> getAccounts(Pageable pageable);
    void changeStatus(UUID publicIdCustomer, UUID publicIdAccount, Status newStatus);

    //Add later: admin only
    Page<AccountResponse> searchAccounts(AccountSearchCriteria criteria,Pageable pageable);
}
