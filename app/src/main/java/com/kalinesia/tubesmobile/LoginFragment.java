package com.kalinesia.tubesmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalinesia.tubesmobile.Customer.CustomerModel;
import com.kalinesia.tubesmobile.Maps.HomeFragment;
import com.kalinesia.tubesmobile.User.UserModel;
import com.kalinesia.tubesmobile.complaint.ComplaintFragment;
import com.kalinesia.tubesmobile.complaint.ComplaintModel;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    View view;
    TextInputEditText textInputEmail,editTextPassword;
    Button btnLogin;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    SharedPreferences pref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textInputEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        btnLogin = view.findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(getContext());
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref = view.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String session_user =  pref.getString("session_user","");
        progressDialog.setMessage("Session Check...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style icon Spinner
        progressDialog.show();
        if(!"".equals(session_user)){
            final String[] separated = session_user.split("@");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Customers").child(separated[0].trim());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0){
                        CustomerModel data = dataSnapshot.getValue(CustomerModel.class);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("idPelanggan", data.getIdPelanggan());
                        editor.putString("namaPelanggan", data.getNamaPelanggan());
                        editor.putString("latitude", data.getLatitude());
                        editor.putString("longitude", data.getLongitude());
                        editor.putString("alamatPelanggan", data.getAlamatPelanggan());
                        editor.commit();
                        ((MainActivity) getActivity()).loadFragmentNoBack(new ComplaintFragment(),null);
                    }else{
                        ((MainActivity) getActivity()).loadFragmentNoBack(new HomeFragment(),null);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            progressDialog.dismiss();
        }
        loginUser();

    }

    private void loginUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = textInputEmail.getText().toString();
                final String password = editTextPassword.getText().toString();

                if(email.isEmpty()){
                    textInputEmail.setError("Email harus diisi");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textInputEmail.setError("Email tidak valid");
                }else if(password.isEmpty()){
                    editTextPassword.setError("Password harus diisi");
                }else if(password.length()<6){
                    editTextPassword.setError("Password minimal 6 karakter");
                }else{
                    progressDialog.setMessage("Process ...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style icon Spinner
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (!task.isSuccessful()) {
                                        Snackbar.make(view,"Email atau password belum sesuai",Snackbar.LENGTH_LONG).show();
//                                        Toast.makeText(MainActivity.class,"Register GAGAL, karena "
//                                                ,Toast.LENGTH_LONG).show();
                                    } else {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("session_user", email);
                                        editor.putString("session_key", password);
                                        editor.commit();
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(email.replace(".",","));
                                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getChildrenCount()>0){
                                                    ((MainActivity) getActivity()).loadFragmentNoBack(new LoginFragment(),null);
                                                }else{
                                                    ((MainActivity) getActivity()).loadFragmentNoBack(new ComplaintFragment(),null);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }
}
