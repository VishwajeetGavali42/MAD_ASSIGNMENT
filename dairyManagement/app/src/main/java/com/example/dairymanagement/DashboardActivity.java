package com.example.dairymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout addMemberBtn, milkEntryBtn, viewRecordsBtn,
            memberTotalBtn, dateReportBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addMemberBtn  = findViewById(R.id.addMemberBtn);
        milkEntryBtn  = findViewById(R.id.milkEntryBtn);
        viewRecordsBtn = findViewById(R.id.viewRecordsBtn);
        memberTotalBtn = findViewById(R.id.memberTotalBtn);
        dateReportBtn  = findViewById(R.id.dateReportBtn);
        logoutBtn      = findViewById(R.id.logoutBtn);

        addMemberBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddMemberActivity.class)));

        milkEntryBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SelectMemberActivity.class)));

        viewRecordsBtn.setOnClickListener(v ->
                startActivity(new Intent(this, ViewMilkActivity.class)));

        memberTotalBtn.setOnClickListener(v ->
                startActivity(new Intent(this, MemberTotalActivity.class)));

        dateReportBtn.setOnClickListener(v ->
                startActivity(new Intent(this, DateReportActivity.class)));

        // 🔐 LOGOUT
        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}