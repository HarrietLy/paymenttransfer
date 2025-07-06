package com.harriet.takehome.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal transferAmount;
    private String transactionStatus;
    private LocalDateTime insertedTime;
    private LocalDateTime lastUpdatedTime;

    public Transaction() {}

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

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
                "transactionId=" + transactionId +
                ", sourceAccountId=" + sourceAccountId +
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
                transactionId == that.transactionId &&
                destinationAccountId == that.destinationAccountId &&
                Objects.equals(transferAmount, that.transferAmount) &&
                Objects.equals(transactionStatus, that.transactionStatus) &&
                Objects.equals(insertedTime, that.insertedTime) &&
                Objects.equals(lastUpdatedTime, that.lastUpdatedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, sourceAccountId, destinationAccountId, transferAmount, transactionStatus, insertedTime, lastUpdatedTime);
    }
}
