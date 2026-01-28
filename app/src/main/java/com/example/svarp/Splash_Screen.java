package com.example.svarp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        Button btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(Splash_Screen.this, language_selection.class);
            startActivity(intent);
            finish();
        });
    }
}