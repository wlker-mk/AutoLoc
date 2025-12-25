package com.example.autoloc.ui.voiture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.autoloc.R;
import com.example.autoloc.utils.PriceCalculator;
import com.example.autoloc.viewmodel.VoitureViewModel;

public class VoitureDetailFragment extends Fragment {

    private ImageView imgVoiture;
    private TextView tvMarque, tvModele, tvPrix;
    private TextView tvDescription;
    private TextView tvSieges, tvTransmission, tvCarburant;
    private Button btnReserver;
    private ImageButton btnBack;

    private VoitureViewModel viewModel;
    private int voitureId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voiture_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Récupérer l'ID de la voiture depuis les arguments
        if (getArguments() != null) {
            voitureId = getArguments().getInt("voitureId", -1);
        }

        initViews(view);
        setupViewModel();
        setupListeners();
    }

    private void initViews(View view) {
        imgVoiture = view.findViewById(R.id.imgVoiture);
        tvMarque = view.findViewById(R.id.tvMarque);
        tvModele = view.findViewById(R.id.tvModele);
        tvPrix = view.findViewById(R.id.tvPrix);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvSieges = view.findViewById(R.id.tvSieges);
        tvTransmission = view.findViewById(R.id.tvTransmission);
        tvCarburant = view.findViewById(R.id.tvCarburant);
        btnReserver = view.findViewById(R.id.btnReserver);
        btnBack = view.findViewById(R.id.btnBack);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(VoitureViewModel.class);

        // Observer la voiture spécifique
        viewModel.getVoitureParId(voitureId).observe(getViewLifecycleOwner(), voiture -> {
            if (voiture != null) {
                afficherDetailsVoiture(voiture);
            }
        });
    }

    private void afficherDetailsVoiture(com.example.autoloc.data.local.entity.Voiture voiture) {
        // Marque et modèle
        tvMarque.setText(voiture.getMarque());
        tvModele.setText(voiture.getModele());

        // Prix
        tvPrix.setText(PriceCalculator.formatPriceShort(voiture.getPrixParJour()));

        // Description générique basée sur la catégorie
        String description = genererDescription(voiture);
        tvDescription.setText(description);

        // Spécifications
        tvSieges.setText(String.valueOf(voiture.getNombrePlaces()));
        tvTransmission.setText("Automatique"); // Valeur par défaut, pourrait être ajoutée à l'entité
        tvCarburant.setText(voiture.getTypeCarburant());

        // Charger l'image avec Glide
        Glide.with(requireContext())
                .load(voiture.getImageUrl())
                .placeholder(R.drawable.car_detail_placeholder)
                .error(R.drawable.car_detail_placeholder)
                .into(imgVoiture);
    }

    private String genererDescription(com.example.autoloc.data.local.entity.Voiture voiture) {
        // Génération d'une description personnalisée selon la catégorie
        String categorie = voiture.getCategorie();
        String marque = voiture.getMarque();
        String modele = voiture.getModele();

        StringBuilder desc = new StringBuilder();
        desc.append("Découvrez la ").append(marque).append(" ").append(modele).append(", ");

        switch (categorie) {
            case "SUV":
                desc.append("un véhicule spacieux et confortable, parfait pour les longs trajets en famille. ");
                desc.append("Avec ses ").append(voiture.getNombrePlaces()).append(" places et sa motorisation ")
                        .append(voiture.getTypeCarburant()).append(", ce SUV allie puissance et praticité.");
                break;
            case "Berline":
                desc.append("une berline élégante et raffinée, idéale pour vos déplacements professionnels. ");
                desc.append("Son confort et ses finitions haut de gamme vous garantissent une expérience de conduite exceptionnelle.");
                break;
            case "Sportive":
                desc.append("un véhicule sportif aux performances exceptionnelles. ");
                desc.append("Vivez l'expérience de conduite ultime avec ce modèle iconique qui allie design et puissance.");
                break;
            case "4x4":
                desc.append("un 4x4 robuste et polyvalent, conçu pour toutes les aventures. ");
                desc.append("Que ce soit en ville ou hors des sentiers battus, ce véhicule vous accompagnera partout.");
                break;
            default:
                desc.append("un véhicule moderne et fiable, parfait pour tous vos besoins de déplacement. ");
                desc.append("Profitez d'une expérience de location sans souci avec ce modèle de qualité.");
        }

        return desc.toString();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        btnReserver.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("voitureId", voitureId);

            Navigation.findNavController(v)
                    .navigate(R.id.action_detail_to_reservation, args);
        });
    }
}