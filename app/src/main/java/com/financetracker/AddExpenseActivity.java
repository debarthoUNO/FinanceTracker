package com.financetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.InputFilter;

import java.util.ArrayList;

public class AddExpenseActivity extends AppCompatActivity {

    private Button categoryButton;
    private Button saveExpenseButton;
    private EditText businessInput;
    private EditText amountInput;
    private EditText notesInput;
    private AppDatabase db;

    private final ArrayList<String> categories = new ArrayList<>();
    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        categoryButton = findViewById(R.id.categoryButton);
        saveExpenseButton = findViewById(R.id.saveExpenseButton);
        businessInput = findViewById(R.id.businessInput);
        amountInput = findViewById(R.id.amountInput);
        notesInput = findViewById(R.id.notesInput);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "finance-tracker-db")
                .allowMainThreadQueries()
                .build();

        amountInput.setFilters(new InputFilter[]{
                new DecimalDigitsInputFilter(2)
        });

        categories.add("Groceries");
        categories.add("Gas");
        categories.add("Restaurants");
        categories.add("Bills");
        categories.add("Entertainment");
        categories.add("Add Category");

        categoryButton.setOnClickListener(v -> showCategoryDialog());

        saveExpenseButton.setOnClickListener(v -> {
            String business = businessInput.getText().toString().trim();
            String amount = amountInput.getText().toString().trim();
            String notes = notesInput.getText().toString().trim();

            if (selectedCategory.isEmpty()) {
                Toast.makeText(this, "Please choose a category", Toast.LENGTH_SHORT).show();
                return;
            }

            if (business.isEmpty()) {
                Toast.makeText(this, "Please enter a business name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double expenseAmount = Double.parseDouble(amount);

            Expense expense = new Expense(selectedCategory, business, expenseAmount, notes);
            db.expenseDao().insert(expense);

            Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show();

            businessInput.setText("");
            amountInput.setText("");
            notesInput.setText("");
            selectedCategory = "";
            categoryButton.setText("Choose Category");
        });
    }

    private void showCategoryDialog() {
        String[] categoryArray = categories.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");

        builder.setItems(categoryArray, (dialog, which) -> {
            String chosenCategory = categories.get(which);

            if (chosenCategory.equals("Add Category")) {
                showAddCategoryDialog();
            } else {
                selectedCategory = chosenCategory;
                categoryButton.setText(selectedCategory);
            }
        });

        builder.show();
    }

    private void showAddCategoryDialog() {
        final EditText input = new EditText(this);
        input.setHint("Enter new category");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newCategory = input.getText().toString().trim();

            if (!newCategory.isEmpty() && !categories.contains(newCategory)) {
                categories.add(categories.size() - 1, newCategory);
                selectedCategory = newCategory;
                categoryButton.setText(selectedCategory);
            } else if (categories.contains(newCategory)) {
                Toast.makeText(this, "Category already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Category cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}