package com.example.autoloc.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.autoloc.data.local.dao.ReservationDao;
import com.example.autoloc.data.local.database.AppDatabase;
import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.data.local.entity.ReservationAvecVoiture;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReservationRepository {

    private final ReservationDao reservationDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReservationRepository(Context context) {
        reservationDao = AppDatabase.getInstance(context).reservationDao();
    }

    public LiveData<List<Reservation>> getReservationsUtilisateur(int idUtilisateur) {
        return reservationDao.getReservationsParUtilisateur(idUtilisateur);
    }

    public LiveData<List<Reservation>> getReservationsVoiture(int idVoiture) {
        return reservationDao.getReservationsParVoiture(idVoiture);
    }

    public LiveData<List<Reservation>> getToutesLesReservations() {
        return reservationDao.getToutesLesReservations();
    }

    /**
     * Récupère l'historique des réservations (terminées ou annulées) avec les détails des voitures
     * @return LiveData contenant la liste des réservations avec leurs voitures
     */
    public LiveData<List<ReservationAvecVoiture>> getHistoriqueReservations() {
        return reservationDao.getHistoriqueReservations();
    }

    public void inserer(Reservation reservation) {
        executor.execute(() -> reservationDao.inserer(reservation));
    }

    public void modifier(Reservation reservation) {
        executor.execute(() -> reservationDao.modifier(reservation));
    }
}