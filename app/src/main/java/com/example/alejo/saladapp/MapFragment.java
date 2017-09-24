package com.example.alejo.saladapp;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap map;

    public MapFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int flag = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        getActivity().setTitle("Mapa de rutas");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("routes").child("B290");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (flag == 1) {
                    map.clear();
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        LatLng mark = new LatLng(Double.parseDouble(postSnapshot.child("lat").getValue().toString()), Double.parseDouble(postSnapshot.child("lng").getValue().toString()));
                        options.add(mark);
                        //map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.food)).position(mark).title(postSnapshot.child("nombre").getValue().toString()).snippet("Precio del domicilio: " + postSnapshot.child("precio").getValue().toString()));
                    }
                    Polyline line = map.addPolyline(options);
                    LatLng udea = new LatLng(6.268021, -75.567693);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(udea, 16));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map.clear();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LatLng mark = new LatLng(Double.parseDouble(postSnapshot.child("lat").getValue().toString()), Double.parseDouble(postSnapshot.child("lng").getValue().toString()));
                    options.add(mark);
                    //map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.food)).position(mark).title(postSnapshot.child("nombre").getValue().toString()).snippet("Precio del domicilio: " + postSnapshot.child("precio").getValue().toString()));
                }
                Polyline line = map.addPolyline(options);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        LatLng udea = new LatLng(6.268021, -75.567693);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(udea, 16));
        flag = 1;

    }
}




