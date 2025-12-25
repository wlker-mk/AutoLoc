package com.example.autoloc.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.data.local.entity.ReservationAvecVoiture;

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

    @Transaction // Essentiel pour les requêtes qui chargent des relations
    @Query("SELECT * FROM reservations WHERE statut IN ('TERMINEE', 'ANNULEE') ORDER BY dateDebut DESC")
    LiveData<List<ReservationAvecVoiture>> getHistoriqueReservations();

    // Si vous avez une méthode pour récupérer toutes les réservations, mettez-la aussi à jour
    @Transaction
    @Query("SELECT * FROM reservations")
    LiveData<List<ReservationAvecVoiture>> getAllReservationsWithVoiture();
}
