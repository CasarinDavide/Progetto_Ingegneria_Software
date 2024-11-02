package com.example.progetto_ingegneria_software.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.LoginActivity;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.SignInActivity;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantsBinding;
import com.example.progetto_ingegneria_software.ui.AddTerrainActivity;
import com.example.progetto_ingegneria_software.ui.home.RecyclerTerrainsViewAdapter;
import com.example.progetto_ingegneria_software.ui.home.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlantsFragment extends Fragment {

    private FragmentPlantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentPlantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Content to display
        List<Terrain> p = new ArrayList();
        p.add(new Terrain("richipasinato", "Questo è un post sulla bellezza sconfinata delle piante (aiuto mi stanno minacciando)"));
        p.add(new Terrain("davidecasarin", "Chi legge è un ammiratore delle piante (hanno rapito mia figlia)"));
        p.add(new Terrain("giorgiabusetto", "Ho davvero tantissime piante a casa, qualcuno ne vuole?"));

        //RecyclerView creation
        final RecyclerView recyclerView = binding.terrainRecyclerViewHome;
        RecyclerTerrainsViewAdapter adapter = new RecyclerTerrainsViewAdapter(p);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        Button add_terrain_btn = binding.addCardButton;
        add_terrain_btn.setOnClickListener(x->{
            Intent addTerrainActivity = new Intent(root.getContext(), AddTerrainActivity.class);
            startActivity(addTerrainActivity);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}