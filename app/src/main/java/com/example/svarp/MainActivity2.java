package com.example.svarp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        MaterialButton btnTalk = findViewById(R.id.btn_talk);
        MaterialButton btnSymp = findViewById(R.id.btn_symp);
        MaterialButton btnInfo = findViewById(R.id.btn_info);
        ImageView languageIcon = findViewById(R.id.imageView);


        // Talk button → opens voice interaction screen
        btnTalk.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, talkTOsvarp.class);
            startActivity(intent);
        });

        // Symptoms button → opens symptom selection screen
        btnSymp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, Select_Symptoms.class);
            startActivity(intent);
        });

        // Medical info button → opens medicine info screen
        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, Medicine.class);
            startActivity(intent);
        });
        languageIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, language_selection.class);
            startActivity(intent);
        });

    }
}
