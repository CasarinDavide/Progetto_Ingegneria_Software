package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.StandardCallback;
import com.example.progetto_ingegneria_software.data.model.Mapper;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Mapper {

    private  String email;
    private String uid;
    private List<String> favourites;
    private String profilePicture;
    private String phone;
    private String username;
    private List<Species> inventory;


    public static UserDB userDB;
    static {
        userDB = new UserDB();
    }

    public User() {
        this.email = "";
        this.uid = "";
        this.favourites = new ArrayList<>();
        this.profilePicture = "/images/profilePictures/default_user_pfp.png";
        this.phone = "";
        this.username = "";
        this.inventory = new ArrayList<>();
    }

    public User(String username, String phone, String profilePicture, List<String> favourites, String uid, String email) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.profilePicture = profilePicture;
        this.favourites = favourites;
        this.uid = uid;
        this.inventory = new ArrayList<>();
    }

    public static class UserDB extends Database {
        UserDB() {
            super("users");
        }

        public void pushToInventory(Species species, StandardCallback callback)
        {
            // faccio l'update a db
            User.userDB.getById(Auth.getCurrentUser().getUid(),User.class,x->{
                x.inventory.add(species);
                User.userDB.updateRecord(x,callback);
            });
        }


        @Override
        public DocumentReference getDocument(String documentId) {
            return super.getDocument(documentId);
        }

        /**
         * Gets current user's username
         * @param callback the callback interface
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
         * @param callback The callback interface
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

        public void setUserImage(String path) {
            updateField(Auth.getCurrentUser().getUid(), "profilePicture", path);
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFavourites(List<String> likes) {
        this.favourites = likes;
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

    public void setInventory(List<Species> speciesList){
        this.inventory = speciesList;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public List<String> getFavourites() {
        return favourites;
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

    public List<Species> getInventory(){
        return this.inventory;
    }

}
