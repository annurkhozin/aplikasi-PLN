package com.kalinesia.tubesmobile.Maps;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kalinesia.tubesmobile.Customer.CustomerFragment;
import com.kalinesia.tubesmobile.LoginFragment;
import com.kalinesia.tubesmobile.MainActivity;
import com.kalinesia.tubesmobile.R;
import com.kalinesia.tubesmobile.User.UserFragment;
import com.kalinesia.tubesmobile.complaint.ComplaintAdapter;
import com.kalinesia.tubesmobile.complaint.ComplaintDetailFragment;
import com.kalinesia.tubesmobile.complaint.ComplaintModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    private DatabaseReference mDataCustomer;
    RecyclerView rvCustomer;
    ArrayList<HomeModel> list;
    HomeAdapter homeAdapter;
    LinearLayout lvHome,lvCustomer,lvUser,lvLogOut;
    SharedPreferences pref;
    TextView session_user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lvHome = view.findViewById(R.id.lvHome);
        lvCustomer = view.findViewById(R.id.lvCustomer);
        lvUser = view.findViewById(R.id.lvUser);
        lvLogOut = view.findViewById(R.id.lvLogOut);
        rvCustomer = view.findViewById(R.id.rvCustomer);
        rvCustomer.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvCustomer.setNestedScrollingEnabled(false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref = view.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        list = new ArrayList<HomeModel>();
        session_user = view.findViewById(R.id.session_user);
        getCustomerList();
        setSession_user();
        menuAction();
    }

    private void getCustomerList(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        Query queries = myRef.child("Complaint").orderByChild("statusLaporan").startAt(0).endAt(1);
        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    HomeModel cus = dataSnapshot1.getValue(HomeModel.class);
                    cus.setKey(dataSnapshot1.getKey());
//                    Log.i("vvvv",cus.getKey());
                    list.add(cus);
                }

                homeAdapter =  new HomeAdapter(getActivity(),list, new HomeAdapter.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("idPelanggan", list.get(position).getIdPelanggan());
                        editor.putString("namaPelangga", list.get(position).getNamaPelanggan());
                        editor.putString("latitude", list.get(position).getLatitude());
                        editor.putString("longitude", list.get(position).getLongitude());
                        editor.commit();
                        ((MainActivity) getActivity()).loadFragment(new MapsView(),null);
                    }

                    @Override
                    public void onChangeStatus(final View view, int position) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = firebaseDatabase.getReference();
                        final int status = list.get(position).getStatusLaporan()+1;
                        Query queries = myRef.child("Complaint").child(list.get(position).getKey());
                        queries.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("statusLaporan").setValue(status);
                                Snackbar.make(view, "Status Berhasil di Perbarui", Snackbar.LENGTH_LONG).show();
                                ((MainActivity) getActivity()).loadFragmentNoBack(new HomeFragment(),null);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                });
                rvCustomer.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setSession_user(){
        String session =  pref.getString("session_user","");
        if(!"".equals(session)){
            session_user.setText(session);
        }else{
            ((MainActivity) getActivity()).loadFragmentNoBack(new LoginFragment(),null);
        }
    }

    private void menuAction(){
        lvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragmentNoBack(new HomeFragment(),null);
            }
        });
        lvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new CustomerFragment(),null);
            }
        });
        lvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new UserFragment(),null);
            }
        });
        lvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acsignOut();
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

