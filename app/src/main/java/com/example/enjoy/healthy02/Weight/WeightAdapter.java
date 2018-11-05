package com.example.enjoy.healthy02.Weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.enjoy.healthy02.R;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Weight> weights = new ArrayList<Weight>();


    public WeightAdapter(@NonNull Context context,
                         int resource,
                         @NonNull List<Weight> objects) {
        super(context, resource, objects);
        this.context = context;
        this.weights = (ArrayList<Weight>) objects;
    }


    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false
        );

        TextView _date = weightItem.findViewById(R.id.weight_item_date);
        TextView _weight = weightItem.findViewById(R.id.weight_item_weight);
        TextView _status = weightItem.findViewById(R.id.weight_item_status);

        Weight row = weights.get(position);

        _date.setText(row.getDate());
        _weight.setText(String.valueOf(row.getWeight()));
        _status.setText(row.getStatus());



        return weightItem;
    }
}
