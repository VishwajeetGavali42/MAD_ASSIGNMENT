package com.example.dairymanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateReportActivity extends AppCompatActivity {

    // ✅ FIXED: LinearLayout instead of Button
    LinearLayout datePickerBtn;
    ListView listView;
    TextView totalText;
    TextView selectedDateText;

    DBHelper dbHelper;
    ArrayList<MilkRecord> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_report);

        // ✅ FIXED: correct IDs matching new XML
        datePickerBtn = findViewById(R.id.datePickerBtn);
        listView = findViewById(R.id.dateReportList);
        totalText = findViewById(R.id.totalText);
        selectedDateText = findViewById(R.id.selectedDateText);

        dbHelper = new DBHelper(this);

        datePickerBtn.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);

                    String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(selected.getTime());

                    // ✅ Update button text to show selected date
                    selectedDateText.setText(selectedDate);

                    loadData(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    private void loadData(String date) {
        ArrayList<MilkRecord> allRecords = dbHelper.getAllRecords();

        recordList = new ArrayList<>();
        double total = 0;

        for (MilkRecord m : allRecords) {
            if (m.date.equals(date)) {
                recordList.add(m);
                total += m.amount;
            }
        }

        // ✅ FIXED: Custom adapter using item_milk.xml instead of plain simple_list_item_1
        MilkRecordAdapter adapter = new MilkRecordAdapter(recordList);
        listView.setAdapter(adapter);

        totalText.setText("₹ " + String.format("%.2f", total));
    }

    // ✅ Custom adapter for beautiful card items
    private class MilkRecordAdapter extends BaseAdapter {
        ArrayList<MilkRecord> list;

        MilkRecordAdapter(ArrayList<MilkRecord> list) {
            this.list = list;
        }

        @Override
        public int getCount() { return list.size(); }

        @Override
        public Object getItem(int position) { return list.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(DateReportActivity.this)
                        .inflate(R.layout.item_milk, parent, false);
            }

            MilkRecord m = list.get(position);

            TextView name = convertView.findViewById(R.id.name);
            TextView details = convertView.findViewById(R.id.details);
            TextView amount = convertView.findViewById(R.id.amount);

            name.setText(m.name);
            details.setText(m.litres + " L  •  Fat: " + m.fat + "  •  " + m.type);
            amount.setText("₹ " + String.format("%.2f", m.amount));

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listView != null) listView.setAdapter(null);
        if (totalText != null) totalText.setText("₹ 0.00");
        if (selectedDateText != null) selectedDateText.setText("Select Date");
    }
}