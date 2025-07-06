package com.harriet.takehome.service.impl;

import com.harriet.takehome.constant.TransactionStatus;
import com.harriet.takehome.model.Transaction;
import com.harriet.takehome.repository.AccountRepository;
import com.harriet.takehome.repository.TransactionRepository;
import com.harriet.takehome.service.AccountService;
import com.harriet.takehome.service.TransactionService;
import com.harriet.takehome.vo.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final JmsTemplate jmsTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final AccountService accountService;

    @Value("${queue.transaction-request}")
    private String transactionQueue;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, JmsTemplate jmsTemplate, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.jmsTemplate = jmsTemplate;
        this.accountService = accountService;
    }

    @Override
    public void processTransaction(TransactionRequest transactionRequest) {
        validateTransationRequest(transactionRequest);
        Transaction transaction = transactionRepository.save(convertTransactionRequestToTransaction(transactionRequest));
        logger.info("transaction request {} is recorded to db", transaction);

        Long createdTransactionId = transaction.getTransactionId();
        logger.info ("starting to send the transaction request {} to transaction queue", transaction);

        //todo sent jms correlationid ?
        jmsTemplate.convertAndSend(transactionQueue, transaction);
    }

    private Transaction convertTransactionRequestToTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(transactionRequest.getSourceAccount());
        transaction.setDestinationAccountId(transactionRequest.getDestinationAccount());
        transaction.setTransactionStatus(TransactionStatus.REQUESTED.name());
        transaction.setInsertedTime(LocalDateTime.now());
        transaction.setTransferAmount(new BigDecimal(transactionRequest.getAmount()));
        return transaction;
    }

    private void validateTransationRequest(TransactionRequest transactionRequest) {
        Long sourceAccountId = transactionRequest.getSourceAccount();
        Long destinationAccountId = transactionRequest.getDestinationAccount();
        if (sourceAccountId == null || destinationAccountId == null) {
            throw new IllegalArgumentException("sourceAccountId and destinationAccountId must not be null");
        }
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("sourceAccountId and destinationAccountId must not be the same");
        }
        Boolean sourceExist = accountRepository.existsById(sourceAccountId);
        Boolean destinationExist = accountRepository.existsById(destinationAccountId);
        if (!sourceExist || !destinationExist) {
            throw new NoSuchElementException("account cannot be found");
        }
        BigDecimal amountInBigDecimal = new BigDecimal(transactionRequest.getAmount());
        if (amountInBigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }
}
