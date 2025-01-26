package com.example.progetto_ingegneria_software.data.model;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public abstract class Auth {

    private static final FirebaseAuth auth;

    static {
        auth = FirebaseAuth.getInstance();
    }

    public static void login(Activity activity, String email, String password, Runnable onSuccessAction) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            // On success, return user object through the callback
                            //User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());

                            onSuccessAction.run();
                        } else {

                            Toast error = Toast.makeText(activity.getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG);
                            error.show();
                        }
                    }

                });
    }

    public static void autologin(Activity activity, String email, String password, Runnable onSuccessAction) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            // On success, return user object through the callback
                            //User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
                            onSuccessAction.run();
                        }
                    }

                });
    }


    public static void signOut() {
        if (auth != null) {
            auth.signOut();
        }
    }

    public static boolean isLogged()
    {
        return auth.getCurrentUser()==null;
    }

    public static void createUser(Activity activity, String email, String password, String telephone, String username, Runnable onSuccessAction)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            Toast error = Toast.makeText(activity.getApplicationContext(),"Registrazione Avvenuta con successo",Toast.LENGTH_LONG);
                            error.show();

                            //Adds user in firebase
                            assert firebaseUser != null;
                            User u = new User(username, telephone, "/images/profilePictures/default_user_pfp.png" , new ArrayList<String>(), firebaseUser.getUid(), email);

                          
                            //Database db = new Database("users");
                            //db.addDocument(u.getUid(), u);
                            User.userDB.addRecord(u, u.getUid());
                            onSuccessAction.run();
                        }
                        else {
                            Toast error = Toast.makeText(activity.getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG);
                            error.show();
                        }
                    }
                });
    }

    public static void resetPassword(Activity activity,String email)
    {
        auth.sendPasswordResetEmail(email);
    }

    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
