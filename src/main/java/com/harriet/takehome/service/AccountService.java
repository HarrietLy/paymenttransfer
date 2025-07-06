package com.harriet.takehome.service;

import com.harriet.takehome.dto.AccountBalanceDTO;
import com.harriet.takehome.vo.AccountCreationRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface AccountService {

    void createNewAccount(AccountCreationRequest accountCreationRequest);

    AccountBalanceDTO getAccountBalance(long accountId);

    void addAccountActivityAndUpdateBalance(long accountId, BigDecimal amount);

}
