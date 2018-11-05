package com.example.enjoy.healthy02.Sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.enjoy.healthy02.MenuFragment;
import com.example.enjoy.healthy02.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SleepFragment extends Fragment{

    private FirebaseAuth mAuth;
    private String uid;
    private ArrayList<Sleep> sleeps;

    public SleepFragment() {
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        this.sleeps = new ArrayList<Sleep>();

    }

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep
                , container
                , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackTomenu();
        initSleepList();
        initAddSleep();
    }

    public void initBackTomenu(){
        Button _backButton = getView().findViewById(R.id.sleep_back_to_menu_btn);
        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void initAddSleep(){
        Button _addButton = getView().findViewById(R.id.sleep_add_button);
        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SleepFormFragment sleepFormFragment = new SleepFormFragment();
                sleepFormFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepFormFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void initSleepList(){

        //sleeps.clear();


        final ListView listViewSleeps = getView().findViewById(R.id.sleep_list);

        final SleepAdapter _sleepAdapter = new SleepAdapter(getActivity(),
                R.layout.fragment_sleep_item,
                sleeps);

        Log.d("adpter", "set Adapter");
        SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS sleepRelation (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid VARCHAR(200), date VARCHAR(200), bed VARCHAR(200), wake VARCHAR(200))");

        final Cursor myCursor = db.rawQuery("select date, bed, wake, _id from sleepRelation",
                null);
        Log.d("adpter", "number of row " + String.valueOf(myCursor.getCount()));
        while (myCursor.moveToNext()){
            String date = myCursor.getString(0);
            String bed = myCursor.getString(1);
            String wake = myCursor.getString(2);
            sleeps.add(new Sleep(date, bed, wake));
        }



//        Log.d("adpter", sleeps.get(2).getDate());

        listViewSleeps.setAdapter(_sleepAdapter);

        Log.d("adpter", "after set adp");

        _sleepAdapter.notifyDataSetChanged();
        listViewSleeps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCursor.moveToPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("sleep_id", myCursor.getString(3));
                bundle.putString("sleepDate", myCursor.getString(0));
                bundle.putString("sleepBed", myCursor.getString(1));
                bundle.putString("sleepWake", myCursor.getString(2));

                SleepFormFragment sleepFormFragment = new SleepFormFragment();
                sleepFormFragment.setArguments(bundle);
                myCursor.close();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepFormFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        db.close();

    }

}
