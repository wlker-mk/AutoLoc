package com.example.autoloc.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.autoloc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Variable d'instance qui sera utilisée par onSupportNavigateUp
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Récupérez le NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            // CORRECTION : Assignez la valeur à la variable d'instance "navController"
            // au lieu de déclarer une nouvelle variable locale.
            this.navController = navHostFragment.getNavController();

            // Fragments de niveau racine (onglets)
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.voitureListFragment,
                    R.id.historiqueFragment
            ).build();

            // Liaison ActionBar + Navigation
            NavigationUI.setupActionBarWithNavController(this, this.navController, appBarConfiguration);

            // Liaison BottomNavigation + Navigation
            NavigationUI.setupWithNavController(bottomNavigationView, this.navController);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Maintenant, "navController" ne sera pas null et le bouton retour fonctionnera
        return navController != null && (navController.navigateUp() || super.onSupportNavigateUp());
    }
}
