package com.example.dairymanagement;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MilkEntryActivity extends AppCompatActivity {

    EditText memberName, litres, fat, rate;
    Button calcBtn;
    TextView result;
    Spinner milkType;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_entry);

        memberName = findViewById(R.id.memberName);
        litres = findViewById(R.id.litres);
        fat = findViewById(R.id.fat);
        rate = findViewById(R.id.rate);
        calcBtn = findViewById(R.id.calcBtn);
        result = findViewById(R.id.result);
        milkType = findViewById(R.id.milkType);

        dbHelper = new DBHelper(this);

        // Auto fill name from SelectMemberActivity
        String selectedName = getIntent().getStringExtra("memberName");
        if (selectedName != null) {
            memberName.setText(selectedName);
        }

        String[] types = {"Select Type", "Cow", "Buffalo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milkType.setAdapter(adapter);

        calcBtn.setOnClickListener(v -> {

            String nameText = memberName.getText().toString().trim();
            String l = litres.getText().toString().trim();
            String r = rate.getText().toString().trim();

            if (nameText.isEmpty()) {
                memberName.setError("Enter name");
                return;
            }
            if (l.isEmpty()) {
                litres.setError("Enter litres");
                return;
            }
            if (r.isEmpty()) {
                rate.setError("Enter rate");
                return;
            }
            if (milkType.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select milk type", Toast.LENGTH_SHORT).show();
                return;
            }

            double litresVal = Double.parseDouble(l);
            double rateVal = Double.parseDouble(r);
            double fatVal = fat.getText().toString().isEmpty() ? 0 :
                    Double.parseDouble(fat.getText().toString());
            double amount = litresVal * rateVal;

            String type = milkType.getSelectedItem().toString();
            String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(new Date());

            dbHelper.insertRecord(nameText, litresVal, fatVal, amount, todayDate, type);

            result.setText("Amount: ₹ " + String.format("%.2f", amount));

            Toast.makeText(this, "Saved ✅", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}