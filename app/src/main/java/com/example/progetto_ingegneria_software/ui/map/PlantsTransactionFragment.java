package com.example.progetto_ingegneria_software.ui.map;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Transaction;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsTransactionBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class PlantsTransactionFragment extends Fragment {

    private FragmentPlantsTransactionBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private FloatingActionButton addLocation;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPlantsTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        //map
        MapView mapView = binding.mapViewPlantTransactionFragment;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        //button
        addLocation = binding.addLocationPlantTransactionFragment;
        addLocation.setOnClickListener( view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_plants_transaction_to_navigation_create_transaction);
        });

        return root;
    }

    public void onMapReady(GoogleMap googleMap) {
        float zoom = googleMap.getMinZoomLevel();

        // Set up cancellation token in case the user navigates away
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        CancellationToken token = cancellationTokenSource.getToken();

        //check position permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            Toast.makeText(requireContext(), "To access this feature enable position access", Toast.LENGTH_LONG).show();
            addLocation.setVisibility(View.GONE);
            return;
        }
        //get User's location
        if(checkLocation(requireContext()))
        {
            addLocation.setVisibility(View.VISIBLE);
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
                    .addOnCompleteListener( task -> {
                        LatLng coord = new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude());

                        //get other transactions location
                        Transaction.transactionDB
                                .getTransactions( transactionList -> {
                                    for(Transaction t : transactionList) {
                                        String items = String.join("\n", t.getItems());

                                        Objects.requireNonNull(googleMap.addMarker(new MarkerOptions().position(new LatLng(t.getLatitude(), t.getLongitude())).title(items)))
                                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    }
                                });
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(coord.latitude, coord.longitude)).title("Your position"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, zoom));
                    });
        }
        else
        {
            addLocation.setVisibility(View.GONE);
            Toast.makeText(getContext(), "To access this feature enable your position", Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}