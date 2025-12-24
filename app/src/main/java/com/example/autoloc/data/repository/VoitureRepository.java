package com.example.autoloc.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.autoloc.data.local.dao.VoitureDao;
import com.example.autoloc.data.local.database.AppDatabase;
import com.example.autoloc.data.local.entity.Voiture;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VoitureRepository {

    private final VoitureDao voitureDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public VoitureRepository(Context context) {
        voitureDao = AppDatabase.getInstance(context).voitureDao();
    }

    public LiveData<List<Voiture>> getVoituresDisponibles() {
        return voitureDao.getVoituresDisponibles();
    }

    public LiveData<List<Voiture>> getToutesLesVoitures() {
        return voitureDao.getToutesLesVoitures();
    }

    public LiveData<Voiture> getVoitureParId(int id) {
        return voitureDao.getVoitureParId(id);
    }

    public void inserer(Voiture voiture) {
        executor.execute(() -> voitureDao.inserer(voiture));
    }

    public void modifier(Voiture voiture) {
        executor.execute(() -> voitureDao.modifier(voiture));
    }

    public void changerDisponibilite(int idVoiture, boolean disponible) {
        executor.execute(() ->
                voitureDao.changerDisponibilite(idVoiture, disponible)
        );
    }
}
