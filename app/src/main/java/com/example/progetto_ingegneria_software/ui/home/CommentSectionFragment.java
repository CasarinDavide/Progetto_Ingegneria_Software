package com.example.progetto_ingegneria_software.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Comment;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentCommentSectionBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FieldValue;

import java.util.List;


public class CommentSectionFragment extends Fragment {

    FragmentCommentSectionBinding binding;

    //I wanted to use this to hide the bottom navBar when you enter in the Comment section but it throws an exception, idk why
    //private final BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCommentSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Get arguments from HomeFragment
        assert getArguments() != null;
        Post post = (Post) getArguments().getSerializable("post");

        TextView content = binding.postContentCommentFragment;
        TextView username = binding.usernameCommentFragment;
        TextView commentInput = binding.commentInputCommentFragment;
        Button sendComment = binding.sendCommentButtonCommentFragment;

        content.setText(post.getContent());
        username.setText(post.getAuthor());


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
        super.onDestroyView();
        //navBar.setVisibility(View.VISIBLE );
        binding = null;
    }
}
