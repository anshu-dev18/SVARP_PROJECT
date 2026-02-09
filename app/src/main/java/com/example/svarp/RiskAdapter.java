package com.example.svarp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class RiskAdapter extends RecyclerView.Adapter<RiskAdapter.RiskViewHolder> {

    private final String risk;

    public RiskAdapter(String risk) {
        this.risk = risk;
    }

    @NonNull
    @Override
    public RiskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_holder, parent, false);
        return new RiskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiskViewHolder holder, int position) {

        // item_holder root = MaterialCardView
        MaterialCardView card = (MaterialCardView) holder.itemView;

        // 🔥 FLAT STYLE (NO BG, NO BORDER)
        card.setStrokeWidth(0);
        card.setCardBackgroundColor(Color.TRANSPARENT);
        card.setRadius(0f);
        card.setUseCompatPadding(false);

        if ("SAFE".equals(risk)) {
            holder.text.setText("Safe");
            holder.icon.setImageResource(R.drawable.safe);
            holder.icon.setColorFilter(Color.parseColor("#2E7D32")); // green

        } else if ("MODERATE".equals(risk)) {
            holder.text.setText("Moderate Risk");
            holder.icon.setImageResource(R.drawable.error);
            holder.icon.setColorFilter(Color.parseColor("#DB671A")); // orange

        } else if ("DANGER".equals(risk)) {
            holder.text.setText("Danger");
            holder.icon.setImageResource(R.drawable.warning);
            holder.icon.setColorFilter(Color.parseColor("#C62828")); // red
        }
    }

    @Override
    public int getItemCount() {
        return 1; // ONLY ONE RISK ITEM
    }

    static class RiskViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView text;

        RiskViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgSymptom);
            text = itemView.findViewById(R.id.txtSymptom);
        }
    }
}
