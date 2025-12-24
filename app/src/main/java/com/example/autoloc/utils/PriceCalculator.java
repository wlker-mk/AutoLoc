package com.example.autoloc.utils;

import java.util.Date;

/**
 * Classe utilitaire pour le calcul des prix
 */
public class PriceCalculator {

    /**
     * Calculer le prix total d'une location
     * @param prixParJour Prix journalier de la voiture
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Prix total
     */
    public static double calculateTotalPrice(double prixParJour, Date dateDebut, Date dateFin) {
        // Calculer le nombre de jours
        int nombreJours = DateUtils.calculateDaysBetween(dateDebut, dateFin);

        // Si moins d'un jour, facturer quand même 1 jour
        if (nombreJours < 1) {
            nombreJours = 1;
        }

        // Calculer le prix total
        return prixParJour * nombreJours;
    }

    /**
     * Calculer le prix total avec le nombre de jours
     * @param prixParJour Prix journalier
     * @param nombreJours Nombre de jours
     * @return Prix total
     */
    public static double calculateTotalPrice(double prixParJour, int nombreJours) {
        if (nombreJours < 1) {
            nombreJours = 1;
        }
        return prixParJour * nombreJours;
    }

    /**
     * Formater un prix pour l'affichage (avec séparateur de milliers)
     * @param prix Prix à formater
     * @return Prix formaté (ex: "25 000 FCFA")
     */
    public static String formatPrice(double prix) {
        return String.format("%,.0f FCFA", prix).replace(",", " ");
    }

    /**
     * Formater un prix court (sans FCFA)
     * @param prix Prix à formater
     * @return Prix formaté (ex: "25 000")
     */
    public static String formatPriceShort(double prix) {
        return String.format("%,.0f", prix).replace(",", " ");
    }

    // Constructeur privé
    private PriceCalculator() {
        throw new AssertionError("Cannot instantiate PriceCalculator class");
    }
}