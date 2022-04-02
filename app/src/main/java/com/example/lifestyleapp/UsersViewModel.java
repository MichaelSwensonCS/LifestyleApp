package com.example.lifestyleapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class UsersViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<User>> users;
    private MutableLiveData<User> user;
    private MutableLiveData<Weather> weather;
    private Repository dataRepository;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        dataRepository = Repository.getInstance(application);
        weather = dataRepository.getData();
    }

    public LiveData<ArrayList<User>> getUserList() {
        if (users == null) {
            users = new MutableLiveData<ArrayList<User>>();
            ArrayList<User> newUsers = new ArrayList<>();
            /*if (user != null)
                newUsers.add(getUser().getValue());*/
            users.setValue(newUsers);
        }
        return users;
    }
    //TODO Trigger weather on user city change Here and WeatherView
    public LiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<User>();
            return newUser();
        }
        return user;
    }

    public LiveData<User> newUser() {
        User newUser = new User();
        setUser(newUser);
        addUser(newUser);
        return user;
    }

    public void setCity(String location){
        dataRepository.setLocation(location);
    }

    public void delete(User user) {
        ArrayList<User> users = this.users.getValue();
        users.remove(user);
        if (this.user.equals(user))
            this.user.setValue(users.get(0));
        this.users.setValue(users);
    }

    public LiveData<Weather> getData(){
        return weather;
    }

    public void delete(int index) {
        delete(this.users.getValue().get(index));
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public void setUser(int index) {
        user.setValue(users.getValue().get(index));
    }

    public void addUser(User user) {
        ArrayList<User> users = getUserList().getValue();
        users.add(user);
        this.users.setValue(users);
    }

    public void updateCurrentUser(User user) {
        getUser().getValue().copy(user);
    }
}


