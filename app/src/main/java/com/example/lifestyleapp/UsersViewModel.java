package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private MutableLiveData<User> user;

    public LiveData<List<User>> getUserList() {
        if (users == null) {
            users = new MutableLiveData<List<User>>();
            if (user != null)
                addUser(getUser().getValue());
        }
        return users;
    }

    public LiveData<User> getUser() {
        if (user == null) {
            User newUser = new User();
            setUser(newUser);
        }
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public void addUser(User user) {
        this.users.getValue().add(user);
    }
}


