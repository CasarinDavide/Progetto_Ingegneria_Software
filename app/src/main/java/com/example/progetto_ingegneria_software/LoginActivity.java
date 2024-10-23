package com.example.progetto_ingegneria_software;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.progetto_ingegneria_software.databinding.LoginMainBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginMainBinding binding;
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = Auth.getInstance();

        // l'utente è gia loggato
        // salto direttamente nella prossima activity
        if (auth.isLogged() != null)
        {
            // jump alla seconda attività
        }



        binding = LoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button login_btn = (Button) findViewById(R.id.login_activity_confirm_button_btn);
        TextView email_textview =  findViewById(R.id.login_activity_email_txtView);
        TextView password_textview =  findViewById(R.id.login_activity_password_txtView);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUser(LoginActivity.this,email_textview.getText().toString(),password_textview.getText().toString());
            }
        });
    }

}