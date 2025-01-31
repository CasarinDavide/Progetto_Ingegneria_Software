package com.example.progetto_ingegneria_software;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


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
                R.id.navigation_home, R.id.navigation_plants_transaction, R.id.navigation_plants, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController,false);

        navController.addOnDestinationChangedListener((a,destinationChangedListener,c)->{
            if (destinationChangedListener.getId() == R.id.navigation_comment_section || destinationChangedListener.getId() == R.id.navigation_create_transaction)
            {
                binding.navView.setVisibility(BottomNavigationView.GONE);
            }
            else
            {
                binding.navView.setVisibility(BottomNavigationView.VISIBLE);
            }
        });
    }
}