package com.example.dairymanagement;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MemberTotalAdapter extends ArrayAdapter<MemberTotalModel> {

    public MemberTotalAdapter(Context context, ArrayList<MemberTotalModel> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_member_total, parent, false);
        }

        MemberTotalModel m = getItem(position);

        TextView name        = convertView.findViewById(R.id.nameText);
        TextView today       = convertView.findViewById(R.id.todayText);
        TextView month       = convertView.findViewById(R.id.monthText);
        TextView year        = convertView.findViewById(R.id.yearText);
        TextView total       = convertView.findViewById(R.id.totalText);
        TextView todayLitres = convertView.findViewById(R.id.todayLitresText);
        TextView monthLitres = convertView.findViewById(R.id.monthLitresText);
        TextView yearLitres  = convertView.findViewById(R.id.yearLitresText);
        TextView totalLitres = convertView.findViewById(R.id.totalLitresText);

        name.setText((position + 1) + ". " + m.name);

        // AMOUNT
        today.setText("Today Amount: ₹ " + String.format("%.2f", m.today));
        month.setText("This Month Amount: ₹ " + String.format("%.2f", m.monthly));
        year.setText("This Year Amount: ₹ " + String.format("%.2f", m.yearly));
        total.setText("Total Amount: ₹ " + String.format("%.2f", m.total));

        // LITRES
        todayLitres.setText("Today Litres: " + String.format("%.2f", m.todayLitres) + " L");
        monthLitres.setText("This Month Litres: " + String.format("%.2f", m.monthlyLitres) + " L");
        yearLitres.setText("This Year Litres: " + String.format("%.2f", m.yearlyLitres) + " L");
        totalLitres.setText("Total Litres: " + String.format("%.2f", m.totalLitres) + " L");

        // Highlight top member
        if (position == 0) {
            convertView.setBackgroundColor(0xFFE8F5E9);
        } else {
            convertView.setBackgroundColor(0xFFFFFFFF);
        }

        return convertView;
    }
}