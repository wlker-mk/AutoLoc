package com.example.autoloc.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.autoloc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // NavController lié au NavHostFragment
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Fragments de niveau racine (onglets)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.voitureListFragment,
                R.id.historiqueFragment
        ).build();

        // Liaison ActionBar + Navigation
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Liaison BottomNavigation + Navigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
