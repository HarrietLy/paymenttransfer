package com.harriet.takehome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    private Long transactionId;

    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal transferAmount;
    private String transactionStatus;
    private LocalDateTime insertedTime;
    private LocalDateTime lastUpdatedTime;

    public Transaction() {}

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public LocalDateTime getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(LocalDateTime insertedTime) {
        this.insertedTime = insertedTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sourceAccountId=" + sourceAccountId +
                ", destinationAccountId=" + destinationAccountId +
                ", transferAmount=" + transferAmount +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", insertedTime=" + insertedTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return sourceAccountId == that.sourceAccountId &&
                destinationAccountId == that.destinationAccountId &&
                Objects.equals(transferAmount, that.transferAmount) &&
                Objects.equals(transactionStatus, that.transactionStatus) &&
                Objects.equals(insertedTime, that.insertedTime) &&
                Objects.equals(lastUpdatedTime, that.lastUpdatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccountId, destinationAccountId, transferAmount, transactionStatus, insertedTime, lastUpdatedTime);
    }
}
