package com.example.progetto_ingegneria_software.ui.plants;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsBinding;
import com.example.progetto_ingegneria_software.databinding.FragmentTerrainDetailsBinding;
import com.example.progetto_ingegneria_software.ui.notifications.PlantsViewModel;

public class TerrainDetailsFragment extends Fragment {

    private FragmentTerrainDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentTerrainDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //RecyclerView creation
        final RecyclerView recyclerView = binding.inventoryPlantList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // get all
        User.userDB.getUserInfo(x->{
            RecyclerInventoryViewAdapter adapter = new RecyclerInventoryViewAdapter(x.getInventory());
            recyclerView.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}