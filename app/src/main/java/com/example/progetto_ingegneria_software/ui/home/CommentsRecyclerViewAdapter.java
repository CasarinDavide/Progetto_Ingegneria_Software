package com.example.progetto_ingegneria_software.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Comment;



import java.util.List;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.CommentsViewHolder> {

    private List<Comment> comments;

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        private final TextView username;
        private final TextView content;

        public CommentsViewHolder(View view) {
            super(view);

            this.username = view.findViewById(R.id.username_comment_item);
            this.content = view.findViewById(R.id.content_comment_item);
        }
    }

    public CommentsRecyclerViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPosts(List<Comment> comments) {
        this.comments = comments;
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
    public CommentsRecyclerViewAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentsViewHolder(view);
    }

    /**
     * Replaces the data of the View with the given data of the List<Comment> dataset
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CommentsRecyclerViewAdapter.CommentsViewHolder holder, int position) {

        Comment comment = comments.get(position);

        holder.content.setText(comment.getComment());
        holder.username.setText(comment.getUsername());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
