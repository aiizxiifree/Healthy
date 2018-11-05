package com.example.enjoy.healthy02.Sleep;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;

import com.example.enjoy.healthy02.R;
import com.google.firebase.auth.FirebaseAuth;

public class SleepFormFragment extends Fragment{

    private FirebaseAuth mAuth;

    private Bundle bundle;


    public SleepFormFragment() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form,
                container,
                false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showHint();

        initBack();
        initSave();



    }

    public void initBack(){
        Button backButton = getView().findViewById(R.id.sleep_form_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void initSave(){
        Button saveButton = getView().findViewById(R.id.sleep_form_save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

                EditText editTextDate = getView().findViewById(R.id.sleep_form_date);
                EditText editTextBed = getView().findViewById(R.id.sleep_form_bed);
                EditText editTextWake = getView().findViewById(R.id.sleep_form_wake);

                Log.d("form", "get view");




                db.execSQL("CREATE TABLE IF NOT EXISTS sleepRelation (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid VARCHAR(200), date VARCHAR(200), bed VARCHAR(200), wake VARCHAR(200))");
                Log.d("form", "finish exe");

                Log.d("form", "exe finish");


                String date = editTextDate.getText().toString();
                String bed = editTextBed.getText().toString();
                String wake = editTextWake.getText().toString();



                ContentValues contentValues = new ContentValues();
                contentValues.put("uid", mAuth.getCurrentUser().getUid());
                contentValues.put("date", date);
                contentValues.put("bed", bed);
                contentValues.put("wake", wake);



                Log.d("form", "put content");

                Log.d("form", String.valueOf(bundle.isEmpty()));
                if(!bundle.isEmpty()) {
                    String sleepID = bundle.getString("sleep_id");

                    db.update("sleepRelation", contentValues, "_id="+sleepID, null);

                }else{

                    db.insert("sleepRelation", null, contentValues);
                }
                db.close();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void showHint(){



        bundle = this.getArguments();

        EditText editTextDate = getView().findViewById(R.id.sleep_form_date);
        EditText editTextBed = getView().findViewById(R.id.sleep_form_bed);
        EditText editTextWake = getView().findViewById(R.id.sleep_form_wake);

        Log.d("form", "b4");


        if(bundle != null){

            String sleepID = bundle.getString("sleep_id");

            String sleepDate = bundle.getString("sleepDate");

            String sleepBed = bundle.getString("sleepBed");

            String sleepWake = bundle.getString("sleepWake");
            Log.d("form", "--->" + sleepID + sleepDate + sleepBed + sleepWake);

            editTextDate.setText(sleepDate);
            editTextBed.setText(sleepBed);
            editTextWake.setText(sleepWake);



        }
        Log.d("form", "end");

    }



}
