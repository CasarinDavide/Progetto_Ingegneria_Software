package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Database;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

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
        this.profilePicture = "";
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
