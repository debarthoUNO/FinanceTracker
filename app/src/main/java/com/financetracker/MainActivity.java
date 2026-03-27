package com.financetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.widget.TextView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button addIncomeButton;
    Button addExpenseButton;
    Button viewEntriesButton;

    private TextView balanceAmount;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        viewEntriesButton = findViewById(R.id.viewEntriesButton);
        balanceAmount = findViewById(R.id.balanceAmount);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "finance-tracker-db")
                        .allowMainThreadQueries()
                        .build();

        loadBalance();

        addIncomeButton.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Add Income clicked", Toast.LENGTH_SHORT).show()
        );

        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        viewEntriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEntriesActivity.class);
            startActivity(intent);
        });
    }

    private void loadBalance() {
        Double totalExpenses = db.expenseDao().getTotalExpenses();

        if (totalExpenses == null) {
            totalExpenses = 0.0;
        }

        balanceAmount.setText("$" + String.format("%.2f", totalExpenses));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (db != null) {
            loadBalance();
        }

    }
}