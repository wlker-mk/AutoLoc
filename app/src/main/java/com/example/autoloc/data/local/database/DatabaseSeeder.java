package com.example.autoloc.data.local.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.data.local.entity.Voiture;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gestionnaire d'initialisation de la base de données.
 * Insère des données de démonstration uniquement au premier lancement.
 */
public class DatabaseSeeder {

    private static final String PREFS_NAME = "DatabasePrefs";
    private static final String KEY_DATABASE_INITIALIZED = "db_initialized";

    private final AppDatabase database;
    private final ExecutorService executorService;
    private final SharedPreferences preferences;

    public DatabaseSeeder(Context context) {
        this.database = AppDatabase.getInstance(context);
        this.executorService = Executors.newSingleThreadExecutor();
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Initialise la base de données avec des données de test.
     * Cette méthode vérifie d'abord si l'initialisation a déjà été effectuée.
     */
    public void seedDatabase() {
        executorService.execute(() -> {
            // Vérifier si la base a déjà été initialisée
            if (!preferences.getBoolean(KEY_DATABASE_INITIALIZED, false)) {
                insertUtilisateurs();
                insertVoitures();

                // Marquer la base comme initialisée
                preferences.edit()
                        .putBoolean(KEY_DATABASE_INITIALIZED, true)
                        .apply();
            }
        });
    }

    /**
     * Réinitialise complètement la base de données.
     * Utile pour le développement ou les tests.
     */
    public void resetDatabase() {
        executorService.execute(() -> {
            // Supprimer toutes les données
            database.clearAllTables();

            // Réinsérer les données
            insertUtilisateurs();
            insertVoitures();

            // Marquer comme réinitialisée
            preferences.edit()
                    .putBoolean(KEY_DATABASE_INITIALIZED, true)
                    .apply();
        });
    }

    private void insertUtilisateurs() {
        // Compte de démonstration
        Utilisateur demo = new Utilisateur(
                "Dupont",
                "Alexandre",
                "demo@autoloc.com",
                "demo123",
                LocalDate.now()
        );

        Utilisateur user1 = new Utilisateur(
                "Martin",
                "Sophie",
                "sophie.martin@email.com",
                "password123",
                LocalDate.now().minusDays(30)
        );

        Utilisateur user2 = new Utilisateur(
                "Bernard",
                "Lucas",
                "lucas.bernard@email.com",
                "password123",
                LocalDate.now().minusDays(15)
        );

        database.utilisateurDao().insert(demo);
        database.utilisateurDao().insert(user1);
        database.utilisateurDao().insert(user2);
    }

    private void insertVoitures() {
        // Collection de voitures variées pour la démonstration
        Voiture[] voitures = {
                new Voiture(
                        "BMW",
                        "X5",
                        2023,
                        "TG-1234-AB",
                        75000,
                        true,
                        "SUV",
                        "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800",
                        5,
                        "Diesel"
                ),
                new Voiture(
                        "Mercedes-Benz",
                        "Classe C",
                        2024,
                        "TG-5678-CD",
                        65000,
                        true,
                        "Berline",
                        "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800",
                        5,
                        "Essence"
                ),
                new Voiture(
                        "Audi",
                        "A4",
                        2023,
                        "TG-9012-EF",
                        60000,
                        true,
                        "Berline",
                        "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800",
                        5,
                        "Diesel"
                ),
                new Voiture(
                        "Porsche",
                        "911 Carrera",
                        2024,
                        "TG-3456-GH",
                        150000,
                        true,
                        "Sportive",
                        "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800",
                        2,
                        "Essence"
                ),
                new Voiture(
                        "Toyota",
                        "RAV4",
                        2023,
                        "TG-7890-IJ",
                        45000,
                        true,
                        "SUV",
                        "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=800",
                        5,
                        "Hybride"
                ),
                new Voiture(
                        "Land Rover",
                        "Range Rover Sport",
                        2024,
                        "TG-1357-KL",
                        95000,
                        true,
                        "4x4",
                        "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=800",
                        5,
                        "Diesel"
                ),
                new Voiture(
                        "Tesla",
                        "Model 3",
                        2024,
                        "TG-2468-MN",
                        85000,
                        true,
                        "Berline",
                        "https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=800",
                        5,
                        "Électrique"
                ),
                new Voiture(
                        "Jeep",
                        "Wrangler",
                        2023,
                        "TG-3579-OP",
                        70000,
                        true,
                        "4x4",
                        "https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800",
                        4,
                        "Essence"
                )
        };

        // Insertion de toutes les voitures
        for (Voiture voiture : voitures) {
            database.voitureDao().inserer(voiture);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}