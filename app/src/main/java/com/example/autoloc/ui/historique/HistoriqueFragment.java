package com.example.autoloc.ui.historique;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.autoloc.R;
import com.example.autoloc.adapter.HistoriqueAdapter;
import com.example.autoloc.data.local.entity.Utilisateur;
import com.example.autoloc.ui.auth.LoginActivity;
import com.example.autoloc.utils.SessionManager;
import com.example.autoloc.viewmodel.HistoriqueViewModel;

public class HistoriqueFragment extends Fragment {

    private ImageView imgProfil;
    private TextView tvNom, tvEmail;
    private Button btnModifierProfil, btnDeconnexion;
    private RecyclerView recyclerViewHistorique;
    private LinearLayout emptyStateHistorique;

    private HistoriqueAdapter adapter;
    private HistoriqueViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historique, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = SessionManager.getInstance(requireContext());

        initViews(view);
        setupRecyclerView();
        setupViewModel();
        afficherInfosUtilisateur();
        setupListeners();
    }

    private void initViews(View view) {
        imgProfil = view.findViewById(R.id.imgProfil);
        tvNom = view.findViewById(R.id.tvNom);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnModifierProfil = view.findViewById(R.id.btnModifierProfil);
        btnDeconnexion = view.findViewById(R.id.btnDeconnexion);
        recyclerViewHistorique = view.findViewById(R.id.recyclerViewHistorique);
        emptyStateHistorique = view.findViewById(R.id.emptyStateHistorique);
    }

    private void setupRecyclerView() {
        adapter = new HistoriqueAdapter();
        recyclerViewHistorique.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewHistorique.setAdapter(adapter);

        // Gestion du clic sur un élément de l'historique
        adapter.setOnHistoriqueClickListener(reservation -> {
            // Afficher les détails de la réservation (à implémenter si nécessaire)
            // Pour l'instant, on ne fait rien
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HistoriqueViewModel.class);

        // Observer l'historique des réservations
        viewModel.getHistoriqueReservations().observe(getViewLifecycleOwner(), reservations -> {
            if (reservations == null || reservations.isEmpty()) {
                emptyStateHistorique.setVisibility(View.VISIBLE);
                recyclerViewHistorique.setVisibility(View.GONE);
            } else {
                emptyStateHistorique.setVisibility(View.GONE);
                recyclerViewHistorique.setVisibility(View.VISIBLE);
                adapter.submitList(reservations);
            }
        });
    }

    private void afficherInfosUtilisateur() {
        // Récupérer les infos depuis SessionManager
        String nomComplet = sessionManager.getUserFullName();
        String email = sessionManager.getUserEmail();

        tvNom.setText(nomComplet);
        tvEmail.setText(email);

        // Charger l'image de profil (placeholder pour le moment)
        Glide.with(requireContext())
                .load("") // URL vide = placeholder
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .circleCrop()
                .into(imgProfil);
    }

    private void setupListeners() {
        btnModifierProfil.setOnClickListener(v -> {
            // Fonctionnalité à implémenter
            // Pour l'instant, on affiche juste un message
            // Toast.makeText(requireContext(), "Modification du profil (à venir)", Toast.LENGTH_SHORT).show();
        });

        btnDeconnexion.setOnClickListener(v -> {
            // Déconnecter l'utilisateur
            sessionManager.logout();

            // Rediriger vers l'écran de connexion
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}