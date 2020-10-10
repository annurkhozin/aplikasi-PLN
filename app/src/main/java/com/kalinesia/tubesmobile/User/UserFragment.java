package com.kalinesia.tubesmobile.User;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalinesia.tubesmobile.Customer.CustomerAdapter;
import com.kalinesia.tubesmobile.Customer.CustomerDetailFragment;
import com.kalinesia.tubesmobile.Customer.CustomerFragment;
import com.kalinesia.tubesmobile.Customer.CustomerModel;
import com.kalinesia.tubesmobile.LoginFragment;
import com.kalinesia.tubesmobile.MainActivity;
import com.kalinesia.tubesmobile.Maps.HomeFragment;
import com.kalinesia.tubesmobile.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    View view;
    private DatabaseReference mDataUser;
    RecyclerView rvUser;
    ArrayList<UserModel> list;
    UserAdapter userAdapter;
    ImageView acBack;
    SharedPreferences pref;
    TextView session_user;
    FloatingActionButton addData;
    LinearLayout lvHome,lvCustomer,lvUser,lvLogOut;
    Button btn_pindahakun;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_pindahakun = view.findViewById(R.id.btn_pindahakun);
        addData = view.findViewById(R.id.addData);
        acBack = view.findViewById(R.id.acBack);
        lvHome = view.findViewById(R.id.lvHome);
        lvCustomer = view.findViewById(R.id.lvCustomer);
        lvUser = view.findViewById(R.id.lvUser);
        lvLogOut = view.findViewById(R.id.lvLogOut);
        rvUser = view.findViewById(R.id.rvUser);
        rvUser.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvUser.setNestedScrollingEnabled(false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref = view.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        list = new ArrayList<UserModel>();
        session_user = view.findViewById(R.id.session_user);
        getUserList();
        setSession_user();
        actionAddData();
        actionBack();
        menuAction();
    }

    private void getUserList(){
        mDataUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mDataUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    UserModel us = dataSnapshot1.getValue(UserModel.class);
                    list.add(us);
                }
                userAdapter =  new UserAdapter(getActivity(),list, new UserAdapter.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Snackbar.make(view, "Detail Petugas "+list.get(position).getEmailPetugas(), Snackbar.LENGTH_LONG).show();

                        Bundle mBundle = new Bundle();
                        Fragment fragment = new UserDetailFragment();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        mBundle.putString("action", "update");
                        mBundle.putString("emailPetugas", list.get(position).getEmailPetugas());
                        mBundle.putString("namaPetugas", list.get(position).getNamaPetugas());
                        mBundle.putString("passwordPetugas", list.get(position).getPasswordPetugas());
                        fragment.setArguments(mBundle);
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.contentFragment, fragment).addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                });
                rvUser.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setSession_user(){
        String session =  pref.getString("session_user","");
        if("".equals(session)){
            session_user.setText(session);
            ((MainActivity) getActivity()).loadFragmentNoBack(new LoginFragment(),null);
        }
    }

    private void actionAddData(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                Fragment fragment = new UserDetailFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                mBundle.putString("action", "add");
                fragment.setArguments(mBundle);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.contentFragment, fragment).addToBackStack(null);
                fragmentTransaction.commit();
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

        btn_pindahakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Konfirmasi");
                builder.setMessage("Pindah akun");
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

