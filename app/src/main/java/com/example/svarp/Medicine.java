package com.example.svarp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;


public class Medicine extends AppCompatActivity {
    private MaterialButton btnAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicine);
        btnAnalyze = findViewById(R.id.btnAnalyze);

        btnAnalyze.setVisibility(View.GONE);
    }
}