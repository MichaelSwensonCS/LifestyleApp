package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lifestyleapp.databinding.FragmentProfileListBinding;

public class ProfileListFragment extends Fragment implements View.OnClickListener {

    private static FragmentProfileListBinding binding;
    private UsersViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentProfileListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        binding.buttonNewUser.setOnClickListener(this);
        binding.recyclerUserList.setAdapter(new UserAdapter(model));
        binding.recyclerUserList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonNewUser:
                model.newUser();
                binding.recyclerUserList.getAdapter().notifyItemChanged(model.getUserList().getValue().size());
                break;
        }
    }
}