package com.example.autoloc.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoloc.R;
import com.example.autoloc.data.local.entity.Reservation;
import com.example.autoloc.utils.PriceCalculator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationAdapter extends ListAdapter<Reservation, ReservationAdapter.ReservationViewHolder> {

    private OnReservationClickListener listener;

    public interface OnReservationClickListener {
        void onReservationClick(Reservation reservation);
        void onAnnulerClick(Reservation reservation);
    }

    public ReservationAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Reservation> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reservation>() {
        @Override
        public boolean areItemsTheSame(@NonNull Reservation oldItem, @NonNull Reservation newItem) {
            return oldItem.getIdReservation() == newItem.getIdReservation();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reservation oldItem, @NonNull Reservation newItem) {
            return oldItem.getStatut().equals(newItem.getStatut()) &&
                    oldItem.getDateDebut().equals(newItem.getDateDebut()) &&
                    oldItem.getDateFin().equals(newItem.getDateFin()) &&
                    oldItem.getMontantTotal() == newItem.getMontantTotal();
        }
    };

    public void setOnReservationClickListener(OnReservationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = getItem(position);
        holder.bind(reservation);
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvReservationId;
        private final TextView tvDateDebut;
        private final TextView tvDateFin;
        private final TextView tvDuree;
        private final TextView tvMontant;
        private final TextView tvStatut;
        private final TextView tvDateReservation;
        private final TextView btnAnnuler;

        ReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvReservationId = itemView.findViewById(R.id.tvReservationId);
            tvDateDebut = itemView.findViewById(R.id.tvDateDebut);
            tvDateFin = itemView.findViewById(R.id.tvDateFin);
            tvDuree = itemView.findViewById(R.id.tvDuree);
            tvMontant = itemView.findViewById(R.id.tvMontant);
            tvStatut = itemView.findViewById(R.id.tvStatut);
            tvDateReservation = itemView.findViewById(R.id.tvDateReservation);
            btnAnnuler = itemView.findViewById(R.id.btnAnnuler);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onReservationClick(getItem(position));
                }
            });

            btnAnnuler.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onAnnulerClick(getItem(position));
                }
            });
        }

        void bind(Reservation reservation) {
            // ID de réservation
            tvReservationId.setText("Réservation #" + reservation.getIdReservation());

            // Formatter les dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(reservation.getDateDebut());
            LocalDate fin = LocalDate.parse(reservation.getDateFin());

            tvDateDebut.setText(debut.format(formatter));
            tvDateFin.setText(fin.format(formatter));

            // Durée
            int duree = reservation.calculerDuree();
            tvDuree.setText(duree + (duree > 1 ? " jours" : " jour"));

            // Montant
            tvMontant.setText(PriceCalculator.formatPrice(reservation.getMontantTotal()));

            // Date de réservation
            LocalDate dateRes = LocalDate.parse(reservation.getDateReservation());
            tvDateReservation.setText("Réservé le " + dateRes.format(formatter));

            // Statut avec couleur
            String statut = reservation.getStatut();
            tvStatut.setText(getStatutText(statut));
            tvStatut.setTextColor(getStatutColor(statut));

            // Bouton Annuler (visible uniquement si EN_ATTENTE ou VALIDEE)
            if (statut.equals("EN_ATTENTE") || statut.equals("VALIDEE")) {
                btnAnnuler.setVisibility(View.VISIBLE);
            } else {
                btnAnnuler.setVisibility(View.GONE);
            }
        }

        private String getStatutText(String statut) {
            switch (statut) {
                case "EN_ATTENTE":
                    return "En attente";
                case "VALIDEE":
                    return "Validée";
                case "ANNULEE":
                    return "Annulée";
                case "TERMINEE":
                    return "Terminée";
                default:
                    return statut;
            }
        }

        private int getStatutColor(String statut) {
            switch (statut) {
                case "EN_ATTENTE":
                    return Color.parseColor("#FF9800"); // Orange
                case "VALIDEE":
                    return Color.parseColor("#4CAF50"); // Vert
                case "ANNULEE":
                    return Color.parseColor("#F44336"); // Rouge
                case "TERMINEE":
                    return Color.parseColor("#9E9E9E"); // Gris
                default:
                    return Color.BLACK;
            }
        }
    }
}