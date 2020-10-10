package com.kalinesia.tubesmobile.complaint;

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

public class ComplaintDetailFragment extends Fragment {
    View view;
    String idPelanggan,namaPelanggan,latitude,longitude,alamatPelanggan,action, valIdPelanggan,laporan;
    EditText etLaporan;
    Button acSave,acDelete;
    ImageView acBack;
    TextView headerForm;
    DatabaseReference mDatabase;
    FirebaseAuth auth;
    FirebaseUser user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.complain_form, container, false);
        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etLaporan = view.findViewById(R.id.laporan);
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
            laporan = bundle.getString("laporan", null);
            action = bundle.getString("action", null);

            etLaporan.setText(laporan);

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
                final String valLaporan = etLaporan.getText().toString();
                if("".equals(valIdPelanggan)){
                    Snackbar.make(view, "ID Pelanggan wajib isi", Snackbar.LENGTH_LONG).show();
                }else if("".equals(valLaporan)){
                    Snackbar.make(view, "Laporan wajib isi", Snackbar.LENGTH_LONG).show();
                }else{
                    if("update".equals(action)){
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Complaint").child(idPelanggan);
                    }else{
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Complaint").push();
                    }
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("idPelanggan").setValue(idPelanggan);
                            dataSnapshot.getRef().child("emailPelanggan").setValue(idPelanggan+"@pln.co.id");
                            dataSnapshot.getRef().child("namaPelanggan").setValue(namaPelanggan);
                            dataSnapshot.getRef().child("latitude").setValue(latitude);
                            dataSnapshot.getRef().child("longitude").setValue(longitude);
                            dataSnapshot.getRef().child("alamatPelanggan").setValue(alamatPelanggan);
                            dataSnapshot.getRef().child("isiLaporan").setValue(valLaporan);
                            dataSnapshot.getRef().child("statusLaporan").setValue(0);
                            Snackbar.make(view, "Berhasil Membuat Laporan", Snackbar.LENGTH_LONG).show();
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
