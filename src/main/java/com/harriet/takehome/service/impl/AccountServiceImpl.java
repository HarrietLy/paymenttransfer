package com.harriet.takehome.service.impl;

import com.harriet.takehome.dto.AccountBalanceDTO;
import com.harriet.takehome.model.Account;
import com.harriet.takehome.model.AccountActivity;
import com.harriet.takehome.model.Transaction;
import com.harriet.takehome.repository.AccountActivityRepository;
import com.harriet.takehome.repository.AccountRepository;
import com.harriet.takehome.service.AccountService;
import com.harriet.takehome.vo.AccountCreationRequest;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

        addAccountActivityAndUpdateBalance(accountCreationRequest.getAccountId(), new BigDecimal (accountCreationRequest.getInitialBalance()),null);

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

    @Autowired
    private RedissonClient redissonClient;
    @Transactional
    @Override
    public void processTransaction(Transaction transaction) throws InterruptedException {
        // https://redis.io/glossary/redis-lock/
        Long sourceAccountId = transaction.getSourceAccountId();
        Long destinationAccountId = transaction.getDestinationAccountId();
        BigDecimal amount = transaction.getTransferAmount();
        Long accountIdToLock1 = sourceAccountId< destinationAccountId ? sourceAccountId : destinationAccountId;
        Long accountIdToLock2 = sourceAccountId< destinationAccountId ? destinationAccountId : sourceAccountId;
        String lock1 = "account-lock-" + accountIdToLock1;
        String lock2 = "account-lock-" + accountIdToLock2;
        RLock Rlock1 = redissonClient.getLock(lock1);
        RLock Rlock2 = redissonClient.getLock(lock2);
        if(Rlock1.tryLock(15,10, TimeUnit.SECONDS)){
            try {
                if (Rlock2.tryLock(15,10, TimeUnit.SECONDS)){
                    try {
                        logger.info("accessing critical section modifying account balance");
                        addAccountActivityAndUpdateBalance(sourceAccountId, amount.negate(), transaction.getTransactionId());
                        addAccountActivityAndUpdateBalance(destinationAccountId, amount, transaction.getTransactionId());
                        return;
                    } finally {
                        Rlock2.unlock();
                    }
                }
            } finally {
                Rlock1.unlock();
            }
        }
        throw new RuntimeException("cannot acquire lock to update account");
    }

    @Transactional
    protected void addAccountActivityAndUpdateBalance(long accountId, BigDecimal amount, Long transactionId) {
        Account foundAccount = accountRepository.findById(accountId).orElseThrow(()->new NoSuchElementException("account not found"));
        BigDecimal currentBalance = foundAccount.getCurrentBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(" insufficient fund in account");
        }
        foundAccount.setCurrentBalance(newBalance);
        foundAccount.setLastUpdatedTime(LocalDateTime.now());
        accountRepository.save(foundAccount);
        logger.info ("current balance has been updated to {}" , foundAccount);
        AccountActivity createdAccountActivity = accountActivityRepository.save(covertToAccountActivityEntity(accountId, amount, transactionId));
        logger.info ("new account activity inserted {}" , createdAccountActivity);
    }

    private void validateAccountCreationRequest(AccountCreationRequest accountCreationRequest) {
        if (accountCreationRequest.getAccountId() == null) {
            logger.error("account creation request has null accountId");
            throw new RuntimeException("accountId is null");
        }
        if (accountCreationRequest.getAccountId() <=0) {
            logger.error("accountId is negative");
            throw new RuntimeException("accountId is negative");
        }
        boolean accountExist =  accountRepository.existsById(accountCreationRequest.getAccountId());
        if (accountExist) {
            logger.error("account creation request uses an existing account");
            throw new RuntimeException("account already exists");
        }
        BigDecimal initialBalInBigDecimal = new BigDecimal(accountCreationRequest.getInitialBalance());
        if (initialBalInBigDecimal.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new RuntimeException("balance is out of range");
        }
    }

    private Account covertAccountCreationRequestToAccountEntity(AccountCreationRequest accountCreationRequest) {
        Account account = new Account();
        account.setAccountId(accountCreationRequest.getAccountId());
//        BigDecimal initialBalanceInBigDecimal = new BigDecimal(accountCreationRequest.getInitialBalance());
        account.setCurrentBalance(BigDecimal.ZERO);
        account.setCreatedTime(LocalDateTime.now());
        return account;
    }

    private AccountActivity covertToAccountActivityEntity(long accountId, BigDecimal amount, Long transactionId) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAccountId(accountId);
        accountActivity.setTransferAmount(amount);
        accountActivity.setTransactionId(transactionId);
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
