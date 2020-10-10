package com.kalinesia.tubesmobile.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalinesia.tubesmobile.MainActivity;
import com.kalinesia.tubesmobile.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserDetailFragment extends Fragment {
    View view;
    String emailPetugas,namaPetugas,passwordPetugas,action;
    EditText etEmailPetugas,etNamaPetugas,etPasswordPetugas;
    Button acSave,acDelete;
    ImageView acBack;
    TextView headerForm;
    DatabaseReference mDatabase;
    FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_form, container, false);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etEmailPetugas = view.findViewById(R.id.emailPetugas);
        etNamaPetugas = view.findViewById(R.id.namaPetugas);
        etPasswordPetugas = view.findViewById(R.id.passwordPetugas);
        acSave = view.findViewById(R.id.acSave);
        acDelete = view.findViewById(R.id.acDelete);
        acBack = view.findViewById(R.id.acBack);
        headerForm = view.findViewById(R.id.headerForm);

        actionSave();
        actionDelete();
        actionBack();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            emailPetugas = bundle.getString("emailPetugas", null);
            namaPetugas = bundle.getString("namaPetugas", null);
            passwordPetugas = bundle.getString("passwordPetugas", null);
            action = bundle.getString("action", null);

            etEmailPetugas.setText(emailPetugas);
            etNamaPetugas.setText(namaPetugas);
            etPasswordPetugas.setText(passwordPetugas);

            if("update".equals(action)){
                acDelete.setVisibility(View.VISIBLE);
                headerForm.setText("Update Data Petugas");
            }
        }
    }

    private void actionSave(){
        acSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String valEmailPetugas = etEmailPetugas.getText().toString();
                final String valNamaPetugas = etNamaPetugas.getText().toString();
                final String valPasswordPetugas = etPasswordPetugas.getText().toString();
                if("".equals(valEmailPetugas)){
                    Snackbar.make(view, "Email Petugas wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valNamaPetugas)){
                    Snackbar.make(view, "Nama Petugas wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valPasswordPetugas)){
                    Snackbar.make(view, "Password isi", Snackbar.LENGTH_LONG).show();
                }else{
                    if("update".equals(action)){
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(emailPetugas.replace(".", ","));
                    }else{
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(valEmailPetugas.replace(".", ","));
                    }
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            deleteUser(1);
                            dataSnapshot.getRef().child("emailPetugas").setValue(valEmailPetugas);
                            dataSnapshot.getRef().child("namaPetugas").setValue(valNamaPetugas);
                            dataSnapshot.getRef().child("passwordPetugas").setValue(valPasswordPetugas);
                            Snackbar.make(view, "Data "+namaPetugas+" Berhasil di Perbarui", Snackbar.LENGTH_LONG).show();
                            ((MainActivity) getActivity()).onBackPressed();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void deleteUser(final int ac){
        if(emailPetugas!=null && passwordPetugas!=null){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(emailPetugas, passwordPetugas);
            // Prompt the user to re-provide their sign-in credentials
//            user.reauthenticate(credential)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            user.delete()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
////                                                Log.d(TAG, "User account deleted.");
//                                                if(ac>0){
//                                                    saveUser();
//                                                }
//                                            }
//                                        }
//                                    });
//
//                        }
//                    });
        }else{
            saveUser();
        }
    }
    private void saveUser(){
        auth.createUserWithEmailAndPassword(etEmailPetugas.getText().toString(),etPasswordPetugas.getText().toString());
    }

    private void actionDelete(){
        acDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(0);
                FirebaseDatabase.getInstance().getReference().child("Users").child(emailPetugas.replace(".",",")).removeValue();
                Snackbar.make(view, "Data "+namaPetugas+" Berhasil di Hapus", Snackbar.LENGTH_LONG).show();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

    private void actionBack(){
        acBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }
}
