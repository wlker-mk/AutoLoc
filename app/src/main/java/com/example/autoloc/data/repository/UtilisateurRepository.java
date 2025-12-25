package com.example.autoloc.data.repository;

import android.content.Context;

import com.example.autoloc.application.AutoLocApplication;
import com.example.autoloc.data.local.dao.UtilisateurDao;
import com.example.autoloc.data.local.database.AppDatabase;
import com.example.autoloc.data.local.entity.Utilisateur;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UtilisateurRepository {

    private final UtilisateurDao utilisateurDao;
    private final ExecutorService executorService;

    public UtilisateurRepository(Context context) {
        AppDatabase db = AutoLocApplication.getInstance().getDatabase();
        utilisateurDao = db.utilisateurDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Inscription
    public void inscrire(String nom, String prenom, String email, String motDePasse) {
        executorService.execute(() -> {
            Utilisateur utilisateur = new Utilisateur(
                    nom,
                    prenom,
                    email,
                    motDePasse,
                    LocalDate.now()
            );
            utilisateurDao.insert(utilisateur);
        });
    }

    // Connexion
    public Utilisateur connecter(String email, String motDePasse) {
        return utilisateurDao.login(email, motDePasse);
    }

    //  Profil
    public Utilisateur getUtilisateur(int id) {
        return utilisateurDao.getById(id);
    }
    public Utilisateur getUtilisateurParEmail(String email) {
        return utilisateurDao.getByEmail(email);
    }

    public void modifierProfil(Utilisateur utilisateur) {
        executorService.execute(() -> utilisateurDao.update(utilisateur));
    }
}
