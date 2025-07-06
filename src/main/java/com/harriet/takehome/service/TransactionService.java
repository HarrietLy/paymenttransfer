package com.harriet.takehome.service;

import com.harriet.takehome.vo.TransactionRequest;
import org.springframework.stereotype.Service;

public interface TransactionService {

    void processTransaction(TransactionRequest transactionRequest);
}
