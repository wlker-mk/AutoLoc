package com.example.autoloc.data.local.entity;

import androidx.room.Entity;
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

    // 🔹 Constructeur

    public Voiture() {}
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

    public int getId() {
        return idVoiture;
    }

    public void setId(int idVoiture) {
        this.idVoiture = idVoiture;
    }

    public int getIdVoiture() { return idVoiture; }
    public void setIdVoiture(int idVoiture) { this.idVoiture = idVoiture; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getModele() { return modele; }
    public int getAnnee() { return annee; }
    public String getImmatriculation() { return immatriculation; }
    public double getPrixParJour() { return prixParJour; }
    public boolean isDisponible() { return disponible; }
    public String getCategorie() { return categorie; }
    public String getImageUrl() { return imageUrl; }
    public int getNombrePlaces() { return nombrePlaces; }
    public String getTypeCarburant() { return typeCarburant; }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
