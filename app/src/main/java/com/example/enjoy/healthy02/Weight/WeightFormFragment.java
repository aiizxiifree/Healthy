package com.example.enjoy.healthy02.Weight;

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
import android.widget.Toast;

import com.example.enjoy.healthy02.MenuFragment;
import com.example.enjoy.healthy02.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WeightFormFragment extends Fragment{


    private FirebaseFirestore fFirestore;
    private FirebaseAuth mAuth;

    public WeightFormFragment() {
        mAuth = FirebaseAuth.getInstance();
        fFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onClickBack();
            onClickAdd();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    public void onClickAdd() {
        Button btn = getView().findViewById(R.id.weight_form_save_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _uid = mAuth.getCurrentUser().getUid();

                // EditText
                EditText _weight = getView().findViewById(R.id.weight_form_weight);
                EditText _date = getView().findViewById(R.id.weight_form_date);

                // String
                String _weightString = _weight.getText().toString();
                String _dateString = _date.getText().toString();

                // Weight object
                Weight _data = new Weight(_dateString, "UP", String.format("%.2f", Double.parseDouble(_weightString)));

                // Check empty String
                if (_weightString.isEmpty() || _dateString.isEmpty()) {
                    Log.d("WEIGHTFROM", "weight or date is empty");
                    Toast.makeText(getActivity(), "Weight or Date is empty", Toast.LENGTH_SHORT).show();
                } else {
                    fFirestore.collection("myfitness")
                            .document(_uid)
                            .collection("weight")
                            .document(_dateString)
                            .set(_data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("WEIGHTFROM", "Data Saved!!");
                                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, new WeightFragment())
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("WEIGHTFROM", "Data save failure :(");
                            Toast.makeText(getActivity(), "Save data failure = " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public void onClickBack() {
        Button btn = getView().findViewById(R.id.weight_form_back_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }



}
