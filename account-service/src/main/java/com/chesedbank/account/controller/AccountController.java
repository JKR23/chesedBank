package com.chesedbank.account.controller;

import com.chesedbank.account.dto.request.AccountCreateRequest;
import com.chesedbank.account.dto.request.AccountStatusChangeRequest;
import com.chesedbank.account.dto.response.AccountResponse;
import com.chesedbank.account.dto.search.AccountSearchCriteria;
import com.chesedbank.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    //Create an account
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountCreateRequest request) {

        //create account
        AccountResponse response = service.createAccount(request);

        //return response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Retrieve one account by publicIdAccount
    @GetMapping("/{publicIdAccount}")
    public ResponseEntity<AccountResponse> getAccountByPublicId(@PathVariable("publicIdAccount")
                                                                UUID publicIdAccount) {
        //get account
        AccountResponse response = service.getAccountByPublicId(publicIdAccount);

        //return response
        return ResponseEntity.ok(response);
    }

    //List accounts with pagination
    @GetMapping
    public ResponseEntity<Page<AccountResponse>> getAccounts(
            @PageableDefault(
                size = 10,
                sort = "createdAt",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(service.getAccounts(pageable));
    }

    //Search using optional filters
    @GetMapping("/search")
    public ResponseEntity<Page<AccountResponse>> searchAccounts(
            @ModelAttribute AccountSearchCriteria criteria,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {


        return ResponseEntity.ok(service.searchAccounts(criteria, pageable));
    }

    //Change account status
    @PatchMapping("/{publicIdAccount}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable("publicIdAccount") UUID publicIdAccount,
            @RequestParam("publicIdCustomer") UUID publicIdCustomer,
            @Valid @RequestBody AccountStatusChangeRequest request
    ) {

        //treatment
        service.changeStatus(publicIdCustomer, publicIdAccount, request.newStatus());

        //return body.build
        return ResponseEntity.noContent().build();
    }

}
