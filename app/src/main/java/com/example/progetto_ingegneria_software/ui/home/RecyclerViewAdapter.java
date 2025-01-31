package com.example.progetto_ingegneria_software.ui.home;

import android.content.Context;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.progetto_ingegneria_software.R;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.storage.FirebaseStorage;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, Bundle post);
    }

    private final List<Post> p;
    private final OnItemClickListener onclickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView profilePicture;
        private final TextView username;
        private final TextView postContent;
        private final TextView likeNumber;
        private final ImageButton likeButton;
        private final ImageButton favouriteButton;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);

            profilePicture = view.findViewById(R.id.profile_picture_recycler_view);
            username = view.findViewById(R.id.username_recycler_view);
            postContent = view.findViewById(R.id.post_content_recycler_view);
            likeNumber = view.findViewById(R.id.post_likes_number_recycler_view);
            likeButton = view.findViewById(R.id.post_like_recycler_view);
            favouriteButton = view.findViewById(R.id.post_favourites_recycler_view);
            image = view.findViewById(R.id.post_image_recycler_view);
        }
    }

    public RecyclerViewAdapter(List<Post> p, OnItemClickListener onclickListener) {
        this.p = p;
        this.onclickListener = onclickListener;
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
        Context context = holder.profilePicture.getContext();
        holder.profilePicture.setLayoutParams(new RecyclerView.LayoutParams(100, 100));

        holder.itemView.setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    onclickListener.onItemClick(view, bundle);
                }
        );

        holder.postContent.setText(post.getContent());
        holder.username.setText(post.getAuthor());
        holder.likeNumber.setText(String.valueOf(post.getLikes().size()));

        //set post's profile picture
        User.userDB.getCollection()
                .whereEqualTo("username", post.getAuthor())
                .get()
                .addOnCompleteListener( querySnapshot -> {
                    DocumentSnapshot u = querySnapshot.getResult().getDocuments().get(0);

                    User userInfo = u.toObject(User.class);

                    //set author profile picture
                    //assert userInfo != null;
                    if(Auth.getCurrentUser()!=null) {
                        FirebaseStorage.getInstance().getReference(userInfo.getProfilePicture())
                                .getDownloadUrl()
                                .addOnCompleteListener(task -> Glide.with(context)
                                        .load(task.getResult())
                                        .override(100, 100)
                                        .circleCrop()
                                        .into(holder.profilePicture));
                    }

                });

        User.userDB.getUserInfo( userInfo -> {
            //set the like button icon, if the user previously liked it or not
            if(post.getLikes().contains(userInfo.getUid())) {
                holder.likeButton.setImageResource(R.drawable.baseline_thumb_up_24dp);
            } else {
                holder.likeButton.setImageResource(R.drawable.outline_thumb_up_24dp);
            }

            //set the favourite button icon, check if the user already added it to its favourites
            if(userInfo.getFavourites().contains(post.getPostId().toString())) {
                holder.favouriteButton.setImageResource(R.drawable.baseline_favourite_24dp);
            } else {
                holder.favouriteButton.setImageResource(R.drawable.outline_favourite_24dp);
            }

            if(!post.getImage().isEmpty()) {
                FirebaseStorage.getInstance().getReference(post.getImage())
                        .getDownloadUrl()
                        .addOnCompleteListener(task -> Glide.with(context)
                                .load(task.getResult())
                                .override(900, 900)
                                .into(holder.image));
            }

        });

        //set click listener for every button of the recyclerview
        holder.likeButton.setOnClickListener( view -> {
            //check if the post is in user's liked posts
            String postId = post.getPostId().toString();
            String uid = Auth.getCurrentUser().getUid();

            List<String> likes = post.getLikes();
            int likeNumber = likes.size();

            //if the post is already liked, deletes it from liked posts, otherwise add it to liked posts
            if (likes.contains(uid)) {
                holder.likeButton.setImageResource(R.drawable.outline_thumb_up_24dp);
                holder.likeNumber.setText(String.valueOf(likeNumber-1));

                likes.remove(uid);
            } else {
                holder.likeButton.setImageResource(R.drawable.baseline_thumb_up_24dp);
                holder.likeNumber.setText(String.valueOf(likeNumber+1));

                likes.add(uid);
            }

            Post.postDB.updateField(postId, "likes", likes);
        });

        holder.favouriteButton.setOnClickListener( view -> {
            User.userDB.getUserInfo( userInfo -> {
                List<String> fav = userInfo.getFavourites();
                String postId = post.getPostId().toString();

                //if user's favourites list already contains the post, remove it
                if( fav.contains(post.getPostId().toString()) ) {
                    holder.favouriteButton.setImageResource(R.drawable.outline_favourite_24dp);
                    fav.remove(postId);
                } else {
                    holder.favouriteButton.setImageResource(R.drawable.baseline_favourite_24dp);
                    fav.add(postId);
                }

                User.userDB.updateField(userInfo.getUid(), "favourites", fav);
            });
        });
    }

    @Override
    public int getItemCount() {
        return p.size();
    }
}
