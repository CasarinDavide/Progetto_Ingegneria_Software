package com.example.progetto_ingegneria_software.ui.map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Transaction;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCreateTransactionBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateTransactionFragment extends Fragment {
    private FragmentCreateTransactionBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up cancellation token in case the user navigates away
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        CancellationToken token = cancellationTokenSource.getToken();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        ListView l = binding.listViewFragmentCreateTransaction;

        User.userDB.getDocument(Auth.getCurrentUser().getUid()).get().addOnCompleteListener(
                documentSnapshot -> {
                    List<Item> inventory = new ArrayList<>();
                    List<Map<String, Object>> m = (List<Map<String, Object>>) documentSnapshot.getResult().get("inventory");

                    if(m != null) {
                        for (Map<String, Object> val : m) {
                            Item i = new Item((String) val.get("commonName"), false);
                            inventory.add(i);
                        }
                    }
                    l.setAdapter(new CreateTransactionAdapter(getContext(), inventory));
                });

        FloatingActionButton done = binding.addTransactionPosition;
        done.setOnClickListener( view -> {
            //check position permission
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
                Toast.makeText(requireContext(), "To access this feature enable position access", Toast.LENGTH_LONG).show();
                return;
            }

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
                    .addOnCompleteListener( task -> {
                        //get all checked elements
                        List<String> selectedItems = new ArrayList<>();
                        l.getAdapter().getCount();

                        for(int i=0; i < l.getAdapter().getCount(); i++) {
                            Item item = (Item) l.getAdapter().getItem(i);

                            if(item.isChecked()) {
                                selectedItems.add(item.getLabel());
                            }
                        }

                        if(selectedItems.isEmpty()) {
                            Toast.makeText(requireContext(), "Select at least one item", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //add transaction to the database
                        Transaction t = new Transaction(Auth.getCurrentUser().getUid(), task.getResult().getLatitude(), task.getResult().getLongitude(), selectedItems);
                        Transaction.transactionDB.addTransaction(t);

                        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_create_transaction_to_navigation_plants_transaction);
                    });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


