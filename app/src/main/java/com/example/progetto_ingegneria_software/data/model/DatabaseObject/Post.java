package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import android.net.Uri;

import android.util.Log;

import com.example.progetto_ingegneria_software.data.model.Database;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

/**
 * Post has to implement Serializable to enable casting when passed between fragments
 */
public class Post implements Serializable{
    private String author;
    private String content;
    private List<Comment> comments;
    private Object timestamp;
    private Integer postId;
    private String image;
    private List<String> likes;

    public static PostDB postDB;
    static {
        postDB = new PostDB();
    }

    public Post() {
        this.author = "";
        this.comments = new ArrayList<>();
        this.timestamp = FieldValue.serverTimestamp();
        this.content = "";
        this.postId = 0;
        this.image = "";
        this.likes = new ArrayList<>();
    }

    public Post(String author, String content, List<Comment> comments, Integer postId, String image, List<String> likes) {
        this.author = author;
        this.content = content;
        this.comments = comments;
        this.timestamp = FieldValue.serverTimestamp();
        this.postId = postId;
        this.image = image;
        this.likes = likes;
    }

    public static class PostDB extends Database {
        private final String TAG = "PostDB";
        public PostDB() {
            super("posts");
        }

        /**
         * Creates a Post object and adds it to the database
         * @param author username of the author
         * @param content content of the post
         */
        public void createPost(User author, String content, Uri imageUri) {
            getCollection().orderBy("postId", Query.Direction.DESCENDING).limit(1).get()
                    .addOnCompleteListener( task -> {
                        if (task.isSuccessful()) {
                            //get the most recent post's id
                            DocumentSnapshot q = task.getResult().getDocuments().get(0);
                            int id = 0;

                            if (q.exists()) {
                                id = Integer.parseInt(q.getId());
                            }
                            id++;

                            String url;
                            if(imageUri.toString().isEmpty()) {
                                url = "";
                            } else {
                                url = "/images/postPictures/" + id + ".jpg";

                                //upload image
                                StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/postPictures/");
                                StorageReference fileReference = storageReference.child(id + ".jpg");

                                fileReference.putFile(imageUri);
                            }

                            Post p = new Post(author.getUsername(), content, new ArrayList<>(), id, url, new ArrayList<>());
                            addDocument(Integer.toString(id), p);
                        } else {
                            Log.d(TAG, "Error: Failed to create Post");
                        }
                    });
        }

        @Override
        public DocumentReference getDocument(String id) {
            return super.getDocument(id);
        }

        @Override
        public void updateField(String documentId, String field, Object data) {
            super.updateField(documentId, field, data);
        }

        /**
         * Fetches All Posts data from the database
         * @param callback Callback interface
         */
        public void fetchPosts(DatabaseCallback<List<Post>> callback) {
            getCollection().orderBy("timestamp", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener( task -> {
                        List<Post> l = new ArrayList<>();

                        for (DocumentSnapshot t : task.getResult()) {
                            Post p = t.toObject(Post.class);
                            l.add(p);
                        }

                        callback.onComplete(l);
                    });
        }

        /**
         * Fetches posts of the given user
         * @param author Username
         * @param callback Callback interface
         */
        public void fetchUserPosts(String author, DatabaseCallback<List<Post>> callback) {
            getCollection()
                    .whereEqualTo("author", author)
                    .orderBy("timestamp")
                    .get().addOnCompleteListener( task -> {
                        List<Post> l = new ArrayList<>();

                        for(DocumentSnapshot t : task.getResult()) {
                            Post p = t.toObject(Post.class);
                            l.add(p);
                        }

                        callback.onComplete(l);
                    });
        }
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

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
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

    public List<String> getLikes() {
        return likes;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getImage() {
        return image;
    }
}
