package com.example.autoloc.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "voitures")
public class Voiture {

    @PrimaryKey(autoGenerate = true)
    private int idVoiture;

    private String marque;
    private String modele;
    private int annee;
    private String immatriculation;
    private double prixParJour;
    private boolean disponible;
    private String categorie;
    private String imageUrl;
    private int nombrePlaces;
    private String typeCarburant;

    // 🔹 Constructeurs

    // Constructeur sans arguments utilisé par Room
    public Voiture() {}

    // Constructeur pour un usage manuel, ignoré par Room
    @Ignore
    public Voiture(String marque, String modele, int annee,
                   String immatriculation, double prixParJour,
                   boolean disponible, String categorie,
                   String imageUrl, int nombrePlaces,
                   String typeCarburant) {
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.immatriculation = immatriculation;
        this.prixParJour = prixParJour;
        this.disponible = disponible;
        this.categorie = categorie;
        this.imageUrl = imageUrl;
        this.nombrePlaces = nombrePlaces;
        this.typeCarburant = typeCarburant;
    }

    // 🔹 Méthodes métier
    public void afficherDetails() {
        System.out.println(
                marque + " " + modele + " (" + annee + ") - "
                        + prixParJour + " / jour"
        );
    }

    public boolean estDisponible() {
        return disponible;
    }

    public double calculerPrixTotal(int nbJours) {
        return prixParJour * nbJours;
    }

    // 🔹 Getters & Setters

    public int getIdVoiture() { return idVoiture; }
    public void setIdVoiture(int idVoiture) { this.idVoiture = idVoiture; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public double getPrixParJour() { return prixParJour; }
    public void setPrixParJour(double prixParJour) { this.prixParJour = prixParJour; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getNombrePlaces() { return nombrePlaces; }
    public void setNombrePlaces(int nombrePlaces) { this.nombrePlaces = nombrePlaces; }

    public String getTypeCarburant() { return typeCarburant; }
    public void setTypeCarburant(String typeCarburant) { this.typeCarburant = typeCarburant; }
}
