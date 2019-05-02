package com.example.lazismu.ketek.adapter.spiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lazismu.ketek.R;
import com.example.lazismu.ketek.model.DataAmbulan2Item;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    List<DataAmbulan2Item> data;

    public CustomAdapter(Context context, List<DataAmbulan2Item> data) {
        this.context = context;
        this.data = data;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_id_ambulan, null);
        TextView names = view.findViewById(R.id.textView);

        names.setText(data.get(i).getNama());
        return view;
    }
}
