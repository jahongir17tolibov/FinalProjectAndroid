package com.jt17.finalprojectandroid;

import android.app.Application;

import com.jt17.finalprojectandroid.data.db.AppDatabase;

public class FinalProjectApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.getInstance(this);
    }
}
