package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String author;
    private String content;
    private List<Comment> comments;
    private FieldValue timestamp;
    private Integer postId;
    private String image;
    private int likes;


    public Post() {
        this.author = "";
        this.comments = new ArrayList<Comment>();
        this.timestamp = FieldValue.serverTimestamp();
        this.content = "";
        this.postId = 0;
        this.image = "";
        this.likes = 0;
    }

    public Post(String author, String content, List<Comment> comments, Integer postId, String image, int likes) {
        this.author = author;
        this.content = content;
        this.comments = comments;
        this.timestamp = FieldValue.serverTimestamp();
        this.postId = postId;
        this.image = image;
        this.likes = likes;
    }

    public FieldValue getTimestamp() {
        return timestamp;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setTimestamp(FieldValue timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getImage() {
        return image;
    }
}
