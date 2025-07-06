package com.harriet.takehome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="account")
public class Account {

    @Id
    private Long accountId;

    private BigDecimal currentBalance;

    private LocalDateTime createdTime;

    public Account() {}
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", currentBalance=" + currentBalance +
                ", createdTime=" + createdTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(currentBalance, account.currentBalance) &&
                Objects.equals(createdTime, account.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, currentBalance, createdTime);
    }
}
