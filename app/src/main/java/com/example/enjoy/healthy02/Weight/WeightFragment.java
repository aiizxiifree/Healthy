package com.example.enjoy.healthy02.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enjoy.healthy02.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WeightFragment extends Fragment {

    private ArrayList<Weight> weights;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fFirestore;



    public WeightFragment() {
        this.weights = new ArrayList<Weight>();
        this.mAuth =    FirebaseAuth.getInstance();
        this.fFirestore = FirebaseFirestore.getInstance();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight,
                container,
                false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getValueFromFirebase();
        addWeightFragment();
    }



    public void addWeightFragment(){
        Button _addBtn = getView().findViewById(R.id.weight_add_weight);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }




    public void getValueFromFirebase(){
        final ListView _list = getView().findViewById(R.id.weight_list);
        weights.clear();

        final WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_item,
                weights
        );


        fFirestore
                .collection("myfitness")
                .document(mAuth.getCurrentUser().getUid())
                .collection("weight")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {

                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            if(doc.get("date") != null){
                                String _getDateFire = doc.get("date").toString();
                                String _getWeightFire = doc.get("weight").toString();
                                weights.add(new Weight(_getDateFire, "", _getWeightFire));
                            }
                        }

                        addStatus();
                        _list.setAdapter(_weightAdapter);
                    }
                });



    }

    public void addStatus(){
        int totalOb = weights.size();
        if(totalOb >= 1 ){
            for(int i = 0; i < totalOb-1 ;i++){
                if(Double.parseDouble(weights.get(i).getWeight()) < Double.parseDouble(weights.get(i+1).getWeight())){
                    weights.get(i).setStatus("DOWN");
                }else if(Double.parseDouble(weights.get(i).getWeight()) > Double.parseDouble(weights.get(i+1).getWeight())){
                    weights.get(i).setStatus("UP");
                }else{
                    weights.get(i).setStatus("");
                }
            }
            weights.get(weights.size()-1).setStatus("");
        }
    }

}
