package com.harriet.takehome.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Request object for submitting a transfer request")
public class TransactionRequest {

    @JsonProperty("source_account_id")
    private Long sourceAccount;

    @JsonProperty("destination_account_id")
    private Long destinationAccount;

    private String amount;

    public TransactionRequest() {
    }

    public Long getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Long sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Long getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Long destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "sourceAccount=" + sourceAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionRequest)) return false;
        TransactionRequest that = (TransactionRequest) o;
        return Objects.equals(sourceAccount, that.sourceAccount) &&
                Objects.equals(destinationAccount, that.destinationAccount) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, destinationAccount, amount);
    }
}
