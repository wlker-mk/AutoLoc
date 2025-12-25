package com.example.autoloc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.autoloc.R;
import com.example.autoloc.data.local.entity.Voiture;
import com.example.autoloc.utils.PriceCalculator;

public class VoitureAdapter extends ListAdapter<Voiture, VoitureAdapter.VoitureViewHolder> {

    private OnVoitureClickListener listener;

    public interface OnVoitureClickListener {
        void onDetailsClick(Voiture voiture);
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
                    oldItem.getImageUrl().equals(newItem.getImageUrl());
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
        private final TextView tvPrixOverlay;
        private final TextView tvNomVoiture;
        private final Button btnViewDetails;
        private final Context context;

        VoitureViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            imgVoiture = itemView.findViewById(R.id.imgVoiture);
            tvPrixOverlay = itemView.findViewById(R.id.tvPrixOverlay);
            tvNomVoiture = itemView.findViewById(R.id.tvNomVoiture);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);

            btnViewDetails.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onDetailsClick(getItem(position));
                }
            });
        }

        void bind(Voiture voiture) {
            // Nom complet de la voiture
            String nomComplet = voiture.getMarque() + " " + voiture.getModele();
            tvNomVoiture.setText(nomComplet);

            // Formatage du prix
            String prixFormatte = PriceCalculator.formatPrice(voiture.getPrixParJour()) + " / day";
            tvPrixOverlay.setText(prixFormatte);

            // Chargement de l'image avec Glide
            Glide.with(context)
                    .load(voiture.getImageUrl())
                    .placeholder(R.drawable.car_placeholder)
                    .error(R.drawable.car_placeholder)
                    .centerCrop()
                    .into(imgVoiture);
        }
    }
}