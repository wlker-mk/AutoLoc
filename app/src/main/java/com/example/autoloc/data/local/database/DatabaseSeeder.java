package com.example.autoloc.data.local.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.autoloc.R;
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

    private static final String TAG = "DatabaseSeeder";
    private static final String PREFS_NAME = "DatabasePrefs";
    private static final String KEY_DATABASE_INITIALIZED = "db_initialized";
    private static final String KEY_DB_VERSION = "db_version";
    private static final int CURRENT_DB_VERSION = 3; //  CHANGEZ CE NOMBRE POUR FORCER LA RÉINITIALISATION

    private final AppDatabase database;
    private final ExecutorService executorService;
    private final SharedPreferences preferences;
    private final Context context;

    public DatabaseSeeder(Context context) {
        this.context = context;
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
            try {
                int savedVersion = preferences.getInt(KEY_DB_VERSION, 0);
                boolean isInitialized = preferences.getBoolean(KEY_DATABASE_INITIALIZED, false);

                Log.d(TAG, "=====================================");
                Log.d(TAG, " VÉRIFICATION BASE DE DONNÉES");
                Log.d(TAG, "Version enregistrée : " + savedVersion);
                Log.d(TAG, "Version actuelle : " + CURRENT_DB_VERSION);
                Log.d(TAG, "Déjà initialisée : " + isInitialized);
                Log.d(TAG, "=====================================");

                // Si pas initialisée OU version différente → réinitialiser
                if (!isInitialized || savedVersion != CURRENT_DB_VERSION) {
                    Log.w(TAG, " RÉINITIALISATION NÉCESSAIRE !");

                    Log.d(TAG, " Nettoyage de toutes les tables...");
                    database.clearAllTables();
                    Log.d(TAG, "✓ Tables nettoyées");

                    Log.d(TAG, " Insertion des utilisateurs...");
                    insertUtilisateurs();

                    Log.d(TAG, " Insertion des voitures...");
                    insertVoitures();

                    // Marquer comme initialisée
                    preferences.edit()
                            .putBoolean(KEY_DATABASE_INITIALIZED, true)
                            .putInt(KEY_DB_VERSION, CURRENT_DB_VERSION)
                            .apply();

                    Log.d(TAG, "=====================================");
                    Log.d(TAG, " BASE DE DONNÉES INITIALISÉE !");
                    Log.d(TAG, "=====================================");
                } else {
                    Log.d(TAG, "✓ Base de données déjà à jour (version " + CURRENT_DB_VERSION + ")");
                }

            } catch (Exception e) {
                Log.e(TAG, " ERREUR CRITIQUE lors de l'initialisation", e);
                Log.e(TAG, "Message d'erreur : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Réinitialise complètement la base de données.
     * Utile pour le développement ou les tests.
     */
    public void resetDatabase() {
        executorService.execute(() -> {
            try {
                Log.d(TAG, "=====================================");
                Log.d(TAG, " RESET FORCÉ DE LA BASE DE DONNÉES");
                Log.d(TAG, "=====================================");

                // Supprimer toutes les données
                database.clearAllTables();
                Log.d(TAG, "✓ Toutes les tables vidées");

                // Réinsérer les données
                insertUtilisateurs();
                insertVoitures();

                // Marquer comme réinitialisée
                preferences.edit()
                        .putBoolean(KEY_DATABASE_INITIALIZED, true)
                        .putInt(KEY_DB_VERSION, CURRENT_DB_VERSION)
                        .apply();

                Log.d(TAG, "=====================================");
                Log.d(TAG, " BASE RÉINITIALISÉE AVEC SUCCÈS !");
                Log.d(TAG, "=====================================");

            } catch (Exception e) {
                Log.e(TAG, " ERREUR lors du reset", e);
            }
        });
    }

    private void insertUtilisateurs() {
        try {
            Log.d(TAG, "  → Création utilisateur 1 : donne usiruto");
            Utilisateur demo = new Utilisateur(
                    "usiruto",
                    "donne",
                    "usiruti@gmail.com",
                    "demo123",
                    LocalDate.now()
            );
            long id1 = database.utilisateurDao().insert(demo);
            Log.d(TAG, "    ✓ Inséré avec ID : " + id1);

            Log.d(TAG, "  → Création utilisateur 2 : Sophie Martin");
            Utilisateur user1 = new Utilisateur(
                    "Martin",
                    "Sophie",
                    "sophie.martin@email.com",
                    "password123",
                    LocalDate.now().minusDays(30)
            );
            long id2 = database.utilisateurDao().insert(user1);
            Log.d(TAG, "    ✓ Inséré avec ID : " + id2);

            Log.d(TAG, "  → Création utilisateur 3 : Lucas Bernard");
            Utilisateur user2 = new Utilisateur(
                    "Bernard",
                    "Lucas",
                    "lucas.bernard@email.com",
                    "password123",
                    LocalDate.now().minusDays(15)
            );
            long id3 = database.utilisateurDao().insert(user2);
            Log.d(TAG, "    ✓ Inséré avec ID : " + id3);

            Log.d(TAG, "   3 utilisateurs insérés avec succès");

        } catch (Exception e) {
            Log.e(TAG, "   ERREUR lors de l'insertion des utilisateurs", e);
            throw e;
        }
    }

    private void insertVoitures() {
        try {
            Voiture[] voitures = {
                    new Voiture(
                            "BMW", "X5", 2023, "TG-1234-AB", 75000, true, "SUV",
                            getDrawableUri(R.drawable.bmw), 5, "Diesel"
                    ),
                    new Voiture(
                            "Mercedes-Benz", "Classe C", 2024, "TG-5678-CD", 65000, true, "Berline",
                            getDrawableUri(R.drawable.mercedesclassec), 5, "Essence"
                    ),
                    new Voiture(
                            "Audi", "A4", 2023, "TG-9012-EF", 60000, true, "Berline",
                            getDrawableUri(R.drawable.audi), 5, "Diesel"
                    ),
                    new Voiture(
                            "Porsche", "911 Carrera", 2024, "TG-3456-GH", 150000, true, "Sportive",
                            getDrawableUri(R.drawable.porche), 2, "Essence"
                    ),
                    new Voiture(
                            "Toyota", "RAV4", 2023, "TG-7890-IJ", 45000, true, "SUV",
                            getDrawableUri(R.drawable.toyota), 5, "Hybride"
                    ),
                    new Voiture(
                            "Land Rover", "Range Rover Sport", 2024, "TG-1357-KL", 95000, true, "4x4",
                            getDrawableUri(R.drawable.range), 5, "Diesel"
                    ),
                    new Voiture(
                            "Tesla", "Model 3", 2024, "TG-2468-MN", 85000, true, "Berline",
                            getDrawableUri(R.drawable.tesla), 5, "Électrique"
                    ),
                    new Voiture(
                            "Jeep", "Wrangler", 2023, "TG-3579-OP", 70000, true, "4x4",
                            getDrawableUri(R.drawable.jeep), 4, "Essence"
                    )
            };

            int count = 0;
            for (Voiture voiture : voitures) {
                count++;
                Log.d(TAG, "  → Voiture " + count + "/8 : " + voiture.getMarque() + " " + voiture.getModele());
                database.voitureDao().inserer(voiture);
                Log.d(TAG, "    ✓ Insérée (Prix: " + voiture.getPrixParJour() + " FCFA)");
            }

            Log.d(TAG, "   " + voitures.length + " voitures insérées avec succès");

        } catch (Exception e) {
            Log.e(TAG, "   ERREUR lors de l'insertion des voitures", e);
            Log.e(TAG, "  Détails : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Convertit un ID de ressource drawable en URI utilisable par Glide
     */
    private String getDrawableUri(int drawableId) {
        String uri = "android.resource://" + context.getPackageName() + "/" + drawableId;
        return uri;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}