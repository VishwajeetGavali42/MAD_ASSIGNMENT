package com.example.dairymanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewMembersActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_members);

        listView = findViewById(R.id.memberListView);

        ArrayList<String> names = new ArrayList<>();

        for (Member m : Member.memberList) {
            names.add("Name: " + m.name + "\nPhone: " + m.phone + "\nVillage: " + m.village);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                names
        );

        listView.setAdapter(adapter);
    }
}