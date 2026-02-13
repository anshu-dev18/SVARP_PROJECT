package com.example.svarp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class assessment_res extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_res);

        // Navigation
        Button btnHome = findViewById(R.id.btnHome);
        Button btnCare = findViewById(R.id.btnCare);

        btnHome.setOnClickListener(v ->
                startActivity(new Intent(this, Main_Screen.class))
        );

        btnCare.setOnClickListener(v ->
                startActivity(new Intent(this, care_guidance.class))
        );

        setupRiskLevel();
    }

    private void setupRiskLevel() {

        RecyclerView riskRecycler = findViewById(R.id.riskLevel);
        riskRecycler.setLayoutManager(new LinearLayoutManager(this));
        riskRecycler.setNestedScrollingEnabled(false);

        // 🔁 CHANGE THIS VALUE BASED ON YOUR LOGIC
        String risk = "MODERATE"; // SAFE | MODERATE | DANGER

        RiskAdapter adapter = new RiskAdapter(risk);
        riskRecycler.setAdapter(adapter);
    }
}
