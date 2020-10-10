package com.kalinesia.tubesmobile;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragmentNoBack(new LoginFragment(),null);
    }

    public void loadFragmentNoBack(Fragment fragment,Parcelable data) {
        Bundle mBundle = new Bundle();
        FragmentManager fm = getSupportFragmentManager();
        mBundle.putParcelable("dataLogin", data);
        fragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragment, fragment);
        fragmentTransaction.commit();
    }

    public void loadFragment(Fragment fragment, Parcelable data) {
        Bundle mBundle = new Bundle();
        FragmentManager fm = getSupportFragmentManager();
        mBundle.putParcelable("dataLogin", data);
        fragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragment, fragment).addToBackStack(null);
        fragmentTransaction.commit();

    }
}
