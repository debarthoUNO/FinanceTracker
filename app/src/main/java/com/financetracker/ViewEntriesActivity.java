package com.financetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {

    private TextView entriesTextView;
    private Button deleteAllButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        entriesTextView = findViewById(R.id.entriesTextView);
        deleteAllButton = findViewById(R.id.deleteAllButton);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "finance-tracker-db")
                .allowMainThreadQueries()
                .build();

        seedDataIfEmpty();// for testing (comment out when not testing)
        loadEntries();

        deleteAllButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All Entries")
                    .setMessage("Are you sure you want to delete all entries?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.expenseDao().deleteAllExpenses();
                        Toast.makeText(this, "All entries deleted", Toast.LENGTH_SHORT).show();
                        loadEntries();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void loadEntries() {
        List<Expense> expenses = db.expenseDao().getAllExpenses();

        if (expenses.isEmpty()) {
            entriesTextView.setText("No expenses saved yet.");
        } else {
            StringBuilder builder = new StringBuilder();

            for (Expense expense : expenses) {
                builder.append("Category: ").append(expense.getCategory()).append("\n");
                builder.append("Business: ").append(expense.getBusinessName()).append("\n");
                builder.append("Amount: $")
                        .append(String.format("%.2f",expense.getAmount()))
                                .append("\n");
                builder.append("Notes: ").append(expense.getNotes()).append("\n");
                builder.append("-------------------------\n\n");
            }

            entriesTextView.setText(builder.toString());
        }
    }

    private void seedDataIfEmpty() {
        List<Expense> expenses = db.expenseDao().getAllExpenses();

        if (expenses.isEmpty()) {
            db.expenseDao().insert(new Expense("Groceries", "Walmart", 45.25, "Weekly shopping"));
            db.expenseDao().insert(new Expense("Gas", "Shell", 32.10, ""));
            db.expenseDao().insert(new Expense("Restaurant", "Chili's", 27.50, "Dinner"));

            loadEntries();
        }
    }
}