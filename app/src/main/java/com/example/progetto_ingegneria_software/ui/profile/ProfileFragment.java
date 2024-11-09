package com.example.progetto_ingegneria_software.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentDashboardBinding;
import com.example.progetto_ingegneria_software.databinding.FragmentProfileBinding;
import com.example.progetto_ingegneria_software.ui.home.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //set profile image
        ImageView profilePicture = binding.profilePictureFragmentProfile;
        Context context = binding.profilePictureFragmentProfile.getContext();

        //Set recycler view
        final RecyclerView recyclerView = binding.postsRecyclerViewProfile;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        User.userDB.getUserInfo( userInfo -> {

            //fetch user's posts
            Post.postDB.fetchUserPosts(userInfo.getUsername(), list -> {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
                recyclerView.setAdapter(adapter);
            });

            //set profile picture
            FirebaseStorage.getInstance().getReference(userInfo.getProfilePicture())
                    .getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(task.getResult())
                                    .into(new CustomTarget<Bitmap>(100, 100) {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            profilePicture.setImageBitmap(resource);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });
                        }
                    });
        });

        //Set modify profile button
        final Button modify = binding.profileInfoButton;
        modify.setOnClickListener( view -> {

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}