package com.harriet.takehome.controller;

import com.harriet.takehome.service.TransactionService;
import com.harriet.takehome.vo.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
@Tag(name="Transaction Controller")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "summit a transfer request between two accounts")
    public void processTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.processTransaction(transactionRequest);
    }
}
