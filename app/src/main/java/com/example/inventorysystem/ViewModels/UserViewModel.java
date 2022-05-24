package com.example.inventorysystem.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorysystem.Repositories.UserRepository;
import com.example.inventorysystem.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

        private UserRepository repository;
        private LiveData<List<User>> allUsers;
        private User userNameAndPassword;
        private User currentUser;

        public UserViewModel(@NonNull Application application) {
            super(application);
            repository = new UserRepository(application);
            allUsers = repository.getAllUsers();
        }

        public void insert (User user){
            repository.insert(user);
        }

        public void update(User user){
            repository.update(user);
        }

        public void delete(User user){
            repository.delete(user);
        }

        public LiveData<List<User>> getAllUsers(){
            return (LiveData<List<User>>) allUsers;
        }

        public User getUserById(int userId){
            currentUser = repository.getUserById(userId);
            return currentUser;
        }

    public User getUserNameAndPassword(String userName, String password){
        userNameAndPassword = repository.getUserNameAndPassword(userName, password);
        return userNameAndPassword;
    }
}
