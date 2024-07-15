package com.kinjal.bank.entity;

import java.time.LocalDate;

public class Transaction {

    private LocalDate transactionDate;
    private Double transactionAmount;
    private String transactionId;
    private String transactionPerformedBy;
    private String transactionType;
    private Double initialBalance;
    private Double finalBalance;

    public Transaction(LocalDate transactionDate, Double transactionAmount, String transactionId, String transactionPerformedBy, String transactionType, Double initialBalance, Double finalBalance) {
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionId = transactionId;
        this.transactionPerformedBy = transactionPerformedBy;
        this.transactionType = transactionType;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionPerformedBy() {
        return transactionPerformedBy;
    }

    public void setTransactionPerformedBy(String transactionPerformedBy) {
        this.transactionPerformedBy = transactionPerformedBy;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Double finalBalance) {
        this.finalBalance = finalBalance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                ", transactionId='" + transactionId + '\'' +
                ", transactionPerformedBy='" + transactionPerformedBy + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", initialBalance=" + initialBalance +
                ", finalBalance=" + finalBalance +
                '}';
    }
}
