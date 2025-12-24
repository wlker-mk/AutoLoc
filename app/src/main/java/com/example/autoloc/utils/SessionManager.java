package com.example.autoloc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.autoloc.data.local.entity.Utilisateur; // Importez l'entité Utilisateur/**

public class SessionManager {

    // 1. Variable statique pour l'instance unique
    private static volatile SessionManager instance;

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    /**
     * 2. Constructeur privé pour empêcher l'instanciation directe
     */
    private SessionManager(Context context) {
        // Utiliser getApplicationContext() pour éviter les fuites de mémoire
        prefs = context.getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**
     * 3. Méthode publique statique pour obtenir l'instance (thread-safe)
     */
    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * Sauvegarder la session utilisateur (après connexion).
     * Modifiée pour prendre un objet Utilisateur, comme utilisé dans AuthViewModel.
     */
    public void login(Utilisateur utilisateur) {
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, true);
        editor.putInt(Constants.KEY_USER_ID, utilisateur.getId()); // Assurez-vous que la méthode getId() existe dans votre classe Utilisateur
        editor.putString(Constants.KEY_USER_EMAIL, utilisateur.getEmail());
        editor.putString(Constants.KEY_USER_NOM, utilisateur.getNom());
        editor.putString(Constants.KEY_USER_PRENOM, utilisateur.getPrenom());
        editor.apply(); // Utiliser apply() pour une écriture asynchrone en arrière-plan
    }

    /**
     * Déconnecter l'utilisateur (supprimer la session)
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }

    /**
     * Vérifier si l'utilisateur est connecté
     */
    public boolean isLoggedIn() {
        return prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }

    // ... gardez vos autres méthodes (getUserId, getUserEmail, etc.) ici ...
    public int getUserId() {
        return prefs.getInt(Constants.KEY_USER_ID, -1);
    }

    public String getUserEmail() {
        return prefs.getString(Constants.KEY_USER_EMAIL, "");
    }

    public String getUserNom() {
        return prefs.getString(Constants.KEY_USER_NOM, "");
    }

    public String getUserPrenom() {
        return prefs.getString(Constants.KEY_USER_PRENOM, "");
    }

    public String getUserFullName() {
        String prenom = getUserPrenom();
        String nom = getUserNom();
        return prenom + " " + nom;
    }
}
