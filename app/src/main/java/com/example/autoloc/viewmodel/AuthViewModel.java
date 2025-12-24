package com.example.autoloc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.data.repository.UtilisateurRepository;
import com.example.autoloc.utils.SessionManager;

public class AuthViewModel extends AndroidViewModel {

    private final UtilisateurRepository utilisateurRepository;
    private final MutableLiveData<Utilisateur> utilisateurLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        utilisateurRepository = new UtilisateurRepository(application);
    }

    // 🔐Connexion
    public void seConnecter(String email, String motDePasse) {
        new Thread(() -> {
            Utilisateur utilisateur = utilisateurRepository.connecter(email, motDePasse);
            if (utilisateur != null) {
                SessionManager.getInstance(getApplication()).login(utilisateur);
                utilisateurLiveData.postValue(utilisateur);
            } else {
                erreurLiveData.postValue("Email ou mot de passe incorrect");
            }
        }).start();
    }

    // Inscription
    public void sInscrire(String nom, String prenom, String email, String motDePasse) {
        utilisateurRepository.inscrire(nom, prenom, email, motDePasse);
    }

    // LiveData
    public LiveData<Utilisateur> getUtilisateur() {
        return utilisateurLiveData;
    }

    public LiveData<String> getErreur() {
        return erreurLiveData;
    }

    // Déconnexion
    public void seDeconnecter() {
        SessionManager.getInstance(getApplication()).logout();
        utilisateurLiveData.setValue(null);
    }
}
