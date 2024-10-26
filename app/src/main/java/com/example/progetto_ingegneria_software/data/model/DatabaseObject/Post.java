package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import java.util.Date;
import java.util.List;

public class Post {
    private String author;
    private List<Comment> comments;
    private Date date;
    private int postId;
    private String image;
    private int likes;


    public Post() {}

    public Post(String author, List<Comment> comments, Date date, int postId, String image, int likes) {
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
