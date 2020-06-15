package com.example.inventorysystem.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorysystem.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table ORDER BY userName DESC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user_table WHERE id=:userId")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM user_table WHERE userName=:userName AND password=:password")
    User getUserNameAndPassword(String userName, String password);
}
