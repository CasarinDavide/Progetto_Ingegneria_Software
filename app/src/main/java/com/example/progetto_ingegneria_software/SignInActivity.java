package com.example.progetto_ingegneria_software;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.text.Html;
import android.text.Spanned;
import androidx.core.text.HtmlCompat;

import com.example.progetto_ingegneria_software.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    public interface SignInCallBack<T> {
        void onComplete(T value);
    }

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        /toolBarLayout.setTitle(getTitle()); */

        Button confirm_btn = binding.signInActivityConfirmBtn;
        TextView username_txt = binding.signInActivityUsernameTxt;
        TextView password_txt = binding.signInActivityPasswordTxt;
        TextView confirm_password_txt = binding.signInActivityConfirmPasswordTxt;
        TextView telephone_txt = binding.signInActivityTelephoneTxt;
        TextView email_txt = binding.signInActivityEmailTxt;
        TextView login_btn = findViewById(R.id.login_button_btn);

        login_btn.setOnClickListener(x->{
            Intent login_activity = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(login_activity);

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String email_str = email_txt.getText().toString();
                String password_str = password_txt.getText().toString();
                String confirm_password_str = confirm_password_txt.getText().toString();
                String telephone_str = telephone_txt.getText().toString();
                String username_str = username_txt.getText().toString();

                isValidFormInput(email_str, password_str, confirm_password_str, telephone_str, username_str, new SignInCallBack<Boolean>() {
                    @Override
                    public void onComplete(Boolean isDataCorrect) {
                        if(isDataCorrect) {
                            Auth.createUser(SignInActivity.this, email_str, password_str, telephone_str, username_str, new Runnable() {
                                @Override
                                public void run() {
                                    confirm_btn.setActivated(false);
                                    Intent home_activity = new Intent(SignInActivity.this, HomeActivity.class);
                                    home_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(home_activity);
                                    finish();
                                }
                            });
                        }
                        else {
                            confirm_btn.setActivated(false);
                            Toast.makeText(SignInActivity.this, "Errore nell'inseriemento dei dati. Riprovare.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Rifiuta", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setTitle("Condizioni utilizzo dati");

        final TextView message = new TextView(this);
        Spanned text = HtmlCompat.fromHtml(
                "Autorizzo il trattamento dei dati personali presenti ai sensi del D.Lgs. 2018/101 e del GDPR (Regolamento UE 2016/679).<br><br>" +
                        "<a href='https://drive.google.com/file/d/1DO4XhgD57sK4FnbWGyObNObaf5HF8D-x/view?usp=sharing'>Scarica PDF</a>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
        );
        message.setText(text);

        message.setMovementMethod(LinkMovementMethod.getInstance());
        message.setLinksClickable(true);
        message.setAutoLinkMask(Linkify.WEB_URLS);

        message.setPadding(50, 20, 50, 20);

        builder.setView(message);

        confirm_btn.setOnClickListener(x -> {
            confirm_btn.setActivated(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        });


    }


    protected void isValidFormInput(String email, String password, String conf_password, String telephone, String username_txt, SignInCallBack<Boolean> callBack)
    {
        Database db = new Database("users");
        db.isUnique("username", username_txt, new Database.DatabaseCallback<Boolean>() {

            boolean result = true;
            @Override
            public void onComplete(Boolean isUniqueUser) {
                    if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        result = false;
                    }

                    if (password.isEmpty() || !password.equals(conf_password)) {
                        result = false;
                    }

                    if(telephone.isEmpty() || !Patterns.PHONE.matcher(telephone).matches()) {
                        result = false;
                    }

                    if(!isUniqueUser) {
                        result = false;
                    }

                    callBack.onComplete(result);
            }
        });
    }
}