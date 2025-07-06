package com.harriet.takehome.service.impl;

import com.harriet.takehome.model.Account;
import com.harriet.takehome.model.AccountActivity;
import com.harriet.takehome.repository.AccountActivityRepository;
import com.harriet.takehome.repository.AccountRepository;
import com.harriet.takehome.vo.AccountCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountActivityRepository accountActivityRepository;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountActivityRepository = mock(AccountActivityRepository.class);
        accountService = new AccountServiceImpl(accountRepository, accountActivityRepository);
    }

    @Test
    void testCreateNewAccount_success() {
        // todo: with H2 in-memory db
    }

    @Test
    void testCreateNewAccount_shouldFailIfAccountIdIsNull() {
        AccountCreationRequest request = new AccountCreationRequest();
        request.setAccountId(null);
        request.setInitialBalance("100.00");

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> accountService.createNewAccount(request));

        assertEquals("accountId is null", thrown.getMessage());
    }

    @Test
    void testCreateNewAccount_shouldFailIfAccountAlreadyExists() {
        AccountCreationRequest request = new AccountCreationRequest();
        request.setAccountId(100L);
        request.setInitialBalance("100.00");

        when(accountRepository.existsById(100L)).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> accountService.createNewAccount(request));

        assertEquals("account already exists", thrown.getMessage());
    }


    @Test
    void testCreateNewAccount_shouldFailIfBalanceIsNegative() {
        AccountCreationRequest request = new AccountCreationRequest();
        request.setAccountId(101L);
        request.setInitialBalance("-10");

        when(accountRepository.existsById(101L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> accountService.createNewAccount(request));

        assertEquals("balance is out of range", thrown.getMessage());
    }
}