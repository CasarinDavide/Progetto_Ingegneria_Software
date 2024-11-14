package com.example.progetto_ingegneria_software.ui.home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCreatePostBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private Button done;
    private TextView content;
    private String uid;
    private ImageButton addImage;
    private Uri imageUri = Uri.parse("");

    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if(uri != null) {
                    imageUri = uri;
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

        //Get useful data from the database
        uid = Auth.getCurrentUser().getUid();

        //Undo post creation
        ImageButton exit = binding.exitButtonCreatePost;
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getParentFragmentManager();
                fm.popBackStack();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User.userDB.getUserInfo( userInfo -> {

            //Confirm button, adds the post in the database
            done.setOnClickListener( buttonView -> {
                Post.postDB.createPost(userInfo, content.getText().toString(), imageUri);

                FragmentManager fm = getParentFragmentManager();
                fm.popBackStack();
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