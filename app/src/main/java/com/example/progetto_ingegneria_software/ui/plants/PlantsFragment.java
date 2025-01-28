package com.example.progetto_ingegneria_software.ui.plants;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsBinding;
import com.example.progetto_ingegneria_software.ui.notifications.PlantsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

// TODO fare aggiornamento delle lista quando ritorno dall'activity di aggiunta del terreno

public class PlantsFragment extends Fragment {

    private FragmentPlantsBinding binding;
    private TextView no_result;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentPlantsBinding.inflate(inflater, container, false);
        no_result = binding.emptyMessage;
        progressBar = binding.loadingSpinner;

        View root = binding.getRoot();

        //RecyclerView creation
        final RecyclerView recyclerView = binding.terrainRecyclerViewHome;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ImageView add_find_plant = binding.addFindPlant;

        add_find_plant.setOnClickListener(x->{
            Navigation.findNavController(requireView()).navigate(R.id.navigation_search_plants);
        });



        // get all
        progressBar.setVisibility(View.VISIBLE);

        User.userDB.getUserInfo(x->{

            List<Species> inventory = x.getInventory();
            if (inventory.isEmpty())
            {
                getActivity().runOnUiThread(()->{
                    no_result.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                });
            }
            else
            {
                getActivity().runOnUiThread(()->{
                    no_result.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                });

                RecyclerSpeciesViewAdapter adapter = new RecyclerSpeciesViewAdapter(x.getInventory(),(pos,model)->{
                    PlantDetailFragment detailFragment = new PlantDetailFragment();
                    NavController navController = Navigation.findNavController(requireView());
                    Bundle bundle = new Bundle();
                    bundle.putString("id", model.getId().toString());
                    bundle.putSerializable("plant",model);
                    // aggiungo il dato da passare
                    detailFragment.setArguments(bundle);
                    navController.navigate(R.id.navigation_plants_detail, bundle);

                });
                recyclerView.setAdapter(adapter);
            }

        });


        /*
        Terrain.terrainDB.getAllFilteredByUser(x->{
            RecyclerTerrainsViewAdapter adapter = new RecyclerTerrainsViewAdapter(x,(pos,model)->{
                TerrainDetailsFragment detailFragment = new TerrainDetailsFragment();
                NavController navController = Navigation.findNavController(requireView());
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getDocumentId());
                // aggiungo il dato da passare
                detailFragment.setArguments(bundle);
                navController.navigate(R.id.navigation_terrain_detail, bundle);
            });
            recyclerView.setAdapter(adapter);
        });
        */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}