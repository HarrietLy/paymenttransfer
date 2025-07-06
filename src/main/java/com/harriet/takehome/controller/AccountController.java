package com.harriet.takehome.controller;

import com.harriet.takehome.dto.AccountBalanceDTO;
import com.harriet.takehome.model.Account;
import com.harriet.takehome.service.AccountService;
import com.harriet.takehome.vo.AccountCreationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/accounts")
@RestController
@Tag(name ="Account Controller")
public class AccountController {

    public final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "create a new account with initial balance")
    public void createNewAccount(@RequestBody AccountCreationRequest accountCreationRequest){
        accountService.createNewAccount(accountCreationRequest);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "view the current balance of an existing account")
    public AccountBalanceDTO getAccountBalance(@PathVariable long accountId){
        return accountService.getAccountBalance(accountId);
    }
}
