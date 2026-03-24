package com.financetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button addIncomeButton;
    Button addExpenseButton;
    Button viewEntriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        viewEntriesButton = findViewById(R.id.viewEntriesButton);

        addIncomeButton.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Add Income clicked", Toast.LENGTH_SHORT).show()
        );

        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        viewEntriesButton.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "View Entries clicked", Toast.LENGTH_SHORT).show()
        );
    }
}