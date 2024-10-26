package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

public class Comment {
    private final String username;
    private final String comment;

    Comment() {
        this.username = "";
        this.comment = "";
    }

    Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
