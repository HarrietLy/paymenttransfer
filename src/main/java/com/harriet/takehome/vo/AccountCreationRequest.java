package com.harriet.takehome.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Request object for creating a new account")
public class AccountCreationRequest {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("initial_balance")
    private String initialBalance;

    public AccountCreationRequest() {}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(String initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public String toString() {
        return "AccountCreationRequest{" +
                "accountId=" + accountId +
                ", initialBalance=" + initialBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountCreationRequest)) return false;
        AccountCreationRequest that = (AccountCreationRequest) o;
        return accountId == that.accountId &&
                Objects.equals(initialBalance, that.initialBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, initialBalance);
    }
}
