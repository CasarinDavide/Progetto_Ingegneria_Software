package com.example.progetto_ingegneria_software.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        */

        //Content to display
        List<Post> p = new ArrayList();
        p.add(new Post("richipasinato", "Questo è un post sulla bellezza sconfinata delle piante (aiuto mi stanno minacciando)", new ArrayList<>(), new Date(), 1, "", 24));
        p.add(new Post("davidecasarin", "Chi legge è un ammiratore delle piante (hanno rapito mia figlia)", new ArrayList<>(), new Date(), 2, "",3));
        p.add(new Post("giorgiabusetto", "Ho davvero tantissime piante a casa, qualcuno ne vuole?", new ArrayList<>(), new Date(), 3, "", 15));

        //RecyclerView creation
        final RecyclerView recyclerView = binding.postsRecyclerViewHome;
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(p);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}