package com.example.progetto_ingegneria_software;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantsApi;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_plants, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        fillData();
    }

    // esempio di utilizzo della classe
    public void fillData() {

        // Mostro la ProgressBar on the main thread
        runOnUiThread(() -> {
            ProgressBar speciesProgressBar = findViewById(R.id.main_activity_progressbar_load_species);
            speciesProgressBar.setVisibility(View.VISIBLE);
        });

        // chiamata a funzione per eseguire la richiesta GET
        // l'ultimo parametro è una lambda che prende in input un PlantRequestContainer<Species> e lo elabora graficamente
        // plantRequestContainer è generato dalla funzione..

        PlantsApi.getAllSpeciesAsync("","","","","","","",0, plantRequestContainer -> {

            // solo il thread princiale ( UI thread può cambiare le informazioni riguardo la grafica )
            // da un thread secondario devo passare al principale

            runOnUiThread(() -> {

                ProgressBar speciesProgressBar = findViewById(R.id.main_activity_progressbar_load_species);

                // nascondo progress bar
                speciesProgressBar.setVisibility(View.GONE);


                // esempio utilizzo dei dati ricevuti in input
                for (Species species : plantRequestContainer.getData()) {

                    // aggiungi alla grafica i dati presi .....
                }
            });
        });
    }

}