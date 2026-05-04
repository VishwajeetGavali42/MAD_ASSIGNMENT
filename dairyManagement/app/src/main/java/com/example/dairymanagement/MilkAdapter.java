package com.example.dairymanagement;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MilkAdapter extends BaseAdapter {

    Context context;
    ArrayList<MilkRecord> list;

    public MilkAdapter(Context context, ArrayList<MilkRecord> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_milk, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView details = convertView.findViewById(R.id.details);
        TextView amount = convertView.findViewById(R.id.amount);

        MilkRecord m = list.get(position);

        name.setText(m.name);

        details.setText(
                m.litres + " L  •  Fat: " + m.fat +
                        "\n" + m.date + "  •  " + m.type
        );

        amount.setText("₹ " + String.format("%.2f", m.amount));

        return convertView;
    }
}