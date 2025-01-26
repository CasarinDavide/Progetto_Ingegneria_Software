package com.example.progetto_ingegneria_software;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto_ingegneria_software.Repository.DataStoreRepository;
import com.example.progetto_ingegneria_software.data.model.Auth;

import androidx.appcompat.app.AppCompatActivity;

import com.example.progetto_ingegneria_software.databinding.LoginMainBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Auth.getCurrentUser() != null) {
            Intent home_activity = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(home_activity);
            finish();
        }

        binding = LoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button login_btn = (Button) findViewById(R.id.confirm_button_btn);
        TextView sign_in_btn = findViewById(R.id.sign_in_button_btn);
        TextView email_textview =  findViewById(R.id.email_txtView);
        TextView password_textview =  findViewById(R.id.password_txtView);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login_btn.setActivated(false);
                if(email_textview.getText().toString().isEmpty() || password_textview.getText().toString().isEmpty())
                {
                    login_btn.setActivated(true);
                    Toast.makeText(LoginActivity.this, "Devi inserire tutte le credenziali", Toast.LENGTH_SHORT).show();
                    return;
                }

                Auth.login(LoginActivity.this,email_textview.getText().toString(),password_textview.getText().toString(),
                        () -> {
                            login_btn.setActivated(true);
                            Intent home_activity = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(home_activity);
                        }
                );
            }
        });

        sign_in_btn.setOnClickListener(x->{
            Intent sign_in_activity = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(sign_in_activity);

        });

    }

}