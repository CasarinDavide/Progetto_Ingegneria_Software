package com.example.progetto_ingegneria_software.ui.profile;


import android.net.Uri;
import android.os.Bundle;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;


import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.databinding.FragmentModifyProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;


public class ModifyProfileFragment extends Fragment {
    private FragmentModifyProfileBinding binding;
    //same problem, I try to hide it but it doesn't work
    //private BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);


    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                if(uri != null) {
                    String location = "images/profilePictures/";
                    String imgName = Auth.getCurrentUser().getUid() + ".jpg";

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(location);

                    StorageReference fileReference = storageReference.child(imgName);
                    fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(requireContext(), "Foto profilo aggiornata correttamente", Toast.LENGTH_SHORT).show();
                            User.userDB.setUserImage(location + imgName);
                        }
                    });
                }
            }
    );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentModifyProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //get all view elements
        ImageView profilePicture = binding.profilePictureFragmentModifyProfile;
        ImageButton uploadPicture = binding.uploadProfilePictureFragmentModifyProfile;
        EditText email = binding.inputEmailFragmentModifyProfile;
        EditText phone = binding.inputPhoneFragmentModifyProfile;
        TextView changePassword = binding.changePasswordFragmentModifyProfile;
        Button submit = binding.doneButtonFragmentModifyProfile;

        User.userDB.getUserInfo( userInfo -> {
            //set profile picture
            FirebaseStorage.getInstance().getReference(userInfo.getProfilePicture())
                    .getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Glide.with(profilePicture.getContext())
                                    .load(task.getResult())
                                    .override(500, 500)
                                    .circleCrop()
                                    .into(profilePicture);
                        }
                    });
            email.setText(userInfo.getEmail());
            phone.setText(userInfo.getPhone());


            changePassword.setOnClickListener( view -> {
                Auth.resetPassword(getActivity(), userInfo.getEmail());
                Toast.makeText(requireActivity().getApplicationContext(), "A link to your email has been sent", Toast.LENGTH_LONG).show();
            });

            uploadPicture.setOnClickListener( view -> {
                openGallery();
            });

            //submit button
            submit.setOnClickListener( view -> {
                String newEmail = email.getText().toString();
                String newPhone = phone.getText().toString();

                if(!(newEmail.isEmpty() && newPhone.isEmpty())
                        && Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
                        && Patterns.PHONE.matcher(newPhone).matches()) {

                    User.userDB.setUserEmail(newEmail);
                    User.userDB.setUserPhone(newPhone);
                    //navBar.setVisibility(View.VISIBLE);

                    FragmentManager fm = getParentFragmentManager();
                    fm.popBackStack();
                } else {
                    Toast.makeText(requireActivity().getApplicationContext(), "Invalid input", Toast.LENGTH_LONG).show();
                }
            });
        });

        //navBar.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //navBar.setVisibility(View.VISIBLE);
        binding = null;
    }

    public void openGallery() {
        galleryLauncher.launch("image/");
    }

}