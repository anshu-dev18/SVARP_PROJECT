package com.example.svarp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Select_Symptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_symptoms);

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1️⃣ Get RecyclerView
        RecyclerView recyclerView = findViewById(R.id.symptomList);

        // 2️⃣ Load symptoms from strings.xml
        String[] symptoms = getResources().getStringArray(R.array.symptom_list);

        // 3️⃣ Grid layout: 3 items per row
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // 4️⃣ Attach adapter (connects item_symptom.xml)
        SymptomAdapter adapter = new SymptomAdapter(symptoms);
        recyclerView.setAdapter(adapter);
    }
}
