package com.example.enjoy.healthy02;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMIFragment extends Fragment {
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi
                , container
                , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitCalcBtn();
        InitBackToMenuBtn();
    }

    public void InitCalcBtn(){
        final Button _btn =  (Button) getView().findViewById(R.id.BMI_BMI_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView _BMIshow = (TextView) getView().findViewById(R.id.BMI_show);
                EditText _height = (EditText) getView().findViewById(R.id.BMI_height);
                EditText _weight = (EditText) getView().findViewById(R.id.BMI_weight);
                String _heightStr = _height.getText().toString();
                Float _heightF = Float.parseFloat(_heightStr);
                Float _weightF = Float.parseFloat(_weight.getText().toString());
                Float _bmi =  _weightF / ((_heightF / 100 ) * (_heightF /  100));
//                Log.d("USER", );
                ;
                _BMIshow.setText("Your BMI\n" + _bmi.toString());
            }
        });
    }

    public void InitBackToMenuBtn(){
        Button _backBtn = getView().findViewById(R.id.BMI_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
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
}
