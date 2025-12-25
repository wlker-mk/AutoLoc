package com.example.autoloc.ui.reservation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.autoloc.R;
import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.utils.SessionManager;
import com.example.autoloc.viewmodel.ReservationViewModel;
import com.example.autoloc.viewmodel.VoitureViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ReservationFragment extends Fragment {

    private TextInputEditText etDateDebut, etDateFin;
    private AutoCompleteTextView actvLieuPrise;
    private Button btnValider;
    private ImageButton btnBack;

    private ReservationViewModel reservationViewModel;
    private VoitureViewModel voitureViewModel;

    private int voitureId;
    private LocalDate dateDebut, dateFin;
    private double prixParJour;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            voitureId = getArguments().getInt("voitureId", -1);
        }

        initViews(view);
        setupViewModels();
        setupLieuxPrise();
        setupListeners();
    }

    private void initViews(View view) {
        etDateDebut = view.findViewById(R.id.etDateDebut);
        etDateFin = view.findViewById(R.id.etDateFin);
        actvLieuPrise = view.findViewById(R.id.actvLieuPrise);
        btnValider = view.findViewById(R.id.btnValider);
        btnBack = view.findViewById(R.id.btnBack);
    }

    private void setupViewModels() {
        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        voitureViewModel = new ViewModelProvider(this).get(VoitureViewModel.class);

        // Récupérer le prix de la voiture
        voitureViewModel.getVoitureParId(voitureId).observe(getViewLifecycleOwner(), voiture -> {
            if (voiture != null) {
                prixParJour = voiture.getPrixParJour();
            }
        });
    }

    private void setupLieuxPrise() {
        // Liste des lieux de prise disponibles
        String[] lieux = {
                "Agence Centre-Ville - Lomé",
                "Aéroport International Gnassingbé Eyadéma",
                "Gare Routière de Lomé",
                "Agence Kodjoviakopé",
                "Agence Bè-Kpota"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                lieux
        );
        actvLieuPrise.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        // DatePicker pour date début
        etDateDebut.setOnClickListener(v -> afficherDatePicker(true));

        // DatePicker pour date fin
        etDateFin.setOnClickListener(v -> afficherDatePicker(false));

        // Validation de la réservation
        btnValider.setOnClickListener(v -> validerReservation());
    }

    private void afficherDatePicker(boolean isDateDebut) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    LocalDate dateSelectionnee = LocalDate.of(year, month + 1, dayOfMonth);

                    if (isDateDebut) {
                        dateDebut = dateSelectionnee;
                        etDateDebut.setText(dateDebut.format(dateFormatter));
                    } else {
                        dateFin = dateSelectionnee;
                        etDateFin.setText(dateFin.format(dateFormatter));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Empêcher la sélection de dates passées
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void validerReservation() {
        // Validation des champs
        if (!validerChamps()) {
            return;
        }

        // Récupérer l'ID de l'utilisateur connecté
        int idUtilisateur = SessionManager.getInstance(requireContext()).getUserId();

        if (idUtilisateur == -1) {
            Toast.makeText(requireContext(), "Erreur: utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer la réservation
        Reservation reservation = new Reservation(
                idUtilisateur,
                voitureId,
                dateDebut.toString(),
                dateFin.toString(),
                LocalDate.now().toString()
        );

        // Calculer le montant
        reservation.calculerMontant(prixParJour);

        // Insérer la réservation
        reservationViewModel.inserer(reservation);

        // Afficher un message de succès
        Toast.makeText(requireContext(), "Réservation effectuée avec succès !", Toast.LENGTH_LONG).show();

        // Retourner à l'écran précédent
        Navigation.findNavController(requireView()).navigateUp();
    }

    private boolean validerChamps() {
        // Vérifier la date de début
        if (dateDebut == null) {
            Toast.makeText(requireContext(), "Veuillez sélectionner une date de début", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Vérifier la date de fin
        if (dateFin == null) {
            Toast.makeText(requireContext(), "Veuillez sélectionner une date de fin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Vérifier que la date de fin est après la date de début
        if (dateFin.isBefore(dateDebut) || dateFin.isEqual(dateDebut)) {
            Toast.makeText(requireContext(), "La date de fin doit être après la date de début", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Vérifier le lieu de prise
        String lieu = actvLieuPrise.getText().toString().trim();
        if (lieu.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez sélectionner un lieu de prise", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}