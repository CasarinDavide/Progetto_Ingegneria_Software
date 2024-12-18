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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentProfileBinding;
import com.example.progetto_ingegneria_software.ui.home.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //set profile image
        ImageView profilePicture = binding.profilePictureFragmentProfile;
        Context context = binding.profilePictureFragmentProfile.getContext();

        //set profile name
        final RecyclerView nameRecyclerView = binding.nameRecyclerViewProfile;
        nameRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Set recycler view
        final RecyclerView recyclerView = binding.postsRecyclerViewProfile;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        User.userDB.getUserInfo( userInfo -> {

            //fetch user's posts
            Post.postDB.fetchUserPosts(userInfo.getUsername(), list -> {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
                recyclerView.setAdapter(adapter);
            });

            //fetch username
            List<String> profileNames = List.of(userInfo.getUsername());
            ProfileAdapter profileAdapter = new ProfileAdapter(profileNames);
            nameRecyclerView.setAdapter(profileAdapter);

            //set profile picture
            FirebaseStorage.getInstance().getReference(userInfo.getProfilePicture())
                    .getDownloadUrl()
                    .addOnCompleteListener(task -> Glide.with(context)
                                                    .load(task.getResult())
                                                    .override(100,100)
                                                    .into(profilePicture)
                    );

            //set profile name

        });

        //Set modify profile button
        final Button modify = binding.profileInfoButton;
        modify.setOnClickListener( view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_profile_to_navigation_modify_profile);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}