package com.financetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {

    private TextView entriesTextView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        entriesTextView = findViewById(R.id.entriesTextView);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "finance-tracker-db")
                .allowMainThreadQueries()
                .build();

        List<Expense> expenses = db.expenseDao().getAllExpenses();

        if (expenses.isEmpty()) {
            entriesTextView.setText("No expenses saved yet.");
        } else {
            StringBuilder builder = new StringBuilder();

            for (Expense expense : expenses) {
                builder.append("Category: ").append(expense.getCategory()).append("\n");
                builder.append("Business: ").append(expense.getBusinessName()).append("\n");
                builder.append("Amount: $").append(expense.getAmount()).append("\n");
                builder.append("Notes: ").append(expense.getNotes()).append("\n");
                builder.append("-------------------------\n\n");
            }

            entriesTextView.setText(builder.toString());
        }
    }
}