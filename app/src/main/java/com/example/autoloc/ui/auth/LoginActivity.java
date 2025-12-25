package com.example.autoloc.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.autoloc.R;
import com.example.autoloc.ui.main.MainActivity;
import com.example.autoloc.utils.Constants;
import com.example.autoloc.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvCreateAccount;
    private ProgressBar progressBar;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initViewModel();
        setupListeners();
        observeViewModel();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> login());

        tvCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void observeViewModel() {
        // Observer pour l'utilisateur connecté
        authViewModel.getUtilisateur().observe(this, utilisateur -> {
            if (utilisateur != null) {
                Toast.makeText(this, Constants.MSG_CONNEXION_REUSSIE, Toast.LENGTH_SHORT).show();

                // Naviguer vers MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Observer pour les erreurs
        authViewModel.getErreur().observe(this, erreur -> {
            if (erreur != null && !erreur.isEmpty()) {
                Toast.makeText(this, erreur, Toast.LENGTH_LONG).show();
            }
        });

        // Observer pour le loading
        authViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                btnLogin.setEnabled(!isLoading);
            }
        });
    }

    private void login() {
        // Récupérer les valeurs
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation
        if (!validateInputs(email, password)) {
            return;
        }

        // Lancer la connexion via ViewModel
        authViewModel.seConnecter(email, password);
    }

    private boolean validateInputs(String email, String password) {
        // Vérifier email vide
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("L'email est obligatoire");
            etEmail.requestFocus();
            return false;
        }

        // Valider le format de l'email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(Constants.MSG_EMAIL_INVALIDE);
            etEmail.requestFocus();
            return false;
        }

        // Vérifier mot de passe vide
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Le mot de passe est obligatoire");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}