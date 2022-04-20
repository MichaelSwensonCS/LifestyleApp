package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lifestyleapp.databinding.FragmentProfileListBinding;

import java.util.List;

public class ProfileListFragment extends Fragment implements View.OnClickListener {

    private static FragmentProfileListBinding binding;
    private UserViewModel model;

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

        model = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        binding.buttonNewUser.setOnClickListener(this);
        binding.recyclerUserList.setLayoutManager(new LinearLayoutManager(getContext()));

        final UserAdapter adapter = new UserAdapter(model);
        binding.recyclerUserList.setAdapter(adapter);

        final Observer<List<User>> userObserver = new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) { adapter.setUsers(users);}
        };
        model.getUsers().observe(getViewLifecycleOwner(), userObserver);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonNewUser:
                User user = new User();
                model.insert(user);
                binding.recyclerUserList.getAdapter().notifyItemChanged(model.getUsers().getValue().size());
                break;
        }
    }
}