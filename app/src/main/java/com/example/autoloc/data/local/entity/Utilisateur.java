package com.example.autoloc.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.time.LocalDate;

@Entity(tableName = "utilisateurs")
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "mot_de_passe")
    private String motDePasse;

    @ColumnInfo(name = "date_inscription")
    private LocalDate dateInscription;

    // Constructeur
    public Utilisateur(String nom, String prenom, String email, String motDePasse, LocalDate dateInscription) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateInscription = dateInscription;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    /* ======================================================
       Méthodes demandées (logique déléguée en MVVM)
       ====================================================== */

    public void sInscrire() {
        // La logique réelle sera dans UtilisateurRepository
    }

    public void seConnecter() {
        // Gérée par AuthViewModel + Repository
    }

    public void consulterProfil() {
        // Affichage via ProfilFragment
    }

    public void modifierProfil() {
        // Mise à jour via Repository
    }

    public void seDeconnecter() {
        // Géré par SessionManager
    }
}
