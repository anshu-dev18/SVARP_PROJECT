package com.example.svarp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        ConstraintLayout card1 = findViewById(R.id.card1);
        ConstraintLayout card2 = findViewById(R.id.card2);
//        ConstraintLayout card3 = findViewById(R.id.card3);


        card1.setOnClickListener(v ->
                startActivity(new Intent(this, privacy.class))
        );
        card2.setOnClickListener(v ->
                startActivity(new Intent(this, terms.class))
        );
//        card3.setOnClickListener(v ->
//                startActivity(new Intent(this, privacy.class))
//        );
    }
}