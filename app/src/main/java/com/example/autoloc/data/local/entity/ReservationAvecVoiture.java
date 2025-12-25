package com.example.autoloc.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ReservationAvecVoiture {

    @Embedded
    public Reservation reservation;

    @Relation(
            parentColumn = "idVoiture",
            entityColumn = "idVoiture"
    )
    public Voiture voiture;

    // Méthode utilitaire pour accéder facilement au nom de la voiture
    public String getVoitureNom() {
        if (voiture != null) {
            return voiture.getMarque() + " " + voiture.getModele();
        }
        return "Voiture inconnue"; // Fallback au cas où la voiture ne serait pas chargée
    }

    // Méthode utilitaire pour accéder facilement à l'URL de l'image
    public String getVoitureImageUrl() {
        if (voiture != null) {
            return voiture.getImageUrl();
        }
        return null;
    }

    // Vous pouvez ajouter d'autres getters ici pour déléguer les appels
    // à 'reservation' et 'voiture' pour simplifier le code de l'adapter.
    public Reservation getReservation() {
        return reservation;
    }

    public Voiture getVoiture() {
        return voiture;
    }
}
