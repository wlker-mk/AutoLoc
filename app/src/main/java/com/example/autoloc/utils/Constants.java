package com.example.autoloc.utils;

/**
 * Classe contenant toutes les constantes de l'application
 */
public class Constants {

    // ========== BASE DE DONNÉES ==========
    public static final String DATABASE_NAME = "autoloc_db";
    public static final int DATABASE_VERSION = 1;

    // Noms des tables
    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String TABLE_VOITURE = "voiture";
    public static final String TABLE_RESERVATION = "reservation";


    // ========== STATUTS RESERVATION ==========
    public static final String STATUT_EN_COURS = "EN_COURS";
    public static final String STATUT_TERMINEE = "TERMINEE";
    public static final String STATUT_ANNULEE = "ANNULEE";


    // ========== SHARED PREFERENCES ==========
    public static final String PREF_NAME = "AutoLocPrefs";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_NOM = "user_nom";
    public static final String KEY_USER_PRENOM = "user_prenom";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";


    // ========== CATÉGORIES DE VOITURES ==========
    public static final String CATEGORIE_BERLINE = "Berline";
    public static final String CATEGORIE_SUV = "SUV";
    public static final String CATEGORIE_CITADINE = "Citadine";
    public static final String CATEGORIE_SPORTIVE = "Sportive";


    // ========== TYPES DE CARBURANT ==========
    public static final String CARBURANT_ESSENCE = "Essence";
    public static final String CARBURANT_DIESEL = "Diesel";
    public static final String CARBURANT_HYBRIDE = "Hybride";
    public static final String CARBURANT_ELECTRIQUE = "Électrique";


    // ========== FORMATS DE DATE ==========
    public static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DATABASE = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";


    // ========== MESSAGES ==========
    public static final String MSG_CONNEXION_REUSSIE = "Connexion réussie !";
    public static final String MSG_INSCRIPTION_REUSSIE = "Inscription réussie !";
    public static final String MSG_EMAIL_EXISTE = "Cet email existe déjà";
    public static final String MSG_EMAIL_INVALIDE = "Email invalide";
    public static final String MSG_MDP_COURT = "Mot de passe trop court (min 6 caractères)";
    public static final String MSG_CHAMPS_OBLIGATOIRES = "Tous les champs sont obligatoires";
    public static final String MSG_RESERVATION_REUSSIE = "Réservation effectuée avec succès !";
    public static final String MSG_DATES_INVALIDES = "Les dates sont invalides";
    public static final String MSG_VOITURE_NON_DISPONIBLE = "Cette voiture n'est pas disponible pour ces dates";


    // ========== AUTRES ==========
    public static final int SPLASH_DELAY = 2000; // 2 secondes
    public static final int MIN_PASSWORD_LENGTH = 6;


    // Constructeur privé pour empêcher l'instanciation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}