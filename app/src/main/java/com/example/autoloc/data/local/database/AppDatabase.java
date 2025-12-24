package com.example.autoloc.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.autoloc.data.local.dao.ReservationDao;
import com.example.autoloc.data.local.dao.UtilisateurDao;
import com.example.autoloc.data.local.dao.VoitureDao;
import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.data.local.entity.Voiture;

@Database(
        entities = {
                Utilisateur.class,
                Voiture.class,
                Reservation.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "autoloc_db";
    private static volatile AppDatabase INSTANCE;

    // DAO
    public abstract UtilisateurDao utilisateurDao();
    public abstract VoitureDao voitureDao();
    public abstract ReservationDao reservationDao();

    // Singleton
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
