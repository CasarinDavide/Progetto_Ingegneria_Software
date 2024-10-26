package com.example.progetto_ingegneria_software;

import android.content.Intent;
import android.os.Bundle;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto_ingegneria_software.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        Button confirm_btn = (Button) findViewById(R.id.sign_in_activity_confirm_btn);
        TextView username_txt = (TextView) findViewById(R.id.sign_in_activity_username_txt);
        TextView password_txt = (TextView) findViewById(R.id.sign_in_activity_password_txt);
        TextView confirm_password_txt = (TextView) findViewById(R.id.sign_in_activity_confirm_password_txt);
        TextView telephone_txt = (TextView) findViewById(R.id.sign_in_activity_telephone_txt);
        TextView email_txt = (TextView) findViewById(R.id.sign_in_activity_email_txt);


        confirm_btn.setOnClickListener(x-> {

            String email_str = email_txt.getText().toString();
            String password_str = password_txt.getText().toString();
            String confirm_password_str = confirm_password_txt.getText().toString();
            String telephone_str = telephone_txt.getText().toString();
            String username_str = username_txt.getText().toString();

            if (!isValidFormInput(email_str,password_str,confirm_password_str,telephone_str,username_str))
            {
                Toast.makeText(this, "Errore nell'inseriemento dei dati. Riprovare.", Toast.LENGTH_SHORT).show();
                return;
            }

            Auth.createUser(SignInActivity.this, email_str, password_str, new Runnable() {
                @Override
                public void run() {
                    Intent home_activity = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(home_activity);
                }
            });

        });
    }


    protected boolean isValidFormInput(String email, String password, String conf_password, String telephone,String username_txt)
    {
        // TODO aggiungere altri check per l'input utente

        // controllo univocità username
        /**
         * Auth.checkUniqueUsername.... ad esempio
         */


        if (!password.equals(conf_password))
        {
            return false;
        }

        return true;
    }
}