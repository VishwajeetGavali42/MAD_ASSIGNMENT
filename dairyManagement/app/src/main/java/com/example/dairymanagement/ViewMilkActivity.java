package com.example.dairymanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewMilkActivity extends AppCompatActivity {

    ListView listView;
    TextView totalAmount;
    ArrayList<MilkRecord> recordList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_milk);

        listView = findViewById(R.id.milkListView);
        totalAmount = findViewById(R.id.totalAmountText);

        dbHelper = new DBHelper(this);

        loadData();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            MilkRecord m = recordList.get(position);

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_record, null);

            EditText editName = dialogView.findViewById(R.id.editName);
            EditText editLitres = dialogView.findViewById(R.id.editLitres);
            EditText editFat = dialogView.findViewById(R.id.editFat);
            EditText editRate = dialogView.findViewById(R.id.editRate);

            editName.setText(m.name);
            editLitres.setText(String.valueOf(m.litres));
            editFat.setText(String.valueOf(m.fat));

            double currentRate = (m.litres != 0) ? (m.amount / m.litres) : 0;
            editRate.setText(String.format("%.2f", currentRate));

            new AlertDialog.Builder(this)
                    .setTitle("Edit Record")
                    .setView(dialogView)

                    .setPositiveButton("Update", (dialog, which) -> {
                        try {
                            String n = editName.getText().toString().trim();
                            double l = Double.parseDouble(editLitres.getText().toString().trim());
                            double f = Double.parseDouble(editFat.getText().toString().trim());
                            double r = Double.parseDouble(editRate.getText().toString().trim());

                            double amount = l * r;

                            dbHelper.updateRecord(m.id, n, l, f, amount);

                            Toast.makeText(this, "Updated ✅", Toast.LENGTH_SHORT).show();
                            loadData();

                        } catch (Exception e) {
                            Toast.makeText(this, "Invalid input ❌", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .setNegativeButton("Delete", (dialog, which) -> {
                        new AlertDialog.Builder(this)
                                .setTitle("Delete Record?")
                                .setMessage("Are you sure you want to delete this record?")
                                .setPositiveButton("Yes", (d, w) -> {
                                    dbHelper.deleteRecord(m.id);
                                    Toast.makeText(this, "Deleted ✅", Toast.LENGTH_SHORT).show();
                                    loadData();
                                })
                                .setNegativeButton("No", null)
                                .show();
                    })
                    .show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        recordList = dbHelper.getAllRecords();

        double total = 0;

        for (MilkRecord m : recordList) {
            total += m.amount;
        }

        totalAmount.setText("₹ " + String.format("%.2f", total));

        // ✅ IMPORTANT: Use custom adapter
        MilkAdapter adapter = new MilkAdapter(this, recordList);
        listView.setAdapter(adapter);
    }
}