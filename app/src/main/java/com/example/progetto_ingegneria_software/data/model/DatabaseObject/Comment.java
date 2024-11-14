package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import java.io.Serializable;

/**
 * Comment has to implement Serializable to enable casting when passed between fragments
 */
public class Comment implements Serializable {
    private String username;
    private String comment;

    Comment() {
        this.username = "";
        this.comment = "";
    }

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public void setComment(String c) {
        this.comment = c;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
