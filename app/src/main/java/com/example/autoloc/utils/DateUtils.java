package com.example.autoloc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Classe utilitaire pour la gestion des dates
 */
public class DateUtils {

    /**
     * Formater une date pour l'affichage (ex: 25/12/2024)
     */
    public static String formatDateForDisplay(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DISPLAY, Locale.FRANCE);
        return sdf.format(date);
    }

    /**
     * Formater une date pour la base de données (ex: 2024-12-25)
     */
    public static String formatDateForDatabase(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DATABASE, Locale.FRANCE);
        return sdf.format(date);
    }

    /**
     * Convertir une String en Date
     */
    public static Date parseDate(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculer le nombre de jours entre deux dates
     */
    public static int calculateDaysBetween(Date dateDebut, Date dateFin) {
        if (dateDebut == null || dateFin == null) return 0;

        long difference = dateFin.getTime() - dateDebut.getTime();
        return (int) TimeUnit.MILLISECONDS.toDays(difference);
    }

    /**
     * Vérifier si la date de début est avant la date de fin
     */
    public static boolean isDateDebutBeforeDateFin(Date dateDebut, Date dateFin) {
        if (dateDebut == null || dateFin == null) return false;
        return dateDebut.before(dateFin);
    }

    /**
     * Vérifier si une date est dans le futur
     */
    public static boolean isDateInFuture(Date date) {
        if (date == null) return false;
        return date.after(new Date());
    }

    /**
     * Vérifier si une date est dans le passé
     */
    public static boolean isDateInPast(Date date) {
        if (date == null) return false;
        return date.before(new Date());
    }

    /**
     * Obtenir la date d'aujourd'hui
     */
    public static Date getTodayDate() {
        return new Date();
    }

    /**
     * Obtenir la date actuelle formatée pour l'affichage
     */
    public static String getTodayFormatted() {
        return formatDateForDisplay(new Date());
    }

    // Constructeur privé
    private DateUtils() {
        throw new AssertionError("Cannot instantiate DateUtils class");
    }
}