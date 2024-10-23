package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

public class User {

    protected String _email;
    protected String _uid;

    public User(String uid,String email)
    {
        this._uid = uid;
        this._email = email;
    }
}
