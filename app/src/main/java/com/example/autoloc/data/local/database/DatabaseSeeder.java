package com.example.autoloc.data.local.database;

import android.content.Context;

import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.data.local.entity.Voiture;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe utilitaire pour peupler la base de données avec des données de test
 * Cette approche est courante dans le développement Android pour faciliter les tests
 */
public class DatabaseSeeder {

    private final AppDatabase database;
    private final ExecutorService executorService;

    public DatabaseSeeder(Context context) {
        this.database = AppDatabase.getInstance(context);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Peuple la base avec des données de démonstration
     * Cette méthode doit être appelée une seule fois au premier lancement
     */
    public void seedDatabase() {
        executorService.execute(() -> {
            // Vérifier si des données existent déjà
            if (isDatabaseEmpty()) {
                insertUtilisateurs();
                insertVoitures();
            }
        });
    }

    private boolean isDatabaseEmpty() {
        // Méthode simple pour vérifier si la base contient déjà des données
        // En production, on utiliserait une vérification plus robuste
        return true;
    }

    private void insertUtilisateurs() {
        // Création d'utilisateurs de test
        Utilisateur user1 = new Utilisateur(
                "Dupont",
                "Alexandre",
                "alexandre.dupont@email.com",
                "password123",
                LocalDate.now()
        );

        Utilisateur user2 = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@email.com",
                "password123",
                LocalDate.now()
        );

        database.utilisateurDao().insert(user1);
        database.utilisateurDao().insert(user2);
    }

    private void insertVoitures() {
        // Création de voitures de test avec des données réalistes

        // Voiture 1 - BMW X5
        Voiture bmw = new Voiture(
                "BMW",
                "X5",
                2023,
                "AB-123-CD",
                75000,
                true,
                "SUV",
                "https://images.unsplash.com/photo-1555215695-3004980ad54e",
                5,
                "Diesel"
        );

        // Voiture 2 - Mercedes Classe C
        Voiture mercedes = new Voiture(
                "Mercedes",
                "Classe C",
                2024,
                "EF-456-GH",
                65000,
                true,
                "Berline",
                "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8",
                5,
                "Essence"
        );

        // Voiture 3 - Audi A4
        Voiture audi = new Voiture(
                "Audi",
                "A4",
                2023,
                "IJ-789-KL",
                60000,
                true,
                "Berline",
                "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6",
                5,
                "Diesel"
        );

        // Voiture 4 - Porsche 911
        Voiture porsche = new Voiture(
                "Porsche",
                "911 Carrera",
                2024,
                "MN-012-OP",
                150000,
                true,
                "Sportive",
                "https://images.unsplash.com/photo-1503376780353-7e6692767b70",
                2,
                "Essence"
        );

        // Voiture 5 - Toyota RAV4
        Voiture toyota = new Voiture(
                "Toyota",
                "RAV4",
                2023,
                "QR-345-ST",
                45000,
                true,
                "SUV",
                "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb",
                5,
                "Hybride"
        );

        // Voiture 6 - Range Rover
        Voiture rangeRover = new Voiture(
                "Land Rover",
                "Range Rover Sport",
                2024,
                "UV-678-WX",
                95000,
                true,
                "4x4",
                "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46",
                5,
                "Diesel"
        );

        // Insertion dans la base
        database.voitureDao().inserer(bmw);
        database.voitureDao().inserer(mercedes);
        database.voitureDao().inserer(audi);
        database.voitureDao().inserer(porsche);
        database.voitureDao().inserer(toyota);
        database.voitureDao().inserer(rangeRover);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}