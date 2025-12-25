package com.example.autoloc.application;

import android.app.Application;

import com.example.autoloc.data.local.database.AppDatabase;
import com.example.autoloc.data.local.database.DatabaseSeeder;

public class AutoLocApplication extends Application {

    private static AutoLocApplication instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);

        // Peupler la base de données avec des données de test
        // Cette opération s'exécute en arrière-plan et ne bloque pas le démarrage de l'app
        DatabaseSeeder seeder = new DatabaseSeeder(this);
        seeder.seedDatabase();
    }

    public static AutoLocApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}