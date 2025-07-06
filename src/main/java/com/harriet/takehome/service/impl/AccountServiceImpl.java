package com.harriet.takehome.service.impl;

import com.harriet.takehome.dto.AccountBalanceDTO;
import com.harriet.takehome.model.Account;
import com.harriet.takehome.model.AccountActivity;
import com.harriet.takehome.repository.AccountActivityRepository;
import com.harriet.takehome.repository.AccountRepository;
import com.harriet.takehome.service.AccountService;
import com.harriet.takehome.vo.AccountCreationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepository accountRepository;
    private AccountActivityRepository accountActivityRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountActivityRepository accountActivityRepository) {
        this.accountRepository = accountRepository;
        this.accountActivityRepository = accountActivityRepository;
    }

    @Transactional
    @Override
    public void createNewAccount(AccountCreationRequest accountCreationRequest) {
        validateAccountCreationRequest(accountCreationRequest);
        Account createdAccount = accountRepository.save(covertAccountCreationRequestToAccountEntity(accountCreationRequest));
        logger.info ("new account created {}" , createdAccount);
        AccountActivity createdAccountActivity = accountActivityRepository.save(covertAccountCreationRequestToAccountActivityEntity(accountCreationRequest));
        logger.info ("new account activity inserted {}" , createdAccountActivity);

    }

    @Override
    public AccountBalanceDTO getAccountBalance(long accountId) {
        Optional<Account> foundAccountOpt = accountRepository.findById(accountId);
        if (foundAccountOpt.isPresent()) {
            Account foundAccount = foundAccountOpt.get();
            return covertEntityToAccountBalanceDTO(foundAccount);
        } else {
            throw new NoSuchElementException("account not found");
        }
    }

    @Override
    public void addAccountActivityAndUpdateBalance(long accountId, BigDecimal amount) {

    }

    private void validateAccountCreationRequest(AccountCreationRequest accountCreationRequest) {
        if (accountCreationRequest.getAccountId() == null) {
            logger.error("account creation request has null accountId");
            throw new RuntimeException("accountId is null");
        }
        boolean accountExist =  accountRepository.existsById(accountCreationRequest.getAccountId());
        if (accountExist) {
            logger.error("account creation request uses an existing account");
            throw new RuntimeException("account already exists");
        }
    }

    private Account covertAccountCreationRequestToAccountEntity(AccountCreationRequest accountCreationRequest) {
        Account account = new Account();
        account.setAccountId(accountCreationRequest.getAccountId());
        BigDecimal intialBalanceInBigDecimal = new BigDecimal(accountCreationRequest.getIntialBalance());
        account.setCurrentBalance(intialBalanceInBigDecimal);
        account.setCreatedTime(LocalDateTime.now());
        return account;
    }

    private AccountActivity covertAccountCreationRequestToAccountActivityEntity(AccountCreationRequest accountCreationRequest) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAccountId(accountCreationRequest.getAccountId());
        BigDecimal intialBalanceInBigDecimal = new BigDecimal(accountCreationRequest.getIntialBalance());
        accountActivity.setTransferAmount(intialBalanceInBigDecimal);
        accountActivity.setInsertedTime(LocalDateTime.now());
        return accountActivity;
    }

    private AccountBalanceDTO covertEntityToAccountBalanceDTO(Account account) {
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
        accountBalanceDTO.setAccountId(account.getAccountId());
        accountBalanceDTO.setBalance(account.getCurrentBalance());
        return accountBalanceDTO;
    }
}
