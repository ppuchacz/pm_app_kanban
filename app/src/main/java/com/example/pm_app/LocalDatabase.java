package com.example.pm_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pm_app.Dao.UserDao;
import com.example.pm_app.Entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    static final String DB_NAME = "pm-kanban-app";

    public abstract UserDao userDao();
    private static LocalDatabase localDB;

    public static LocalDatabase getInstance(Context context) {
        if (LocalDatabase.localDB == null) {
            LocalDatabase.localDB = LocalDatabase.buildDatabaseInstance(context);
        }

        return LocalDatabase.localDB;
    }

    private static LocalDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, LocalDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
