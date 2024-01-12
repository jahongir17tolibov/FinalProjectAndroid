package com.jt17.finalprojectandroid.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jt17.finalprojectandroid.data.dao.MainDao;
import com.jt17.finalprojectandroid.data.entity.MyEntity;

@Database(entities = {MyEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MainDao mainDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "nazarmatov_najmidins_db"
            ).build();
        }
        return instance;
    }

}
