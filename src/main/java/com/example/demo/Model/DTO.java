package com.example.demo.Model;

public class DTO {
    String type;
    int amount;
    String date;


    public DTO(String type, int amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }


    public DTO() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
