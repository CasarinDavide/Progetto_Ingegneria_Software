package com.example.progetto_ingegneria_software.data.model;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class Auth {

    private static FirebaseAuth auth;
    private static Auth instance;

    private Auth() {
        auth = FirebaseAuth.getInstance();
    }

    public static Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }

        return instance;
    }

    public void login(Activity activity, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            // On success, return user object through the callback
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
                        } else {

                            Toast error = Toast.makeText(activity.getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG);
                            error.show();
                        }
                    }

                });
    }

    public User isLogged()
    {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        return firebaseUser!= null?new User(firebaseUser.getUid(), firebaseUser.getEmail()):null;
    }

    public void createUser(Activity activity,String email,String password)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
                        }
                        else {
                            Toast error = Toast.makeText(activity.getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG);
                            error.show();
                        }
                    }
                });
    }

}