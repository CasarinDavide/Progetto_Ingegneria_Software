package com.example.progetto_ingegneria_software.ui.plants;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsBinding;
import com.example.progetto_ingegneria_software.ui.notifications.PlantsViewModel;

// TODO fare aggiornamento delle lista quando ritorno dall'activity di aggiunta del terreno

public class PlantsFragment extends Fragment {

    private FragmentPlantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentPlantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //RecyclerView creation
        final RecyclerView recyclerView = binding.terrainRecyclerViewHome;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Button add_terrain_btn = binding.addCardButton;
        Button add_find_plant = binding.addFindPlant;

        add_find_plant.setOnClickListener(x->{
            Navigation.findNavController(requireView()).navigate(R.id.navigation_search_plants);

        });
        add_terrain_btn.setOnClickListener(x->{
            Intent addTerrainActivity = new Intent(root.getContext(), AddTerrainActivity.class);
            startActivity(addTerrainActivity);
        });


        // get all
        Terrain.terrainDB.getAllFilteredByUser(x->{
            RecyclerTerrainsViewAdapter adapter = new RecyclerTerrainsViewAdapter(x,(pos,model)->{
                TerrainDetailsFragment detailFragment = new TerrainDetailsFragment();
                NavController navController = Navigation.findNavController(requireView());
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getUid());
                // aggiungo il dato da passare
                detailFragment.setArguments(bundle);
                navController.navigate(R.id.navigation_terrain_detail, bundle);

            });
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