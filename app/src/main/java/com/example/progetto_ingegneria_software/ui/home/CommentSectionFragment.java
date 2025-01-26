package com.example.progetto_ingegneria_software.ui.home;

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
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Comment;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCommentSectionBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;


public class CommentSectionFragment extends Fragment {

    FragmentCommentSectionBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCommentSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //this.requireActivity().findViewById(R.id.nav_host_fragment_activity_main).setVisibility(View.GONE);

        //Get arguments from HomeFragment
        assert getArguments() != null;
        Post post = (Post) getArguments().getSerializable("post");

        TextView content = binding.postContentCommentFragment;
        TextView username = binding.usernameCommentFragment;
        ImageView imageView = binding.postImageRecyclerView;
        TextView commentInput = binding.commentInputCommentFragment;
        ImageButton sendComment = binding.sendCommentButtonCommentFragment;

        content.setText(post.getContent());
        username.setText(post.getAuthor());

        if(!post.getImage().isEmpty()) {
            FirebaseStorage.getInstance().getReference(post.getImage())
                    .getDownloadUrl()
                    .addOnCompleteListener(task -> Glide.with(this)
                            .load(task.getResult())
                            .override(900, 900)
                            .into(imageView));


        }



        List<Comment> comments = post.getComments();

        //set commentsRecyclerView
        final RecyclerView commentsRecyclerView = binding.commentsRecyclerView;
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        CommentsRecyclerViewAdapter commentAdapter = new CommentsRecyclerViewAdapter(comments);
        commentsRecyclerView.setAdapter(commentAdapter);

        //add comment to post
        sendComment.setOnClickListener( view -> {
            String c = commentInput.getText().toString();
            if(!c.isEmpty()) {
                User.userDB.getUsername( u -> {
                    Comment newComment = new Comment(u, c);
                    comments.add(newComment);
                    commentAdapter.notifyItemInserted(comments.size() - 1);
                    commentsRecyclerView.scrollToPosition(comments.size() - 1);
                    binding.commentInputCommentFragment.setText("");

                    //ad comment to the database
                    Post.postDB.getDocument(post.getPostId().toString()).get()
                            .addOnCompleteListener( documentSnapshotTask -> {
                                DocumentReference commentList = documentSnapshotTask.getResult().getReference();
                                commentList.update("comments", FieldValue.arrayUnion(newComment));
                            })
                            .addOnFailureListener( exception -> {
                                Toast.makeText(requireContext(), "Failed to insert comment: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                            });

                });
            } else {
                Toast.makeText(requireContext(), "Insert a comment", Toast.LENGTH_LONG).show();
            }
        });

        //navBar.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onDestroyView() {
        //this.requireActivity().findViewById(R.id.nav_host_fragment_activity_main).setVisibility(View.VISIBLE);
        super.onDestroyView();
    }
}
