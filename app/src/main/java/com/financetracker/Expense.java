package com.financetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String category;
    private String businessName;
    private double amount;
    private String notes;

    public Expense(String category, String businessName, double amount, String notes) {
        this.category = category;
        this.businessName = businessName;
        this.amount = amount;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getBusinessName() {
        return businessName;
    }

    public double getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }
}