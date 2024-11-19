package com.example.progetto_ingegneria_software.ui.map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.progetto_ingegneria_software.databinding.FragmentPlantsExchangeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;

public class PlantsExchangeFragment extends Fragment {

    private FragmentPlantsExchangeBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPlantsExchangeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        //map
        MapView mapView = binding.mapViewPlantExchangeFragment;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        //button
        Button addLocation = binding.addLocationPlantExchangeFragment;
        addLocation.setOnClickListener( view -> {
            //TODO Add an plant exchange to given location, update database
        });

        return root;
    }

    public void onMapReady(GoogleMap googleMap) {
        float zoom = googleMap.getMaxZoomLevel() - 5;

        // Set up cancellation token in case the user navigates away
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        CancellationToken token = cancellationTokenSource.getToken();


        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
                .addOnCompleteListener( task -> {
                    LatLng coord = new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude());

                    googleMap.addMarker(new MarkerOptions().position(coord).title("Your Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, zoom));
                    //TODO fetch all announcement positions and write them on the map
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}