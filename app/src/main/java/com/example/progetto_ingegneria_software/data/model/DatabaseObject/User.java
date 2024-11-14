package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.ui.home.RecyclerViewAdapter;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private  String email;
    private String uid;
    private List<String> likes;
    private String profilePicture;
    private String phone;
    private String username;

    public static UserDB userDB;
    static {
        userDB = new UserDB();
    }

    public User() {
        this.email = "";
        this.uid = "";
        this.likes = new ArrayList<String>();
        this.profilePicture = "/images/profilePictures/default_user_pfp.png";
        this.phone = "";
        this.username = "";
    }

    public User(String username, String phone, String profilePicture, List<String> likes, String uid, String email) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.profilePicture = profilePicture;
        this.likes = likes;
        this.uid = uid;
    }

    public static class UserDB extends Database {
        UserDB() {
            super("users");
        }

        @Override
        public DocumentReference getDocument(String documentId) {
            return super.getDocument(documentId);
        }

        /**
         * Gets current user's username
         * @param callback
         */
        public void getUsername(DatabaseCallback<String> callback) {
            getDocument(Auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener( documentSnapshot -> {
                        String username = Objects.requireNonNull(documentSnapshot.getResult().toObject(User.class)).getUsername();
                        callback.onComplete(username);
                    });
        }

        /**
         * Get current user's object
         * @param callback
         */
        public void getUserInfo(DatabaseCallback<User> callback) {
            getDocument(Auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener( documentSnapshot -> {
                        User u = documentSnapshot.getResult().toObject(User.class);
                        callback.onComplete(u);
                    });
        }

        public void setUserEmail(String email) {
            updateField(Auth.getCurrentUser().getUid(), "email", email);
        }

        public void setUserPhone(String phone) {
            updateField(Auth.getCurrentUser().getUid(), "phone", phone);
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public List<String> getLikes() {
        return likes;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }
}
