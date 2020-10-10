package com.kalinesia.tubesmobile.Maps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kalinesia.tubesmobile.R;

public class MapsView extends Fragment {
    SharedPreferences pref;
    MapView mMapView;
    private GoogleMap googleMap;
    String idPelanggan,namaPelangga,latitude,longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_view, container, false);

        mMapView = view.findViewById(R.id.mapView);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref = view.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);

        idPelanggan =  pref.getString("idPelanggan","");
        namaPelangga =  pref.getString("namaPelangga","");
        latitude =  pref.getString("latitude","");
        longitude =  pref.getString("longitude","");
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
//                LatLng sydney = new LatLng(-7.91974, 112.6392673);
                googleMap.addMarker(new MarkerOptions().position(sydney).title(idPelanggan).snippet(namaPelangga));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
