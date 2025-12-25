package com.example.autoloc.ui.auth;

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
import com.example.autoloc.utils.Constants;
import com.example.autoloc.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etPrenom, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private ProgressBar progressBar;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initViewModel();
        setupListeners();
        observeViewModel();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etPrenom = findViewById(R.id.etPrenom);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> register());
        tvLogin.setOnClickListener(v -> finish());
    }

    private void observeViewModel() {
        // Observer pour l'inscription réussie
        authViewModel.getInscriptionReussie().observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, Constants.MSG_INSCRIPTION_REUSSIE, Toast.LENGTH_SHORT).show();
                finish(); // Retour à LoginActivity
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
                btnRegister.setEnabled(!isLoading);
            }
        });
    }

    private void register() {
        // Récupérer les valeurs
        String nom = etName.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validation
        if (!validateInputs(nom, prenom, email, password, confirmPassword)) {
            return;
        }

        // Lancer l'inscription via ViewModel
        authViewModel.sInscrire(nom, prenom, email, password);
    }

    private boolean validateInputs(String nom, String prenom, String email,
                                   String password, String confirmPassword) {
        // Vérifier les champs vides
        if (TextUtils.isEmpty(nom)) {
            etName.setError("Le nom est obligatoire");
            etName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(prenom)) {
            etPrenom.setError("Le prénom est obligatoire");
            etPrenom.requestFocus();
            return false;
        }

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

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Le mot de passe est obligatoire");
            etPassword.requestFocus();
            return false;
        }

        // Vérifier la longueur du mot de passe
        if (password.length() < Constants.MIN_PASSWORD_LENGTH) {
            etPassword.setError(Constants.MSG_MDP_COURT);
            etPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Veuillez confirmer le mot de passe");
            etConfirmPassword.requestFocus();
            return false;
        }

        // Vérifier si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Les mots de passe ne correspondent pas");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}