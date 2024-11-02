package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import java.util.ArrayList;
import java.util.List;

public class User {

    private  String email;
    private String uid;
    private List<String> favourites;
    private String profilePicture;
    private String phone;
    private String username;

    public User() {
        this.email = "";
        this.uid = "";
        this.favourites = new ArrayList<String>();
        this.profilePicture = "";
        this.phone = "";
        this.username = "";
    }

    public User(String username, String phone, String profilePicture, List<String> favourites, String uid, String email) {

        this(username,phone,email);
        this.profilePicture = profilePicture;
        this.favourites = favourites;
        this.uid = uid;
    }

    public User(String username,String phone,String email)
    {
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
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
