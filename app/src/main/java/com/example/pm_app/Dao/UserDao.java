package com.example.pm_app.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pm_app.Entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User findOneByUsername(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password_hash = :passwordHash LIMIT 1")
    User findOneWithCredentials(String username, String passwordHash);

    @Insert()
    void registerUser(User user);
}
