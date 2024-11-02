package com.example.progetto_ingegneria_software.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;


/**
 * The ViewModel contains information on the structure of data presented, it does not care about how it is displayed
 * It is used to pass data between fragments
 */
public class HomeViewModel extends ViewModel {

    public interface HomeCallBack<T> {
        void onComplete(T value);
    }

    private User mUser;
    Database db;
    FirebaseUser user;

    public HomeViewModel() {
        db = new Database("users");
        user = Auth.getCurrentUser();
    }

    /**
     *
     * @param callBack the
     */
    public void getUserOnComplete(HomeCallBack<User> callBack) {
        db.getDocument(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    callBack.onComplete(task.getResult().toObject(User.class));
                }
            }
        });
    }

}