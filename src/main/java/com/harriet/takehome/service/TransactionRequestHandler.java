package com.harriet.takehome.service;

import com.harriet.takehome.constant.TransactionStatus;
import com.harriet.takehome.model.Transaction;
import com.harriet.takehome.repository.TransactionRepository;
import com.harriet.takehome.vo.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class TransactionRequestHandler {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private static Logger logger = LoggerFactory.getLogger(TransactionRequestHandler.class);

    public TransactionRequestHandler(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @JmsListener(destination="${queue.transaction-request}")
    public void processTransaction(Transaction transaction) {
        logger.info("received transaction request {} from the queue", transaction);
        transaction.setTransactionStatus(TransactionStatus.PROCESSING.name());
        transactionRepository.save(transaction);

        try {
            accountService.processTransaction(transaction);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS.name());
        } catch(Exception e){
            logger.error(e.getMessage());
            transaction.setTransactionStatus(TransactionStatus.FAILED.name());
        }
        transactionRepository.save(transaction);
    }

}
