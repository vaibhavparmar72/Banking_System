package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transection_details")
public class TransectionDetails {


    @Id
    private long Tid;

    @ManyToOne(cascade = CascadeType.ALL)
    private AccountDetails accountDetails;

    private String transectionType;
    private int amount;
    private String date;

    public long getTid() {
        return Tid;
    }

    public void setTid(long tid) {
        Tid = tid;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public String getTransectionType() {
        return transectionType;
    }

    public void setTransectionType(String transectionType) {
        this.transectionType = transectionType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


