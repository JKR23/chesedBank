
package com.chesedbank.account.service.impl;

import com.chesedbank.account.config.AccountNumberGenerator;
import com.chesedbank.account.dto.response.AccountResponse;
import com.chesedbank.account.dto.search.AccountSearchCriteria;
import com.chesedbank.account.entity.Account;
import com.chesedbank.account.mapper.AccountMapper;
import com.chesedbank.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void searchAccounts_shouldReturnMappedAccounts() {
        // Arrange
        UUID customerId = UUID.randomUUID();

        AccountSearchCriteria criteria = new AccountSearchCriteria(
                null, customerId, null, null, null,
                null, null, null, null, null, null
        );

        Pageable pageable = PageRequest.of(0, 10);

        Account account = mock(Account.class);
        AccountResponse response = mock(AccountResponse.class);

        Page<Account> accountPage =
                new PageImpl<>(List.of(account), pageable, 1);

        when(repository.findAll(
                any(Specification.class), eq(pageable)))
                .thenReturn(accountPage);

        when(mapper.toResponse(account)).thenReturn(response);

        // Act
        Page<AccountResponse> result =
                accountService.searchAccounts(criteria, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(response, result.getContent().get(0));

        verify(repository).findAll(
                any(Specification.class), eq(pageable));
        verify(mapper).toResponse(account);
    }

    @Test
    void searchAccounts_shouldReturnEmptyPageWhenNoAccountsMatch() {
        // Arrange
        AccountSearchCriteria criteria = new AccountSearchCriteria(
                null, UUID.randomUUID(), null, null, null,
                null, null, null, null, null, null
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<Account> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        when(repository.findAll(
                any(Specification.class), eq(pageable)))
                .thenReturn(emptyPage);

        // Act
        Page<AccountResponse> result =
                accountService.searchAccounts(criteria, pageable);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(repository).findAll(
                any(Specification.class), eq(pageable));
        verify(mapper, never()).toResponse(any(Account.class));
    }
}
