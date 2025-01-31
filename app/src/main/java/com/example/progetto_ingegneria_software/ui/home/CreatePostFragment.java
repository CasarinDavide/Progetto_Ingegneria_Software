package com.example.progetto_ingegneria_software.ui.home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCreatePostBinding;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private Button done;
    private TextView content;
    private ImageButton addImage;
    private Uri imageUri = Uri.parse("");
    private ImageView imageView;

    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if(uri != null) {
                    imageUri = uri;

                    Glide.with(this)
                            .load(uri) // image url
                            .override(200, 200) // resizing
                            .centerCrop()
                            .into(imageView);

                }
            }
    );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //binds the Views of the fragment
        done = binding.confirmButtonCreatePost;
        content = binding.postContentInputCreatePost;
        addImage = binding.addImageButtonCreatePost;
        imageView = binding.imageView;

        //Get useful data from the database
        String uid = Auth.getCurrentUser().getUid();

        //Undo post creation
        ImageButton exit = binding.exitButtonCreatePost;
        exit.setOnClickListener( view -> {
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User.userDB.getUserInfo( userInfo -> {

            //Confirm button, adds the post in the database

            done.setOnClickListener( buttonView -> {

                done.setActivated(false);

                if (content.getText().toString().isEmpty()){
                    done.setActivated(true);
                    Toast.makeText(this.getActivity(), "Inserire il contenuto del post.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post.postDB.createPost(userInfo, content.getText().toString(), imageUri);
                done.setActivated(true);
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_create_post_to_navigation_home);
            });

            addImage.setOnClickListener( buttonView -> {
                galleryLauncher.launch("image/");
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}