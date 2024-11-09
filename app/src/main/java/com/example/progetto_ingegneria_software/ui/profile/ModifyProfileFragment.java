package com.example.progetto_ingegneria_software.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.progetto_ingegneria_software.databinding.FragmentModifyProfileBinding;


public class ModifyProfileFragment extends Fragment {
    private FragmentModifyProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel dashboardViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentModifyProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
