package com.example.progetto_ingegneria_software.ui.plants;

import android.content.Intent;
import android.os.Build;
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
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsBinding;
import com.example.progetto_ingegneria_software.databinding.FragmentTerrainDetailsBinding;
import com.example.progetto_ingegneria_software.ui.notifications.PlantsViewModel;

import java.util.List;

public class TerrainDetailsFragment extends Fragment {

    private FragmentTerrainDetailsBinding binding;

    // TODO CAMBIARE CON PARAMETRI IN QUALCHE MODO NON DEVONO ESSERE COSTANTI
    private final Integer ROWS = 10;
    private final Integer COLS = 9;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentTerrainDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        GridLayout terrainPerimeter = binding.inventoryPlantListGridview;


        Bundle args = getArguments();
        if (args != null) {
            String terrain_uid = args.getString("id");
            Terrain.terrainDB.getByIdTerrain(terrain_uid,terrain->{

                getActivity().runOnUiThread(()->{

                    for (int i = 0; i < ROWS;i++)
                    {
                        for (int j = 0; j < COLS;j++)
                        {
                            AddTerrainActivity.PositionalImageView imageView = new AddTerrainActivity.PositionalImageView(getContext(),i,j);
                            // Set the image resource (make sure ic_advice exists in drawable)


                            int pos = j + COLS * i;

                            if (terrain.getTerrain().get(pos).equals(Terrain.CELL_STATES.EMPTY_CELL.id))
                            {
                                imageView.setImageResource(R.drawable.ic_terrain);
                            }
                            else
                            {
                                imageView.setImageResource(R.drawable.ic_grass_terrain);
                            }

                            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                            params.columnSpec = GridLayout.spec(j, 1); // span 1 column
                            params.rowSpec = GridLayout.spec(i, 1);     // span 1 row
                            params.setMargins(4, 4, 4, 4); // Add margins
                            imageView.setLayoutParams(params);
                            imageView.setAdjustViewBounds(true); // Maintain aspect ratio

                            // Add the ImageView to the GridLayout
                            terrainPerimeter.addView(imageView, params);
                        }
                    }
                    terrainPerimeter.requestLayout();
                });
            });
        }


        //RecyclerView creation
        final RecyclerView recyclerView = binding.inventoryPlantList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));

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