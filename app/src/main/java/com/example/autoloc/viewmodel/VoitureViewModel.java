package com.example.autoloc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.autoloc.data.local.entity.Voiture;
import com.example.autoloc.data.repository.VoitureRepository;

import java.util.List;

public class VoitureViewModel extends AndroidViewModel {

    private final VoitureRepository voitureRepository;
    private final LiveData<List<Voiture>> voituresDisponibles;
    private final LiveData<List<Voiture>> toutesLesVoitures;

    public VoitureViewModel(@NonNull Application application) {
        super(application);
        voitureRepository = new VoitureRepository(application);

        voituresDisponibles = voitureRepository.getVoituresDisponibles();
        toutesLesVoitures = voitureRepository.getToutesLesVoitures();
    }

    // 🔹 Exposition vers l'UI
    public LiveData<List<Voiture>> getVoituresDisponibles() {
        return voituresDisponibles;
    }

    public LiveData<List<Voiture>> getToutesLesVoitures() {
        return toutesLesVoitures;
    }

    public LiveData<Voiture> getVoitureParId(int id) {
        return voitureRepository.getVoitureParId(id);
    }

    // 🔹 Actions
    public void inserer(Voiture voiture) {
        voitureRepository.inserer(voiture);
    }

    public void modifier(Voiture voiture) {
        voitureRepository.modifier(voiture);
    }

    public void changerDisponibilite(int idVoiture, boolean disponible) {
        voitureRepository.changerDisponibilite(idVoiture, disponible);
    }
}
