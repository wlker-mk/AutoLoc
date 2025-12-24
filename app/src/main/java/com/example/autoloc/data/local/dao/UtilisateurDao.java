package com.example.autoloc.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.autoloc.data.local.entity.Utilisateur;

@Dao
public interface UtilisateurDao {

    @Insert
    long insert(Utilisateur utilisateur);

    @Update
    void update(Utilisateur utilisateur);

    @Query("SELECT * FROM utilisateurs WHERE email = :email AND mot_de_passe = :motDePasse LIMIT 1")
    Utilisateur login(String email, String motDePasse);

    @Query("SELECT * FROM utilisateurs WHERE id = :id LIMIT 1")
    Utilisateur getById(int id);

    @Query("SELECT * FROM utilisateurs WHERE email = :email LIMIT 1")
    Utilisateur getByEmail(String email);
}
