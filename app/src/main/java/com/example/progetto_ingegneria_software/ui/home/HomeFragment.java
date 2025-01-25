package com.example.progetto_ingegneria_software.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentHomeBinding;
import com.google.firebase.storage.FirebaseStorage;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //set profile image
        ImageView profilePicture = binding.profilePictureFragmentModifyProfile;
        Context context = binding.profilePictureFragmentModifyProfile.getContext();

        //RecyclerView creation
        final RecyclerView recyclerView = binding.postsRecyclerViewHome;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        Post.postDB.fetchPosts(list -> {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        });

        User.userDB.getUserInfo( userInfo -> {
                    //set profile picture
                    FirebaseStorage.getInstance().getReference(userInfo.getProfilePicture())
                            .getDownloadUrl()
                            .addOnCompleteListener(
                                    task -> Glide.with(context)
                                            .load(task.getResult())
                                            .override(100, 100)
                                            .circleCrop()
                                            .into(profilePicture)
                            );
        });

        //CreatePost button
        final ImageView create = binding.createPostButton;
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_navigation_create_post);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}