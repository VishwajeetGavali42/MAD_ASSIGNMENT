package com.example.dairymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, confirmPassword;
    Button registerBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.regUsername);
        password = findViewById(R.id.regPassword);
        confirmPassword = findViewById(R.id.regConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        dbHelper = new DBHelper(this);

        registerBtn.setOnClickListener(v -> {

            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();

            if (user.isEmpty()) {
                username.setError("Enter username");
                return;
            }
            if (pass.isEmpty()) {
                password.setError("Enter password");
                return;
            }
            if (confirm.isEmpty()) {
                confirmPassword.setError("Confirm password");
                return;
            }
            if (!pass.equals(confirm)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }

            boolean success = dbHelper.registerUser(user, pass);

            if (success) {
                Toast.makeText(this, "Account Created ✅ Please Login", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Username already exists ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
}