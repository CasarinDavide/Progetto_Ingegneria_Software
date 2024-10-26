package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import java.util.List;

public class User {

    private  String email;
    private String uid;
    private List<String> favourites;
    private String profilePicture;
    private String phone;
    private String username;

    public User() {}

    public User(String username, String phone, String profilePicture, List<String> favourites, String uid, String email) {
        this.username = username;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.favourites = favourites;
        this.uid = uid;
        this.email = email;
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
}
