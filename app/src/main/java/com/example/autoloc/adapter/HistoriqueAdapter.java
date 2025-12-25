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

import com.bumptech.glide.Glide;
import com.example.autoloc.R;
import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.data.local.entity.ReservationAvecVoiture;
import com.example.autoloc.utils.PriceCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Adapter pour l'historique des réservations, conçu pour fonctionner avec l'objet
 * relationnel ReservationAvecVoiture et le layout item_historique.xml.
 */
public class HistoriqueAdapter extends ListAdapter<ReservationAvecVoiture, HistoriqueAdapter.HistoriqueViewHolder> {

    private OnHistoriqueClickListener listener;

    /**
     * Interface pour gérer les clics sur un élément de l'historique.
     */
    public interface OnHistoriqueClickListener {
        void onHistoriqueClick(Reservation reservation);
    }

    public HistoriqueAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * DiffUtil.ItemCallback pour calculer les différences entre deux listes
     * de manière efficace et optimiser les mises à jour du RecyclerView.
     */
    private static final DiffUtil.ItemCallback<ReservationAvecVoiture> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReservationAvecVoiture>() {
        @Override
        public boolean areItemsTheSame(@NonNull ReservationAvecVoiture oldItem, @NonNull ReservationAvecVoiture newItem) {
            // Vérifie si les items représentent le même objet (basé sur un ID unique)
            return oldItem.reservation.getIdReservation() == newItem.reservation.getIdReservation();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReservationAvecVoiture oldItem, @NonNull ReservationAvecVoiture newItem) {
            // Vérifie si le contenu de l'item a changé pour décider s'il faut redessiner la vue
            return oldItem.reservation.getStatut().equals(newItem.reservation.getStatut()) &&
                    oldItem.reservation.getMontantTotal() == newItem.reservation.getMontantTotal() &&
                    oldItem.getVoitureNom().equals(newItem.getVoitureNom());
        }
    };

    public void setOnHistoriqueClickListener(OnHistoriqueClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crée une nouvelle vue en "gonflant" le layout de l'item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historique, parent, false);
        return new HistoriqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriqueViewHolder holder, int position) {
        // Récupère l'objet à la position donnée et lie ses données à la vue
        ReservationAvecVoiture reservationAvecVoiture = getItem(position);
        if (reservationAvecVoiture != null) {
            holder.bind(reservationAvecVoiture);
        }
    }

    /**
     * Le ViewHolder contient la logique pour lier les données d'un objet
     * ReservationAvecVoiture aux vues (TextView, ImageView, etc.) du layout.
     */
    class HistoriqueViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgVoiture;
        private final TextView tvNomVoiture;
        private final TextView tvDates;
        private final TextView tvPrix;

        HistoriqueViewHolder(@NonNull View itemView) {
            super(itemView);

            // Liaison avec les vues définies dans item_historique.xml
            imgVoiture = itemView.findViewById(R.id.imgVoiture);
            tvNomVoiture = itemView.findViewById(R.id.tvNomVoiture);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvPrix = itemView.findViewById(R.id.tvPrix);

            // Configuration du listener de clic sur l'item
            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    // On passe l'objet Reservation original au listener, car c'est l'entité principale
                    listener.onHistoriqueClick(getItem(position).getReservation());
                }
            });
        }

        /**
         * Méthode pour remplir les vues avec les données de l'objet ReservationAvecVoiture.
         * @param reservationAvecVoiture L'objet contenant la réservation et sa voiture associée.
         */
        void bind(ReservationAvecVoiture reservationAvecVoiture) {
            // Récupère l'objet Reservation pour un accès plus simple aux données de base
            Reservation reservation = reservationAvecVoiture.getReservation();

            // 1. Nom de la voiture (provient de la classe de relation)
            tvNomVoiture.setText(reservationAvecVoiture.getVoitureNom());

            // 2. Image de la voiture (chargée avec Glide)
            Glide.with(itemView.getContext())
                    .load(reservationAvecVoiture.getVoitureImageUrl()) // URL de l'image
                    .placeholder(R.drawable.car_placeholder)          // Image affichée pendant le chargement
                    .error(R.drawable.car_placeholder)                // Image affichée en cas d'erreur
                    .into(imgVoiture);

            // 3. Dates de la réservation
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.FRENCH);
            LocalDate debut = LocalDate.parse(reservation.getDateDebut());
            LocalDate fin = LocalDate.parse(reservation.getDateFin());
            tvDates.setText(String.format("%s - %s", debut.format(formatter), fin.format(formatter)));

            // 4. Prix de la réservation
            // Utilise un helper "PriceCalculator.formatPrice" pour un formatage cohérent (ex: "350,00 €")
            tvPrix.setText(PriceCalculator.formatPrice(reservation.getMontantTotal()));
        }
    }
}
