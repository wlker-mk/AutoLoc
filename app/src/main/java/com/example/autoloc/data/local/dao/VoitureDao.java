package com.example.autoloc.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.autoloc.data.local.entity.Voiture;

import java.util.List;

@Dao
public interface VoitureDao {

    @Insert
    void inserer(Voiture voiture);

    @Update
    void modifier(Voiture voiture);

    @Query("SELECT * FROM voitures")
    LiveData<List<Voiture>> getToutesLesVoitures();

    @Query("SELECT * FROM voitures WHERE disponible = 1")
    LiveData<List<Voiture>> getVoituresDisponibles();

    @Query("SELECT * FROM voitures WHERE idVoiture = :id")
    LiveData<Voiture> getVoitureParId(int id);

    @Query("UPDATE voitures SET disponible = :etat WHERE idVoiture = :id")
    void changerDisponibilite(int id, boolean etat);
}
