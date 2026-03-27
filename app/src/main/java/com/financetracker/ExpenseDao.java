package com.financetracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    void insert(Expense expense);

    @Query("SELECT * FROM Expense ORDER By id DESC")
    List<Expense> getAllExpenses();

    @Query("DELETE FROM Expense")
    void deleteAllExpenses();

    @Query("SELECT SUM(amount) FROM Expense")
    Double getTotalExpenses();
}