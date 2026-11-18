package com.example.assignment1calculator; // KEEP YOUR OWN PACKAGE NAME AT THE TOP

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNum1, etNum2;
    private TextView tvResult, tvHistory;
    private final List<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        tvResult = findViewById(R.id.tvResult);
        tvHistory = findViewById(R.id.tvHistory);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnSqrt = findViewById(R.id.btnSqrt);
        Button btnSquare = findViewById(R.id.btnSquare);
        Button btnPercent = findViewById(R.id.btnPercent);
        Button btnClear = findViewById(R.id.btnClear);

        // Basic Operations
        btnAdd.setOnClickListener(v -> calculateTwoNumbers("+"));
        btnSub.setOnClickListener(v -> calculateTwoNumbers("-"));
        btnMul.setOnClickListener(v -> calculateTwoNumbers("×"));
        btnDiv.setOnClickListener(v -> calculateTwoNumbers("÷"));

        // Bonus Scientific Operations (Uses First Number)
        btnSqrt.setOnClickListener(v -> {
            Double n1 = parseNumber(etNum1);
            if (n1 == null || n1 < 0) {
                tvResult.setText("Error: Enter a valid non-negative first number");
            } else {
                double res = Math.sqrt(n1);
                tvResult.setText("Result: " + res);
                updateHistory("√" + n1 + " = " + res);
            }
        });

        btnSquare.setOnClickListener(v -> {
            Double n1 = parseNumber(etNum1);
            if (n1 == null) {
                tvResult.setText("Error: Enter first number");
            } else {
                double res = n1 * n1;
                tvResult.setText("Result: " + res);
                updateHistory(n1 + "² = " + res);
            }
        });

        btnPercent.setOnClickListener(v -> {
            Double n1 = parseNumber(etNum1);
            if (n1 == null) {
                tvResult.setText("Error: Enter first number");
            } else {
                double res = n1 / 100.0;
                tvResult.setText("Result: " + res);
                updateHistory(n1 + "% = " + res);
            }
        });

        // Clear All Fields
        btnClear.setOnClickListener(v -> {
            etNum1.getText().clear();
            etNum2.getText().clear();
            tvResult.setText(getString(R.string.default_result));
        });
    }

    private void calculateTwoNumbers(String op) {
        String s1 = etNum1.getText().toString().trim();
        String s2 = etNum2.getText().toString().trim();

        if (s1.isEmpty() || s2.isEmpty()) {
            tvResult.setText("Error: Please enter both numbers");
            return;
        }

        Double n1 = parseNumber(etNum1);
        Double n2 = parseNumber(etNum2);

        if (n1 == null || n2 == null) {
            tvResult.setText("Error: Invalid number format");
            return;
        }

        double res = 0;
        switch (op) {
            case "+":
                res = n1 + n2;
                break;
            case "-":
                res = n1 - n2;
                break;
            case "×":
                res = n1 * n2;
                break;
            case "÷":
                if (n2 == 0) {
                    tvResult.setText("Error: Cannot divide by zero");
                    return;
                }
                res = n1 / n2;
                break;
        }

        tvResult.setText("Result: " + res);
        updateHistory(n1 + " " + op + " " + n2 + " = " + res);
    }

    private Double parseNumber(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void updateHistory(String entry) {
        historyList.add(0, entry);
        StringBuilder sb = new StringBuilder();
        for (String item : historyList) {
            sb.append(item).append("\n");
        }
        tvHistory.setText(sb.toString().trim());
    }
}