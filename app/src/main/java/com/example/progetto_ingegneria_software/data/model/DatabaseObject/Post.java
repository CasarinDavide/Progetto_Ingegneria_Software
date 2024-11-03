package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.progetto_ingegneria_software.data.model.Database;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;

import java.util.List;

public class Post {
    private String author;
    private String content;
    private List<Comment> comments;
    private Object timestamp;
    private Integer postId;
    private String image;
    private int likes;

    public static PostDB postDB;
    static {
        postDB = new PostDB();
    }

    public Post() {
        this.author = "";
        this.comments = new ArrayList<Comment>();
        this.timestamp = ServerValue.TIMESTAMP;
        this.content = "";
        this.postId = 0;
        this.image = "";
        this.likes = 0;
    }

    public Post(String author, String content, List<Comment> comments, Integer postId, String image, int likes) {
        this.author = author;
        this.content = content;
        this.comments = comments;
        this.timestamp = ServerValue.TIMESTAMP;
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
        public void createPost(String author, String content) {
            getCollection().orderBy("postId", Query.Direction.DESCENDING).limit(1).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //get the most recent post's id
                                DocumentSnapshot q = task.getResult().getDocuments().get(0);
                                int id = 0;

                                if (q.exists()) {
                                    id = Integer.parseInt(q.getId());
                                }
                                id++;

                                Post p = new Post(author, content, new ArrayList<>(), id, "", 0);
                                addDocument(Integer.toString(id), p);
                            } else {
                                Log.d(TAG, "Error: Failed to create Post");
                            }
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
         * Fetches Post data from the database
         * @param callback Function to get side effects
         */
        public void fetchPosts(DatabaseCallback<List<Post>> callback) {
            getCollection().orderBy("timestamp").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<Post> l = new ArrayList<>();

                    for (DocumentSnapshot t : task.getResult()) {
                        Post p = t.toObject(Post.class);
                        l.add(p);
                    }

                    callback.onComplete(l);
                }
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

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLikes() {
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
