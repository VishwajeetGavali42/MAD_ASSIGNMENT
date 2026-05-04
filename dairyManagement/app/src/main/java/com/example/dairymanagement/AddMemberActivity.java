package com.example.dairymanagement;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddMemberActivity extends AppCompatActivity {

    EditText memberName;
    Button saveBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        memberName = findViewById(R.id.memberName);
        saveBtn = findViewById(R.id.saveBtn);

        dbHelper = new DBHelper(this);

        saveBtn.setOnClickListener(v -> {

            String name = memberName.getText().toString().trim();

            if (name.isEmpty()) {
                memberName.setError("Enter name");
                return;
            }

            dbHelper.addMember(name);

            Toast.makeText(this, "Member Added ✅", Toast.LENGTH_SHORT).show();

            memberName.setText(""); // clear field
            finish();
        });
    }
}