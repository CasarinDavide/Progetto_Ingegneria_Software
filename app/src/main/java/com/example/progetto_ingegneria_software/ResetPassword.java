package com.example.progetto_ingegneria_software;

import android.os.Bundle;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.progetto_ingegneria_software.databinding.ActivityResetPasswordBinding;

public class ResetPassword extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText mail = binding.emailTxtView;
        Button send_mail = binding.resetBtn;


        send_mail.setOnClickListener(view->{
            if (!mail.getText().toString().isEmpty()) {
                Auth.resetPassword(this, mail.getText().toString());
            }
            else
                Toast.makeText(this, "Devi inserire una mail valida", Toast.LENGTH_SHORT).show();
        });
    }

}