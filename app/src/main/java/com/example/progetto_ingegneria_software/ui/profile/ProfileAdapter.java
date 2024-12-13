package com.example.progetto_ingegneria_software.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.progetto_ingegneria_software.R;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.storage.FirebaseStorage;


import java.util.List;
import java.util.Set;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private List<String> profileNames;

    public ProfileAdapter(List<String> profileNames) {
        this.profileNames = profileNames;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.profileNameTextView.setText(profileNames.get(position));
    }

    @Override
    public int getItemCount() {
        return profileNames.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView profileNameTextView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profileNameTextView = itemView.findViewById(R.id.profile_name);
        }
    }
}
