package com.chesedbank.account.controller;

import com.chesedbank.account.dto.request.AccountCreateRequest;
import com.chesedbank.account.dto.request.AccountStatusChangeRequest;
import com.chesedbank.account.dto.response.AccountResponse;
import com.chesedbank.account.dto.search.AccountSearchCriteria;
import com.chesedbank.account.entity.AccountType;
import com.chesedbank.account.entity.Currency;
import com.chesedbank.account.entity.Status;
import com.chesedbank.account.exception.AccountNotFoundException;
import com.chesedbank.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private AccountService service;

    @Test
    void createAccount_shouldReturn201Created() throws Exception {

        // Arrange
        UUID publicIdCustomer = UUID.randomUUID();
        UUID publicIdAccount = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AccountCreateRequest request = new AccountCreateRequest(
                publicIdCustomer,
                AccountType.CHECKING,
                Currency.CAD
        );

        AccountResponse response = new AccountResponse(
                publicIdAccount,
                publicIdCustomer,
                "1234567890",
                AccountType.CHECKING,
                Currency.CAD,
                BigDecimal.ZERO,
                Status.PENDING,
                now,
                now
        );

        when(service.createAccount(any(AccountCreateRequest.class)))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publicIdAccount")
                        .value(publicIdAccount.toString()))
                .andExpect(jsonPath("$.publicIdCustomer")
                        .value(publicIdCustomer.toString()))
                .andExpect(jsonPath("$.accountNumber")
                        .value("1234567890"))
                .andExpect(jsonPath("$.status")
                        .value("PENDING"));
    }


    @Test
    void getAccountByPublicId_shouldReturn200Ok() throws Exception {

        // Arrange
        UUID publicIdAccount = UUID.randomUUID();
        UUID publicIdCustomer = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AccountResponse response = new AccountResponse(
                publicIdAccount,
                publicIdCustomer,
                "1234567890",
                AccountType.CHECKING,
                Currency.CAD,
                BigDecimal.ZERO,
                Status.ACTIVE,
                now,
                now
        );

        when(service.getAccountByPublicId(publicIdAccount))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(get("/api/v1/accounts/{publicIdAccount}", publicIdAccount))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicIdAccount")
                        .value(publicIdAccount.toString()))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"));
    }


    @Test
    void searchAccounts_shouldReturn200Ok() throws Exception {

        // Arrange
        UUID publicIdAccount = UUID.randomUUID();
        UUID publicIdCustomer = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AccountResponse response = new AccountResponse(
                publicIdAccount,
                publicIdCustomer,
                "1234567890",
                AccountType.CHECKING,
                Currency.CAD,
                BigDecimal.ZERO,
                Status.ACTIVE,
                now,
                now
        );

        Page<AccountResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 10),
                1
        );

        when(service.searchAccounts(any(AccountSearchCriteria.class), any(Pageable.class)))
                .thenReturn(page);

        // Act + Assert
        mockMvc.perform(get("/api/v1/accounts/search")
                        .param("publicIdCustomer", publicIdCustomer.toString())
                        .param("status", "ACTIVE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].publicIdAccount")
                        .value(publicIdAccount.toString()))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }


    @Test
    void changeStatus_shouldReturn204NoContent() throws Exception {

        // Arrange
        UUID publicIdAccount = UUID.randomUUID();
        UUID publicIdCustomer = UUID.randomUUID();

        AccountStatusChangeRequest request =
                new AccountStatusChangeRequest(Status.BLOCKED);

        // Act + Assert
        mockMvc.perform(patch(
                        "/api/v1/accounts/{publicIdAccount}/status",
                        publicIdAccount)
                        .param("publicIdCustomer", publicIdCustomer.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        // Verify that the service received the expected values
        verify(service).changeStatus(
                publicIdCustomer,
                publicIdAccount,
                Status.BLOCKED
        );
    }


    @Test
    void createAccount_withMissingRequiredFields_shouldReturn400BadRequest()
            throws Exception {

        // Arrange: both required fields are null
        String invalidRequest = """
            {
                "publicIdCustomer": null,
                "accountType": null,
                "currency": null
            }
            """;

        // Act + Assert
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        // The service must not be called for an invalid request
        verifyNoInteractions(service);
    }


    @Test
    void getAccountByPublicId_whenAccountDoesNotExist_shouldReturn404()
            throws Exception {

        UUID publicIdAccount = UUID.randomUUID();

        when(service.getAccountByPublicId(publicIdAccount))
                .thenThrow(new AccountNotFoundException(
                        "Account not found with ID: " + publicIdAccount
                ));

        mockMvc.perform(get(
                        "/api/v1/accounts/{publicIdAccount}",
                        publicIdAccount))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Account Not Found"));
    }



}