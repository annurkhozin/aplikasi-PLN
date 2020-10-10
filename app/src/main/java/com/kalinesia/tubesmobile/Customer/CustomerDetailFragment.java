package com.kalinesia.tubesmobile.Customer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
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

public class CustomerDetailFragment extends Fragment {
    View view;
    String idPelanggan,namaPelanggan,latitude,longitude,alamatPelanggan,action, valIdPelanggan;
    EditText etIdPelanggan,etNamaPelanggan,etLatitude,etLongitude,etAlamatPelanggan;
    Button acSave,acDelete;
    ImageView acBack;
    TextView headerForm;
    DatabaseReference mDatabase;
    FirebaseAuth auth;
    FirebaseUser user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.customer_form, container, false);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etIdPelanggan = view.findViewById(R.id.idPelanggan);
        etNamaPelanggan = view.findViewById(R.id.namaPelanggan);
        etLatitude = view.findViewById(R.id.latitude);
        etLongitude = view.findViewById(R.id.longitude);
        etAlamatPelanggan = view.findViewById(R.id.alamatPelanggan);
        acSave = view.findViewById(R.id.acSave);
        acDelete = view.findViewById(R.id.acDelete);
        acBack = view.findViewById(R.id.acBack);
        headerForm = view.findViewById(R.id.headerForm);

        actionSave();
        actionDelete();
        actionBack();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idPelanggan = bundle.getString("idPelanggan", null);
            namaPelanggan = bundle.getString("namaPelanggan", null);
            latitude = bundle.getString("latitude", null);
            longitude = bundle.getString("longitude", null);
            alamatPelanggan = bundle.getString("alamatPelanggan", null);
            action = bundle.getString("action", null);

            etIdPelanggan.setText(idPelanggan);
            etNamaPelanggan.setText(namaPelanggan);
            etLatitude.setText(latitude);
            etLongitude.setText(longitude);
            etAlamatPelanggan.setText(alamatPelanggan);

            if("update".equals(action)){
                acDelete.setVisibility(View.VISIBLE);
                headerForm.setText("Update Data Pelanggan");
            }
        }
    }

    private void actionSave(){
        acSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valIdPelanggan = etIdPelanggan.getText().toString();
                final String valNamaPelanggan = etNamaPelanggan.getText().toString();
                final String valLatitude = etLatitude.getText().toString();
                final String valLongitude = etLongitude.getText().toString();
                final String valAlamatPelanggan = etAlamatPelanggan.getText().toString();
                if("".equals(valIdPelanggan)){
                    Snackbar.make(view, "ID Pelanggan wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valNamaPelanggan)){
                    Snackbar.make(view, "Nama Pelanggan wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valLatitude)){
                    Snackbar.make(view, "Latitude wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valLongitude)){
                    Snackbar.make(view, "Longitude wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valAlamatPelanggan)){
                    Snackbar.make(view, "Alamat Pelanggan wajib isi", Snackbar.LENGTH_LONG).show();
                }else{
                    if("update".equals(action)){
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customers").child(idPelanggan);
                    }else{
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customers").child(valIdPelanggan);
                    }
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            deleteCustomer(1);
                            dataSnapshot.getRef().child("idPelanggan").setValue(valIdPelanggan);
                            dataSnapshot.getRef().child("namaPelanggan").setValue(valNamaPelanggan);
                            dataSnapshot.getRef().child("latitude").setValue(valLatitude);
                            dataSnapshot.getRef().child("longitude").setValue(valLongitude);
                            dataSnapshot.getRef().child("alamatPelanggan").setValue(valAlamatPelanggan);
                            Snackbar.make(view, "Data "+namaPelanggan+" Berhasil di Perbarui", Snackbar.LENGTH_LONG).show();
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

    private void deleteCustomer(final int ac){
        if(idPelanggan!=null){
            user = auth.getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(idPelanggan+"@pln.co.id", idPelanggan);
//            Log.i("ccccc",idPelanggan);
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
                                                    saveCustomer();
//                                                }
//                                            }
//                                        }
//                                    });
//
//                        }
//                    });
        }else{
            saveCustomer();
        }
    }
    private void actionDelete(){
        acDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Customers").child(idPelanggan).removeValue();
                Snackbar.make(view, "Data "+namaPelanggan+" Berhasil di Hapus", Snackbar.LENGTH_LONG).show();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }
    private void saveCustomer(){
        auth.createUserWithEmailAndPassword(valIdPelanggan+"@pln.co.id",valIdPelanggan);
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
