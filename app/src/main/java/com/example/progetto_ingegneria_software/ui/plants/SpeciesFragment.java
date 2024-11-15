package com.example.progetto_ingegneria_software.ui.plants;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantRequestContainer;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantsApi;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.FragmentSpeciesBinding;

import java.util.List;

public class SpeciesFragment extends Fragment {

    private FragmentSpeciesBinding binding;
    private Button search_btn;
    private TextView search_term_textView;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //binds the Views of the fragment
        search_btn = binding.searchButton;
        search_term_textView = binding.searchInput;

        recyclerView = binding.speciesRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        search_btn.setOnClickListener(x->{
            getAllSpecies(search_term_textView.getText().toString(),plantRequestContainer -> {

                // solo il thread princiale ( UI thread può cambiare le informazioni riguardo la grafica )
                // da un thread secondario devo passare al principale
                // esempio utilizzo dei dati ricevuti in input


                getActivity().runOnUiThread(() -> {
                    // Update the UI safely on the main thread
                    List<Species> speciesList = plantRequestContainer.getData();
                    RecyclerSpeciesViewAdapter adapter = new RecyclerSpeciesViewAdapter(speciesList,(pos,model)->{

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
                });
            });
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // da decidere se caricare tutti o no
        // son tanti
        // pensiamo a qualcosa meglio semmai
        // oppure inseriamno una sorta di paginazione, la api permette di avere un limite sulle informazioni ritornate e la pagina corrente

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // esempio di utilizzo della classe
    public void getAllSpecies(String search_term, PlantsApi.PlantsApiCallback<PlantRequestContainer<Species>> callback) {

        // Mostro la ProgressBar on the main thread
        
        // chiamata a funzione per eseguire la richiesta GET
        // l'ultimo parametro è una lambda che prende in input un PlantRequestContainer<Species> e lo elabora graficamente
        // plantRequestContainer è generato dalla funzione..

        PlantsApi.getAllSpeciesAsync("","","","","","","",0,search_term,callback );
    }
    
}
