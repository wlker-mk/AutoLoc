package com.example.autoloc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.data.repository.UtilisateurRepository;
import com.example.autoloc.utils.SessionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {

    private final UtilisateurRepository utilisateurRepository;
    private final ExecutorService executorService;

    private final MutableLiveData<Utilisateur> utilisateurLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> erreurLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> inscriptionReussieLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        utilisateurRepository = new UtilisateurRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    // 🔐 Connexion
    public void seConnecter(String email, String motDePasse) {
        loadingLiveData.postValue(true);

        executorService.execute(() -> {
            try {
                Utilisateur utilisateur = utilisateurRepository.connecter(email, motDePasse);

                if (utilisateur != null) {
                    SessionManager.getInstance(getApplication()).login(utilisateur);
                    utilisateurLiveData.postValue(utilisateur);
                    erreurLiveData.postValue(null);
                } else {
                    erreurLiveData.postValue("Email ou mot de passe incorrect");
                    utilisateurLiveData.postValue(null);
                }
            } catch (Exception e) {
                erreurLiveData.postValue("Erreur lors de la connexion: " + e.getMessage());
            } finally {
                loadingLiveData.postValue(false);
            }
        });
    }

    // ✍️ Inscription
    public void sInscrire(String nom, String prenom, String email, String motDePasse) {
        loadingLiveData.postValue(true);

        executorService.execute(() -> {
            try {
                // Vérifier si l'email existe déjà
                Utilisateur utilisateurExistant = utilisateurRepository.getUtilisateurParEmail(email);

                if (utilisateurExistant != null) {
                    erreurLiveData.postValue("Cet email est déjà utilisé");
                    inscriptionReussieLiveData.postValue(false);
                } else {
                    // Créer le nouvel utilisateur
                    utilisateurRepository.inscrire(nom, prenom, email, motDePasse);
                    inscriptionReussieLiveData.postValue(true);
                    erreurLiveData.postValue(null);
                }
            } catch (Exception e) {
                erreurLiveData.postValue("Erreur lors de l'inscription: " + e.getMessage());
                inscriptionReussieLiveData.postValue(false);
            } finally {
                loadingLiveData.postValue(false);
            }
        });
    }

    // 🚪 Déconnexion
    public void seDeconnecter() {
        SessionManager.getInstance(getApplication()).logout();
        utilisateurLiveData.setValue(null);
    }

    // 📡 LiveData getters
    public LiveData<Utilisateur> getUtilisateur() {
        return utilisateurLiveData;
    }

    public LiveData<String> getErreur() {
        return erreurLiveData;
    }

    public LiveData<Boolean> getInscriptionReussie() {
        return inscriptionReussieLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loadingLiveData;
    }

    // 🧹 Cleanup
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}