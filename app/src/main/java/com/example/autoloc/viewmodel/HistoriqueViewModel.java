package com.example.autoloc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.autoloc.data.local.entity.ReservationAvecVoiture;
import com.example.autoloc.data.repository.ReservationRepository;

import java.util.List;

public class HistoriqueViewModel extends AndroidViewModel {

    private final ReservationRepository reservationRepository;
    private final LiveData<List<ReservationAvecVoiture>> historiqueReservations;

    public HistoriqueViewModel(@NonNull Application application) {
        super(application);
        reservationRepository = new ReservationRepository(application);

        // Charger l'historique des réservations (terminées ou annulées)
        historiqueReservations = reservationRepository.getHistoriqueReservations();
    }

    /**
     * Récupère l'historique des réservations (terminées et annulées)
     * @return LiveData contenant la liste des réservations avec leurs voitures
     */
    public LiveData<List<ReservationAvecVoiture>> getHistoriqueReservations() {
        return historiqueReservations;
    }
}