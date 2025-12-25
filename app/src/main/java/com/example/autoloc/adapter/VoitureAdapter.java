package com.example.autoloc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoloc.R;
import com.example.autoloc.data.local.entity.Voiture;
import com.example.autoloc.utils.PriceCalculator;

public class VoitureAdapter extends ListAdapter<Voiture, VoitureAdapter.VoitureViewHolder> {

    private OnVoitureClickListener listener;

    public interface OnVoitureClickListener {
        void onVoitureClick(Voiture voiture);
    }

    public VoitureAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Voiture> DIFF_CALLBACK = new DiffUtil.ItemCallback<Voiture>() {
        @Override
        public boolean areItemsTheSame(@NonNull Voiture oldItem, @NonNull Voiture newItem) {
            return oldItem.getIdVoiture() == newItem.getIdVoiture();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Voiture oldItem, @NonNull Voiture newItem) {
            return oldItem.getMarque().equals(newItem.getMarque()) &&
                    oldItem.getModele().equals(newItem.getModele()) &&
                    oldItem.getPrixParJour() == newItem.getPrixParJour() &&
                    oldItem.isDisponible() == newItem.isDisponible();
        }
    };

    public void setOnVoitureClickListener(OnVoitureClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VoitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voiture, parent, false);
        return new VoitureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoitureViewHolder holder, int position) {
        Voiture voiture = getItem(position);
        holder.bind(voiture);
    }

    class VoitureViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgVoiture;
        private final TextView tvMarque;
        private final TextView tvModele;
        private final TextView tvAnnee;
        private final TextView tvCategorie;
        private final TextView tvPrix;
        private final TextView tvCarburant;
        private final TextView tvPlaces;
        private final View viewDisponibilite;
        private final TextView tvDisponibilite;

        VoitureViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVoiture = itemView.findViewById(R.id.imgVoiture);
            tvMarque = itemView.findViewById(R.id.tvMarque);
            tvModele = itemView.findViewById(R.id.tvModele);
            tvAnnee = itemView.findViewById(R.id.tvAnnee);
            tvCategorie = itemView.findViewById(R.id.tvCategorie);
            tvPrix = itemView.findViewById(R.id.tvPrix);
            tvCarburant = itemView.findViewById(R.id.tvCarburant);
            tvPlaces = itemView.findViewById(R.id.tvPlaces);
            viewDisponibilite = itemView.findViewById(R.id.viewDisponibilite);
            tvDisponibilite = itemView.findViewById(R.id.tvDisponibilite);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onVoitureClick(getItem(position));
                }
            });
        }

        void bind(Voiture voiture) {
            // Marque et modèle
            tvMarque.setText(voiture.getMarque());
            tvModele.setText(voiture.getModele());

            // Année
            tvAnnee.setText(String.valueOf(voiture.getAnnee()));

            // Catégorie
            tvCategorie.setText(voiture.getCategorie());

            // Prix formaté
            tvPrix.setText(PriceCalculator.formatPrice(voiture.getPrixParJour()) + "/jour");

            // Carburant
            tvCarburant.setText(voiture.getTypeCarburant());

            // Nombre de places
            tvPlaces.setText(voiture.getNombrePlaces() + " places");

            // Disponibilité
            if (voiture.isDisponible()) {
                viewDisponibilite.setBackgroundResource(R.drawable.bg_disponible);
                tvDisponibilite.setText("Disponible");
                tvDisponibilite.setTextColor(itemView.getContext().getColor(android.R.color.holo_green_dark));
            } else {
                viewDisponibilite.setBackgroundResource(R.drawable.bg_indisponible);
                tvDisponibilite.setText("Indisponible");
                tvDisponibilite.setTextColor(itemView.getContext().getColor(android.R.color.holo_red_dark));
            }

            // Image (placeholder pour l'instant)
            // TODO: Charger l'image depuis voiture.getImageUrl() avec Glide ou Picasso
            imgVoiture.setImageResource(R.drawable.ic_car_placeholder);
        }
    }
}