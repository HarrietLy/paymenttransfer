package com.harriet.takehome.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Request object for creating a new account")
public class AccountCreationRequest {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("initial_balance")
    private String intialBalance;

    public AccountCreationRequest() {}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getIntialBalance() {
        return intialBalance;
    }

    public void setIntialBalance(String intialBalance) {
        this.intialBalance = intialBalance;
    }

    @Override
    public String toString() {
        return "AccountCreationRequest{" +
                "accountId=" + accountId +
                ", intialBalance=" + intialBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountCreationRequest)) return false;
        AccountCreationRequest that = (AccountCreationRequest) o;
        return accountId == that.accountId &&
                Objects.equals(intialBalance, that.intialBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, intialBalance);
    }
}
