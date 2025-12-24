package com.example.autoloc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.data.repository.ReservationRepository;

import java.util.List;

public class ReservationViewModel extends AndroidViewModel {

    private final ReservationRepository reservationRepository;

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        reservationRepository = new ReservationRepository(application);
    }

    // 🔹 Exposition vers l'UI
    public LiveData<List<Reservation>> getReservationsUtilisateur(int idUtilisateur) {
        return reservationRepository.getReservationsUtilisateur(idUtilisateur);
    }

    public LiveData<List<Reservation>> getReservationsVoiture(int idVoiture) {
        return reservationRepository.getReservationsVoiture(idVoiture);
    }

    public LiveData<List<Reservation>> getToutesLesReservations() {
        return reservationRepository.getToutesLesReservations();
    }

    // 🔹 Actions
    public void inserer(Reservation reservation) {
        reservationRepository.inserer(reservation);
    }

    public void modifier(Reservation reservation) {
        reservationRepository.modifier(reservation);
    }
}
