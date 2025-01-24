package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account_details")
public class AccountDetails {
    @Id
    @Column(unique = true, nullable = false)
    private long acNo;
    private String name;
    private double balance;


    public long getAcNo() {
        return acNo;
    }

    public void setAcNo(long acNo) {
        this.acNo = acNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

