package com.example.autoloc.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity(tableName = "reservations")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int idReservation;

    private int idUtilisateur;
    private int idVoiture;

    // Dates stockées en String (yyyy-MM-dd)
    private String dateDebut;
    private String dateFin;
    private double montantTotal;
    private String statut;
    private String dateReservation;

    // 🔹 Constructeur
    public Reservation(int idUtilisateur, int idVoiture,
                       String dateDebut, String dateFin,
                       String dateReservation) {

        this.idUtilisateur = idUtilisateur;
        this.idVoiture = idVoiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateReservation = dateReservation;
        this.statut = "EN_ATTENTE";
        this.montantTotal = 0;
    }

    // 🔹 Méthodes métier
    public int calculerDuree() {
        LocalDate debut = LocalDate.parse(dateDebut);
        LocalDate fin = LocalDate.parse(dateFin);
        return (int) ChronoUnit.DAYS.between(debut, fin);
    }

    public double calculerMontant(double prixParJour) {
        int duree = calculerDuree();
        montantTotal = duree * prixParJour;
        return montantTotal;
    }

    public void validerReservation() {
        statut = "VALIDEE";
    }

    public void annulerReservation() {
        statut = "ANNULEE";
    }

    public boolean estTerminee() {
        LocalDate fin = LocalDate.parse(dateFin);
        return LocalDate.now().isAfter(fin);
    }

    // 🔹 Getters & Setters
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public int getIdVoiture() { return idVoiture; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public double getMontantTotal() { return montantTotal; }
    public String getStatut() { return statut; }
    public String getDateReservation() { return dateReservation; }
}
