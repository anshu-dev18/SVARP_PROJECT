package com.example.svarp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.button.MaterialButton;

public class Main_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_screen);

        // Views
        MaterialButton btnMic = findViewById(R.id.btn_mic);
        ImageView appSetting = findViewById(R.id.appSetting);
        ConstraintLayout cardSymptoms = findViewById(R.id.cardSymptoms);
        ConstraintLayout cardHistory = findViewById(R.id.cardHistory);

        // Match system bars with navigation bar color
        getWindow().setStatusBarColor(
                getResources().getColor(R.color.blue_900, getTheme())
        );

// Optional: also match bottom navigation bar if you want full consistency
        getWindow().setNavigationBarColor(
                getResources().getColor(R.color.blue_100, getTheme())
        );

// Correct icon contrast (light background → dark icons)
        WindowInsetsControllerCompat controller =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());

        if (controller != null) {
            controller.setAppearanceLightStatusBars(true);
            controller.setAppearanceLightNavigationBars(true);
        }
        View root = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // Navigation
        btnMic.setOnClickListener(v ->
                startActivity(new Intent(this, TalkToSvarp.class))
        );
        appSetting.setOnClickListener(v ->
                startActivity(new Intent(this, setting.class))
        );

        cardSymptoms.setOnClickListener(v ->
                startActivity(new Intent(this, Select_Symptoms.class))
        );

        cardHistory.setOnClickListener(v ->
                startActivity(new Intent(this, history.class))
        );
    }
}