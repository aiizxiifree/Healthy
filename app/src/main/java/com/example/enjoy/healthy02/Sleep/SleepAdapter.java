package com.example.enjoy.healthy02.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.enjoy.healthy02.R;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {

    private Context context;
    private ArrayList<Sleep> sleeps = new ArrayList<Sleep>();

    public SleepAdapter(@NonNull Context context,
                        int resource,
                        ArrayList<Sleep> objects) {
        super(context, resource, objects);
        this.context = context;
        this.sleeps =  objects;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View sleepItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_sleep_item,
                parent,
                false
        );

        TextView dateViw = sleepItem.findViewById(R.id.sleep_item_date);
        TextView bedView = sleepItem.findViewById(R.id.sleep_item_bed_wake);
        TextView statusView = sleepItem.findViewById(R.id.sleep_item_status);

        Sleep row = sleeps.get(position);
        Log.d("adpter", row.getDate());
        dateViw.setText(row.getDate());
        bedView.setText(row.getBed() + "-" + row.getWake());
        String calTotal = calculateTotal(row.getBed(), row.getWake());
        row.setTotal(calTotal);
        statusView.setText(row.getTotal());


        return sleepItem;
    }

    public String calculateTotal(String bed, String wake){

        Boolean stateCalHour = true;
        Boolean stateCalMinute = true;

        String bedHour = bed.split(":")[0];
        String bedMinute = bed.split(":")[1];
        String[] wakeArray = wake.split(":");
        int wakeHourInt = Integer.parseInt(wakeArray[0]);
        int wakeMinuteInt = Integer.parseInt(wakeArray[1]);
        int bedHourInt = Integer.parseInt(bedHour);
        int bedMinuteInt = Integer.parseInt(bedMinute);
        Log.d("adpter", bedHour+ " : " + bedMinute);
        int countHour = 0, countMinute = 0;

        while(stateCalHour){
            if((bedHourInt+countHour) % 24 == wakeHourInt && (bedMinuteInt + countMinute) % 60 == wakeMinuteInt){
                break;
            }else{
                countMinute++;
                if((bedMinuteInt + countMinute) % 60 == 0){
                    bedHourInt++;
                }
                if(countMinute == 0){
                    countHour = Integer.parseInt(String.valueOf(countMinute / 60));

                }
            }

        }






        String total = String.format("%02d:%02d", countHour, countMinute);

        Log.d("adpter", "total" + total);

        return total;

    }

}
