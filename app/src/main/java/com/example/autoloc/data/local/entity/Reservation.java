package com.example.autoloc.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
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

    // 🔹 Constructeurs

    // Constructeur sans arguments utilisé par Room
    public Reservation() {}

    // Constructeur pour un usage manuel, ignoré par Room
    @Ignore
    public Reservation(int idUtilisateur, int idVoiture,
                       String dateDebut, String dateFin,
                       String dateReservation) {
        this.idUtilisateur = idUtilisateur;
        this.idVoiture = idVoiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateReservation = dateReservation;
        this.statut = "EN_ATTENTE"; // Statut initial
        this.montantTotal = 0; // Montant initial
    }

    // 🔹 Méthodes métier
    @Ignore // Il est bon d'ignorer les méthodes qui ne sont pas directement liées aux champs
    public int calculerDuree() {
        LocalDate debut = LocalDate.parse(dateDebut);
        LocalDate fin = LocalDate.parse(dateFin);
        return (int) ChronoUnit.DAYS.between(debut, fin);
    }

    @Ignore
    public double calculerMontant(double prixParJour) {
        int duree = calculerDuree();
        this.montantTotal = duree * prixParJour;
        return this.montantTotal;
    }

    public void validerReservation() {
        this.statut = "VALIDEE";
    }

    public void annulerReservation() {
        this.statut = "ANNULEE";
    }

    @Ignore
    public boolean estTerminee() {
        LocalDate fin = LocalDate.parse(dateFin);
        return LocalDate.now().isAfter(fin);
    }

    // 🔹 Getters & Setters
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(int idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public int getIdVoiture() { return idVoiture; }
    public void setIdVoiture(int idVoiture) { this.idVoiture = idVoiture; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getDateReservation() { return dateReservation; }
    public void setDateReservation(String dateReservation) { this.dateReservation = dateReservation; }
}
