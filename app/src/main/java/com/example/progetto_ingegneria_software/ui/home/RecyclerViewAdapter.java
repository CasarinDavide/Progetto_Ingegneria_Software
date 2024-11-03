package com.example.progetto_ingegneria_software.ui.home;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Post> p;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView profilePicture;
        private final TextView username;
        private final TextView postContent;
        private final TextView likeNumber;
        private final ImageButton likeButton;
        private final ImageButton favouriteButton;

        public ViewHolder(View view) {
            super(view);

            profilePicture = view.findViewById(R.id.profile_picture_recycler_view);
            username = view.findViewById(R.id.username_recycler_view);
            postContent = view.findViewById(R.id.post_content_recycler_view);
            likeNumber = view.findViewById(R.id.post_likes_number_recycler_view);
            likeButton = view.findViewById(R.id.post_like_recycler_view);
            favouriteButton = view.findViewById(R.id.post_favourites_recycler_view);
        }
    }

    public RecyclerViewAdapter(List<Post> p) {
        this.p = p;
    }

    public void setPosts(List<Post> p) {
        this.p = p;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return The newly created View
     */
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Replaces the data of the View with the given data of the List<Post> dataset
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Post post = p.get(position);

        holder.profilePicture.setImageURI(Uri.parse(post.getImage()));
        holder.postContent.setText(post.getContent());
        holder.username.setText(post.getAuthor());
        holder.likeNumber.setText(String.valueOf(post.getLikes()));

        //set the like button icon, if the user previously liked it or not
        User.userDB.getDocument(Auth.getCurrentUser().getUid()).get()
                        .addOnCompleteListener( task -> {
                            DocumentSnapshot d = task.getResult();
                            if(d != null && d.exists()) {
                                User userInfo = d.toObject(User.class);

                                assert userInfo != null;
                                if(userInfo.getLikes().contains(post.getPostId().toString())) {
                                    holder.likeButton.setImageResource(R.drawable.baseline_thumb_up_24dp);
                                } else
                                    holder.likeButton.setImageResource(R.drawable.outline_thumb_up_24dp);
                            }
                        });

        //set click listener for every button of the recyclerview
        holder.likeButton.setOnClickListener( view -> {
            //check if the post is in user's liked posts
            User.userDB.getDocument(Auth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener( userTask -> {
                        DocumentSnapshot u = userTask.getResult();

                        String postId = post.getPostId().toString();
                        String uid = Auth.getCurrentUser().getUid();

                        User userInfo = u.toObject(User.class);
                        assert userInfo != null;
                        List<String> likedPosts = userInfo.getLikes();


                        Post.postDB.getDocument(postId).get()
                                .addOnCompleteListener( postTask -> {
                                    DocumentSnapshot p = postTask.getResult();

                                    Post postInfo = p.toObject(Post.class);
                                    assert postInfo != null;
                                    Integer likes = postInfo.getLikes();

                                    //if the post is already liked, deletes it from liked posts, otherwise add it to liked posts
                                    if (likedPosts.contains(postId)) {
                                        holder.likeButton.setImageResource(R.drawable.outline_thumb_up_24dp);
                                        holder.likeNumber.setText(String.valueOf(likes-1));

                                        Post.postDB.updateField(postId, "likes", likes-1);
                                        likedPosts.remove(postId);
                                        User.userDB.updateField(uid, "likes", likedPosts);
                                    } else {
                                        holder.likeButton.setImageResource(R.drawable.baseline_thumb_up_24dp);
                                        holder.likeNumber.setText(String.valueOf(likes+1));

                                        Post.postDB.updateField(postId, "likes", likes+1);
                                        likedPosts.add(postId);
                                        User.userDB.updateField(uid, "likes", likedPosts);
                                    }

                                });


                    });
        });

        holder.favouriteButton.setOnClickListener( view -> {

        });
    }

    @Override
    public int getItemCount() {
        return p.size();
    }
}
