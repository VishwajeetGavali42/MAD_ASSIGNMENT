package com.example.dairymanagement;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.*;

public class MemberTotalActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_total);

        listView = findViewById(R.id.totalListView);
        dbHelper = new DBHelper(this);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        ArrayList<MilkRecord> records = dbHelper.getAllRecords();

        // AMOUNT maps
        HashMap<String, Double> todayMap   = new HashMap<>();
        HashMap<String, Double> monthMap   = new HashMap<>();
        HashMap<String, Double> yearMap    = new HashMap<>();
        HashMap<String, Double> totalMap   = new HashMap<>();

        // LITRES maps
        HashMap<String, Double> todayLitresMap  = new HashMap<>();
        HashMap<String, Double> monthLitresMap  = new HashMap<>();
        HashMap<String, Double> yearLitresMap   = new HashMap<>();
        HashMap<String, Double> totalLitresMap  = new HashMap<>();

        Calendar cal = Calendar.getInstance();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(cal.getTime());
        String currentMonth = new SimpleDateFormat("MM", Locale.getDefault())
                .format(cal.getTime());
        String currentYear = new SimpleDateFormat("yyyy", Locale.getDefault())
                .format(cal.getTime());

        for (MilkRecord m : records) {

            String name = m.name;

            // TOTAL AMOUNT
            totalMap.put(name, totalMap.getOrDefault(name, 0.0) + m.amount);

            // TOTAL LITRES
            totalLitresMap.put(name, totalLitresMap.getOrDefault(name, 0.0) + m.litres);

            // TODAY
            if (m.date.equals(today)) {
                todayMap.put(name, todayMap.getOrDefault(name, 0.0) + m.amount);
                todayLitresMap.put(name, todayLitresMap.getOrDefault(name, 0.0) + m.litres);
            }

            // THIS MONTH
            if (m.date.length() >= 7 && m.date.substring(5, 7).equals(currentMonth)) {
                monthMap.put(name, monthMap.getOrDefault(name, 0.0) + m.amount);
                monthLitresMap.put(name, monthLitresMap.getOrDefault(name, 0.0) + m.litres);
            }

            // THIS YEAR
            if (m.date.length() >= 4 && m.date.substring(0, 4).equals(currentYear)) {
                yearMap.put(name, yearMap.getOrDefault(name, 0.0) + m.amount);
                yearLitresMap.put(name, yearLitresMap.getOrDefault(name, 0.0) + m.litres);
            }
        }

        ArrayList<MemberTotalModel> list = new ArrayList<>();

        for (String name : totalMap.keySet()) {
            list.add(new MemberTotalModel(
                    name,
                    todayMap.getOrDefault(name, 0.0),
                    monthMap.getOrDefault(name, 0.0),
                    yearMap.getOrDefault(name, 0.0),
                    totalMap.getOrDefault(name, 0.0),
                    todayLitresMap.getOrDefault(name, 0.0),
                    monthLitresMap.getOrDefault(name, 0.0),
                    yearLitresMap.getOrDefault(name, 0.0),
                    totalLitresMap.getOrDefault(name, 0.0)
            ));
        }

        Collections.sort(list, (a, b) -> Double.compare(b.total, a.total));

        MemberTotalAdapter adapter = new MemberTotalAdapter(this, list);
        listView.setAdapter(adapter);
    }
}