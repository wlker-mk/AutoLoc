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
import com.example.autoloc.adapter.ReservationAdapter;
import com.example.autoloc.ui.auth.LoginActivity;
import com.example.autoloc.utils.SessionManager;
import com.example.autoloc.viewmodel.HistoriqueViewModel;
import com.example.autoloc.viewmodel.ReservationViewModel;

public class HistoriqueFragment extends Fragment {

    private ImageView imgProfil;
    private TextView tvNom, tvEmail;
    private Button btnModifierProfil, btnDeconnexion;

    // RecyclerView pour les réservations en cours
    private RecyclerView recyclerViewReservations;
    private LinearLayout emptyStateReservations;
    private TextView tvReservationsTitle;

    // RecyclerView pour l'historique
    private RecyclerView recyclerViewHistorique;
    private LinearLayout emptyStateHistorique;

    private ReservationAdapter reservationAdapter;
    private HistoriqueAdapter historiqueAdapter;

    private HistoriqueViewModel historiqueViewModel;
    private ReservationViewModel reservationViewModel;
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
        setupRecyclerViews();
        setupViewModels();
        afficherInfosUtilisateur();
        setupListeners();
    }

    private void initViews(View view) {
        imgProfil = view.findViewById(R.id.imgProfil);
        tvNom = view.findViewById(R.id.tvNom);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnModifierProfil = view.findViewById(R.id.btnModifierProfil);
        btnDeconnexion = view.findViewById(R.id.btnDeconnexion);

        // Réservations en cours
        tvReservationsTitle = view.findViewById(R.id.tvReservationsTitle);
        recyclerViewReservations = view.findViewById(R.id.recyclerViewReservations);
        emptyStateReservations = view.findViewById(R.id.emptyStateReservations);

        // Historique
        recyclerViewHistorique = view.findViewById(R.id.recyclerViewHistorique);
        emptyStateHistorique = view.findViewById(R.id.emptyStateHistorique);
    }

    private void setupRecyclerViews() {
        // Adapter pour les réservations en cours
        reservationAdapter = new ReservationAdapter();
        recyclerViewReservations.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewReservations.setAdapter(reservationAdapter);

        // Adapter pour l'historique
        historiqueAdapter = new HistoriqueAdapter();
        recyclerViewHistorique.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewHistorique.setAdapter(historiqueAdapter);
    }

    private void setupViewModels() {
        historiqueViewModel = new ViewModelProvider(this).get(HistoriqueViewModel.class);
        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        int userId = sessionManager.getUserId();

        // Observer les réservations en cours (EN_ATTENTE et VALIDEE)
        reservationViewModel.getReservationsUtilisateur(userId).observe(getViewLifecycleOwner(), reservations -> {
            if (reservations == null || reservations.isEmpty()) {
                emptyStateReservations.setVisibility(View.VISIBLE);
                recyclerViewReservations.setVisibility(View.GONE);
            } else {
                // Filtrer uniquement les réservations en cours
                var reservationsEnCours = reservations.stream()
                        .filter(r -> r.getStatut().equals("EN_ATTENTE") || r.getStatut().equals("VALIDEE"))
                        .collect(java.util.stream.Collectors.toList());

                if (reservationsEnCours.isEmpty()) {
                    emptyStateReservations.setVisibility(View.VISIBLE);
                    recyclerViewReservations.setVisibility(View.GONE);
                } else {
                    emptyStateReservations.setVisibility(View.GONE);
                    recyclerViewReservations.setVisibility(View.VISIBLE);
                    reservationAdapter.submitList(reservationsEnCours);
                }
            }
        });

        // Observer l'historique (TERMINEE et ANNULEE)
        historiqueViewModel.getHistoriqueReservations().observe(getViewLifecycleOwner(), reservations -> {
            if (reservations == null || reservations.isEmpty()) {
                emptyStateHistorique.setVisibility(View.VISIBLE);
                recyclerViewHistorique.setVisibility(View.GONE);
            } else {
                emptyStateHistorique.setVisibility(View.GONE);
                recyclerViewHistorique.setVisibility(View.VISIBLE);
                historiqueAdapter.submitList(reservations);
            }
        });

        // Gestion des clics sur les réservations
        reservationAdapter.setOnReservationClickListener(new ReservationAdapter.OnReservationClickListener() {
            @Override
            public void onReservationClick(com.example.autoloc.data.local.entity.Reservation reservation) {
                // Afficher les détails si nécessaire
            }

            @Override
            public void onAnnulerClick(com.example.autoloc.data.local.entity.Reservation reservation) {
                reservation.annulerReservation();
                reservationViewModel.modifier(reservation);
            }
        });
    }

    private void afficherInfosUtilisateur() {
        String nomComplet = sessionManager.getUserFullName();
        String email = sessionManager.getUserEmail();

        tvNom.setText(nomComplet);
        tvEmail.setText(email);

        Glide.with(requireContext())
                .load("")
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .circleCrop()
                .into(imgProfil);
    }

    private void setupListeners() {
        btnModifierProfil.setOnClickListener(v -> {
            // Fonctionnalité à implémenter
        });

        btnDeconnexion.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}