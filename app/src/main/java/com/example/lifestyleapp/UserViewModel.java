package com.example.lifestyleapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repo;

    private LiveData<List<User>> users;
    private MutableLiveData<User> user;

    public UserViewModel(@NonNull Application app) {
        super(app);
        repo = new UserRepository(app);
        users = repo.getUsers();
    }

    public LiveData<List<User>> getUsers() {
        /*if (users == null) {
            users = new LiveData<ArrayList<User>>();
            ArrayList<User> newUsers = new ArrayList<>();
            if (user != null)
                newUsers.add(getUser().getValue());
            users.setValue(newUsers);
        }*/
        return users;
    }

    public void insert(User user) {
        repo.insert(user);
    }

    public void update(User user) {
        repo.update(user);
    }

    public void delete(User user) {
        repo.delete(user);
    }

    public LiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<User>();
            User newUser = new User();
            user.setValue(newUser);
            insert(newUser);
        }
        return user;
    }

    /*public void delete(User user) {
        ArrayList<User> users = this.users.getValue();
        users.remove(user);
        if (this.user.equals(user))
            this.user.setValue(users.get(0));
        this.users.setValue(users);
    }

    public void delete(int index) {
        delete(this.users.getValue().get(index));
    }*/

    public void setUser(User user) {
        this.user.setValue(user);
    }

    /*public void setUser(int index) {
        user.setValue(users.getValue().get(index));
    }*/

    public void updateCurrentUser(User user) {
        //getUser().getValue().copy(user);
        repo.update(user);
    }
}


