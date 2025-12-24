package com.example.autoloc.application;

import android.app.Application;

import com.example.autoloc.data.local.database.AppDatabase;

public class AutoLocApplication extends Application {

    private static AutoLocApplication instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static AutoLocApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
