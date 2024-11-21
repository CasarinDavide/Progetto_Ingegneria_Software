package com.example.progetto_ingegneria_software.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.progetto_ingegneria_software.R;


import java.util.List;

public class CreateTransactionAdapter extends BaseAdapter {
    private final List<Item> plants;
    private final Context context;

    public CreateTransactionAdapter(Context context, List<Item> plants) {
        this.context = context;
        this.plants = plants;
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int position) {
        return plants.get(position);
    }

    public List<Item> getItems() {
        return plants;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exchange_plants_item, parent, false);
        }

        CheckBox c = convertView.findViewById(R.id.checkbox);

        Item currentItem = plants.get(position);
        c.setText(currentItem.getLabel());

        c.setOnCheckedChangeListener( (buttonView, isChecked) -> currentItem.setChecked(isChecked));

        return convertView;
    }
}
