package com.example.dairymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SelectMemberActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayList<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_member);
        listView = findViewById(R.id.memberListView);
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMembers();
    }

    private void loadMembers() {

        memberList = dbHelper.getAllMemberNames();

        if (memberList.size() == 0) {
            Toast.makeText(this,
                    "No members found. Please add member first.",
                    Toast.LENGTH_LONG).show();
            listView.setAdapter(null);
            return;
        }

        // 🔥 BIGGER ITEMS - easy to tap
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_milk_entry,
                R.id.memberNameText,
                memberList
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = memberList.get(position);
            Intent intent = new Intent(this, MilkEntryActivity.class);
            intent.putExtra("memberName", selectedName);
            startActivity(intent);
        });
    }
}