package com.example.autoloc.ui.voiture;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoloc.R;
import com.example.autoloc.adapter.VoitureAdapter;
import com.example.autoloc.data.local.entity.Voiture;
import com.example.autoloc.viewmodel.VoitureViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoitureListFragment extends Fragment {

    private RecyclerView recyclerView;
    private VoitureAdapter adapter;
    private VoitureViewModel viewModel;
    private TextInputEditText etSearch;
    private View progressBar;
    private View emptyState;

    private Chip chipAllCars, chipSUV, chipSedan, chip4x4;

    private List<Voiture> toutesLesVoitures = new ArrayList<>();
    private String searchQuery = "";
    private String categorieSelectionnee = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voiture_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerView();
        setupViewModel();
        setupSearchFunctionality();
        setupFilters();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyState = view.findViewById(R.id.emptyState);
        etSearch = view.findViewById(R.id.etSearch);

        chipAllCars = view.findViewById(R.id.chipAllCars);
        chipSUV = view.findViewById(R.id.chipSUV);
        chipSedan = view.findViewById(R.id.chipSedan);
        chip4x4 = view.findViewById(R.id.chip4x4);
    }

    private void setupRecyclerView() {
        adapter = new VoitureAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        // Gestion du clic sur une voiture
        adapter.setOnVoitureClickListener(voiture -> {
            Bundle args = new Bundle();
            args.putInt("voitureId", voiture.getIdVoiture());

            Navigation.findNavController(requireView())
                    .navigate(R.id.action_list_to_detail, args);
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(VoitureViewModel.class);

        // Observer les voitures disponibles
        viewModel.getVoituresDisponibles().observe(getViewLifecycleOwner(), voitures -> {
            progressBar.setVisibility(View.GONE);

            if (voitures == null || voitures.isEmpty()) {
                emptyState.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyState.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                toutesLesVoitures = voitures;
                appliquerFiltres();
            }
        });
    }

    private void setupSearchFunctionality() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString().toLowerCase().trim();
                appliquerFiltres();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilters() {
        chipAllCars.setOnClickListener(v -> {
            resetChips();
            chipAllCars.setChecked(true);
            categorieSelectionnee = "";
            appliquerFiltres();
        });

        chipSUV.setOnClickListener(v -> {
            resetChips();
            chipSUV.setChecked(true);
            categorieSelectionnee = "SUV";
            appliquerFiltres();
        });

        chipSedan.setOnClickListener(v -> {
            resetChips();
            chipSedan.setChecked(true);
            categorieSelectionnee = "Berline";
            appliquerFiltres();
        });

        chip4x4.setOnClickListener(v -> {
            resetChips();
            chip4x4.setChecked(true);
            categorieSelectionnee = "4x4";
            appliquerFiltres();
        });
    }

    private void resetChips() {
        chipAllCars.setChecked(false);
        chipSUV.setChecked(false);
        chipSedan.setChecked(false);
        chip4x4.setChecked(false);
    }

    private void appliquerFiltres() {
        List<Voiture> voituresFiltrees = new ArrayList<>(toutesLesVoitures);

        // Filtre par catégorie
        if (!categorieSelectionnee.isEmpty()) {
            voituresFiltrees = voituresFiltrees.stream()
                    .filter(v -> v.getCategorie().equalsIgnoreCase(categorieSelectionnee))
                    .collect(Collectors.toList());
        }

        // Filtre par recherche textuelle
        if (!searchQuery.isEmpty()) {
            voituresFiltrees = voituresFiltrees.stream()
                    .filter(v -> v.getMarque().toLowerCase().contains(searchQuery) ||
                            v.getModele().toLowerCase().contains(searchQuery) ||
                            v.getCategorie().toLowerCase().contains(searchQuery))
                    .collect(Collectors.toList());
        }

        adapter.submitList(voituresFiltrees);

        // Afficher l'empty state si aucun résultat
        if (voituresFiltrees.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}