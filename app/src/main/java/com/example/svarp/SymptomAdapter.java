package com.example.svarp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.ViewHolder> {

    private final String[] symptoms;

    public SymptomAdapter(String[] symptoms) {
        this.symptoms = symptoms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_symptom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSymptom.setText(symptoms[position]);
        holder.imgSymptom.setImageResource(R.drawable.med_box);
    }

    @Override
    public int getItemCount() {
        return symptoms.length;
    }

    // ⚠️ THIS CLASS MUST BE INSIDE THE ADAPTER
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSymptom;
        TextView txtSymptom;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSymptom = itemView.findViewById(R.id.imgSymptom);
            txtSymptom = itemView.findViewById(R.id.txtSymptom);
        }
    }
}
