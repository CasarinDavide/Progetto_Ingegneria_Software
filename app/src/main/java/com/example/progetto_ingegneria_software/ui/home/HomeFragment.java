package com.example.progetto_ingegneria_software.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

        //SwipeRefreshLayout
        final SwipeRefreshLayout refreshLayout = binding.swipeRefreshLayout;
        loadPosts(recyclerView, refreshLayout);


        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                loadPosts(recyclerView, refreshLayout);
            }
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
        create.setOnClickListener( view -> Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_navigation_create_post) );

        return root;
    }

    private void loadPosts(RecyclerView r, SwipeRefreshLayout s) {
        Post.postDB.fetchPosts(list -> {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
            r.setAdapter(adapter);

            s.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}