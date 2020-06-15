package com.example.inventorysystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "userName")
    private String userName;

    private String firstName;
    private String lastName;

    @ColumnInfo(name = "password")
    private String password;


    public User(String userName, String firstName, String lastName, String password) {
        this.userName = userName;
         this.firstName = firstName;
         this.lastName = lastName;
         this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
