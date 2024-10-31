package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private String author;
    private String content;
    private List<Comment> comments;
    private Date date;
    private int postId;
    private String image;
    private int likes;


    public Post() {
        this.author = "";
        this.comments = new ArrayList<Comment>();
        this.date = new Date();
        this.content = "";
        this.postId = 0;
        this.image = "";
        this.likes = 0;
    }

    public Post(String author, String content, List<Comment> comments, Date date, int postId, String image, int likes) {
        this.author = author;
        this.comments = comments;
        this.date = date;
        this.postId = postId;
        this.image = image;
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getPostId() {
        return postId;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getImage() {
        return image;
    }
}
