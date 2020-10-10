package com.kalinesia.tubesmobile.complaint;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kalinesia.tubesmobile.Customer.CustomerAdapter;
import com.kalinesia.tubesmobile.Customer.CustomerDetailFragment;
import com.kalinesia.tubesmobile.Customer.CustomerModel;
import com.kalinesia.tubesmobile.LoginFragment;
import com.kalinesia.tubesmobile.MainActivity;
import com.kalinesia.tubesmobile.Maps.HomeFragment;
import com.kalinesia.tubesmobile.R;
import com.kalinesia.tubesmobile.User.UserFragment;

import java.util.ArrayList;

public class ComplaintFragment extends Fragment {
    View view;
    private DatabaseReference mDataCustomer;
    RecyclerView rvCustomer;
    ArrayList<ComplaintModel> list;
    ComplaintAdapter customerAdapter;
    ImageView acBack;
    SharedPreferences pref;
    TextView session_user,logout;
    FloatingActionButton addData;
    LinearLayout lvHome,lvCustomer,lvUser,lvLogOut;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.complaint_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addData = view.findViewById(R.id.addData);
        acBack = view.findViewById(R.id.acBack);
        lvHome = view.findViewById(R.id.lvHome);
        lvCustomer = view.findViewById(R.id.lvCustomer);
        lvUser = view.findViewById(R.id.lvUser);
        lvLogOut = view.findViewById(R.id.lvLogOut);
        rvCustomer = view.findViewById(R.id.rvCustomer);
        logout = view.findViewById(R.id.txt_keluar);
        rvCustomer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvCustomer.setNestedScrollingEnabled(false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref = view.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        list = new ArrayList<ComplaintModel>();
        session_user = view.findViewById(R.id.session_user);
        getComplaintList();
        setSession_user();
        actionAddData();
    }

    private void getComplaintList(){
        String session =  pref.getString("session_user","");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        Query queries = myRef.child("Complaint").orderByChild("emailPelanggan").equalTo(session);
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    ComplaintModel cus = dataSnapshot1.getValue(ComplaintModel.class);
                    list.add(cus);
                }

                customerAdapter =  new ComplaintAdapter(getActivity(),list, new ComplaintAdapter.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
//                        Snackbar.make(view, "Detail Komplain "+list.get(position).getIdPelanggan(), Snackbar.LENGTH_LONG).show();
//
//                        Bundle mBundle = new Bundle();
//                        Fragment fragment = new ComplaintDetailFragment();
//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        mBundle.putString("action", "update");
//                        mBundle.putString("idPelanggan", list.get(position).getIdPelanggan());
//                        mBundle.putString("namaPelanggan", list.get(position).getNamaPelanggan());
//                        mBundle.putString("latitude", list.get(position).getLatitude());
//                        mBundle.putString("longitude", list.get(position).getLongitude());
//                        mBundle.putString("alamatPelanggan", list.get(position).getAlamatPelanggan());
//                        mBundle.putString("isiLapran", list.get(position).getIsiLaporan());
//                        fragment.setArguments(mBundle);
//                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                        fragmentTransaction.replace(R.id.contentFragment, fragment).addToBackStack(null);
//                        fragmentTransaction.commit();
                    }

                });
                rvCustomer.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void setSession_user(){
        String session =  pref.getString("session_user","");
        if("".equals(session)){
            ((MainActivity) getActivity()).loadFragmentNoBack(new LoginFragment(),null);
        }else{
            session_user.setText(session);
        }
    }

    private void actionAddData(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acsignOut();
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                Fragment fragment = new ComplaintDetailFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                mBundle.putString("action", "add");
                mBundle.putString("idPelanggan", pref.getString("idPelanggan",""));
                mBundle.putString("namaPelanggan", pref.getString("namaPelanggan",""));
                mBundle.putString("latitude", pref.getString("latitude",""));
                mBundle.putString("longitude", pref.getString("longitude",""));
                mBundle.putString("alamatPelanggan", pref.getString("alamatPelanggan",""));
                fragment.setArguments(mBundle);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.contentFragment, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void acsignOut() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Konfirmasi");
        builder.setMessage("Hapus sesi akun");
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // dismiss dialog
            }
        });
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("session_user", null);
                editor.commit();
                ((MainActivity) getActivity()).loadFragmentNoBack(new LoginFragment(),null);

            }
        });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}

