package com.example.enjoy.healthy02;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,
                container,
                false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button _registerBtn = getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _repassword = getView().findViewById(R.id.register_repassword);

                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();
                String _repasswordStr = _repassword.getText().toString();

                if(_emailStr.isEmpty() || _passwordStr.isEmpty() || _repasswordStr.isEmpty()){

                    Log.d("REGISTER",
                            "EMAIL or Password or Re password is empty.");

                    Toast.makeText(getActivity(),
                            "Please fill in all information.",
                            Toast.LENGTH_LONG).show();
                }else if(_passwordStr.length() < 6){

                    Log.d("REGISTER",
                            "password less 6 characters");

                    Toast.makeText(getActivity(),
                            "Please fill password or repassword at least 6 characters.",
                            Toast.LENGTH_SHORT).show();
                }else if(_passwordStr.equals(_repasswordStr) == false){

                    Log.d("REGISTER",
                            "Password not Match rrepassword");

                    Toast.makeText(getActivity(),
                            "Password and Re Password not match",
                            Toast.LENGTH_SHORT).show();
                }else{

                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser _user = authResult.getUser();
                            Toast.makeText(getActivity(), "Register done", Toast.LENGTH_SHORT).show();
                            sendVerifiedEmail(_user);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("REGISTER", e.getMessage());
                            Toast.makeText(getActivity(),
                                    e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });


    }


    void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            public void onSuccess(Void aVoid) {
                mAuth.signOut();
                Log.d("REGISTER", "GOTO Login");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }





}
