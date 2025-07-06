package com.harriet.takehome.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This table is used to record historical funds going in and out of each account
 * for viewing by user or for reconciliation
 */
@Entity
@Table(name="account_activity")
public class AccountActivity {

    @Id
    private Long id;

    private Long accountId;

    private Long transactionId;

    private BigDecimal transferAmount;

    private LocalDateTime insertedTime;

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public LocalDateTime getInsertedTime() {
        return insertedTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setInsertedTime(LocalDateTime insertedTime) {
        this.insertedTime = insertedTime;
    }

    @Override
    public String toString() {
        return "AccountActivity{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", transactionId=" + transactionId +
                ", transferAmount=" + transferAmount +
                ", insertedTime=" + insertedTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountActivity)) return false;
        AccountActivity that = (AccountActivity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(transferAmount, that.transferAmount) &&
                Objects.equals(insertedTime, that.insertedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, transactionId, transferAmount, insertedTime);
    }
}


