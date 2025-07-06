package com.harriet.takehome.service;

import com.harriet.takehome.vo.TransactionRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequestHandler {

    private  AccountService accountService;

    //listen to transaction request queue,
    // for each receive,

    // add timeout

    private void updateAccountBalanceAndActivity(TransactionRequest transactionRequest) {

    }
    // call add account activity and updatebalance account service
}
