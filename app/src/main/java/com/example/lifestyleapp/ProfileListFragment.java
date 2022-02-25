package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lifestyleapp.databinding.FragmentProfileListBinding;

public class ProfileListFragment extends Fragment implements View.OnClickListener {

    FragmentProfileListBinding binding;

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

        binding.buttonNewUser.setOnClickListener(this);
        binding.recyclerUserList.setAdapter(new UserAdapter());
        binding.recyclerUserList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonNewUser:
                User user = new User();
                ProfileFragment.user = user;
                ProfileFragment.users.add(user);
                binding.recyclerUserList.getAdapter().notifyItemChanged(ProfileFragment.users.size());
                break;
        }
    }
}