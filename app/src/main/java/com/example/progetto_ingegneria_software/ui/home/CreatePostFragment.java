package com.example.progetto_ingegneria_software.ui.home;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCreatePostBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CreatePostFragment extends Fragment {

    private FragmentCreatePostBinding binding;
    private Button done;
    private TextView content;
    private String uid;
    Database userDb;
    Database postDb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //binds the Views of the fragment
        done = binding.confirmButtonCreatePost;
        content = binding.postContentInputCreatePost;

        //Get useful data from the database
        uid = Auth.getCurrentUser().getUid();
        userDb = new Database("users");
        postDb = new Database("posts");

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

        //This giant piece of ... code adds a post to the database, feel free to modify it but be careful!!
        //I haven't set the option to add an Image yet, but it shouldn't be difficult
        userDb.getDocument(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    User u = task.getResult().toObject(User.class);

                    //Confirm button, adds the post in the database
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Get text field value
                            String value = content.getText().toString();

                            //Adds post in the database
                            assert u != null;
                            String uName = u.getUsername();

                            postDb.getCollection().orderBy("timestamp").limit(1).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()) {
                                                //get the most recent post's id
                                                DocumentSnapshot q = task.getResult().getDocuments().get(0);
                                                int id = 0;

                                                if(q.exists()) {
                                                    id = Integer.parseInt(q.getId());
                                                }
                                                id++;

                                                Post p = new Post(uName, value, new ArrayList<>(), id, "", 0);
                                                postDb.addDocument(Integer.toString(id), p);
                                            }
                                        }
                                    });
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}