package com.harriet.takehome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Response object for querying current balance")
public class AccountBalanceDTO {

    @JsonProperty("account_id")
    private Long accountId;
    private String balance;

    public AccountBalanceDTO() {}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        String balanceInString = balance.toPlainString();
        this.balance = balanceInString;
    }

    @Override
    public String toString() {
        return "AccountBalanceDTO{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalanceDTO)) return false;
        AccountBalanceDTO that = (AccountBalanceDTO) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance);
    }

}
