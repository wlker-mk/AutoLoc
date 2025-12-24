package com.example.autoloc.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.autoloc.data.local.entity.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void inserer(Reservation reservation);

    @Update
    void modifier(Reservation reservation);

    @Query("SELECT * FROM reservations WHERE idUtilisateur = :idUtilisateur")
    LiveData<List<Reservation>> getReservationsParUtilisateur(int idUtilisateur);

    @Query("SELECT * FROM reservations WHERE idVoiture = :idVoiture")
    LiveData<List<Reservation>> getReservationsParVoiture(int idVoiture);

    @Query("SELECT * FROM reservations ORDER BY dateReservation DESC")
    LiveData<List<Reservation>> getToutesLesReservations();
}
