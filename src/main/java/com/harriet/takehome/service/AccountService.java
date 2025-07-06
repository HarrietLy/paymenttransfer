package com.harriet.takehome.service;

import com.harriet.takehome.dto.AccountBalanceDTO;
import com.harriet.takehome.model.Transaction;
import com.harriet.takehome.vo.AccountCreationRequest;
import com.harriet.takehome.vo.TransactionRequest;

import java.math.BigDecimal;

public interface AccountService {

    void createNewAccount(AccountCreationRequest accountCreationRequest);

    AccountBalanceDTO getAccountBalance(long accountId);

    void processTransaction(Transaction transaction);
}
