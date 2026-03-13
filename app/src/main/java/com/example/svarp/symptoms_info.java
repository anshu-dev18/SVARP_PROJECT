package com.example.svarp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class symptoms_info extends AppCompatActivity {

    private TextView txtSymptoms;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_info);

        ImageView btnBack = findViewById(R.id.btnBack);
        txtSymptoms = findViewById(R.id.txtSymptoms);
        txtInfo = findViewById(R.id.txtInfo);

        btnBack.setOnClickListener(v -> finish());

        ArrayList<String> symptoms =
                getIntent().getStringArrayListExtra("selected_symptoms");

        if (symptoms != null && !symptoms.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String s : symptoms) {
                builder.append("• ").append(getDisplayName(s)).append("\n");
            }
            txtSymptoms.setText(builder.toString());
            txtInfo.setText(generateInfo(symptoms));
        }
    }

    private String getDisplayName(String englishName) {
        switch (englishName) {
            case "Fever":
                return getString(R.string.info_fever).split("\n")[0].replaceAll(".*? ", "").trim();
            default:
                break;
        }
        // ✅ Simpler approach — just use symptom_display_list array
        String[] englishList = {
                "Fever", "Cough", "Headache", "Fatigue", "Sore Throat",
                "Vomiting", "Body Ache", "Dizziness", "Skin Rash", "Eye Discomfort",
                "Toothache", "Chest Pain", "Shortness of Breath", "Nausea", "Weakness",
                "Weight Loss", "Blood in Stool", "Blood in Urine", "Diarrhea", "Stomach Ache"
        };
        String[] displayList = getResources().getStringArray(R.array.symptom_display_list);
        for (int i = 0; i < englishList.length; i++) {
            if (englishList[i].equals(englishName)) return displayList[i];
        }
        return englishName;
    }

    private String generateInfo(ArrayList<String> symptoms) {
        StringBuilder info = new StringBuilder();
        for (String s : symptoms) {
            switch (s) {
                case "Fever":
                    info.append(getString(R.string.info_fever));
                    break;
                case "Cough":
                    info.append(getString(R.string.info_cough));
                    break;
                case "Headache":
                    info.append(getString(R.string.info_headache));
                    break;
                case "Fatigue":
                    info.append(getString(R.string.info_fatigue));
                    break;
                case "Sore Throat":
                    info.append(getString(R.string.info_sore_throat));
                    break;
                case "Vomiting":
                    info.append(getString(R.string.info_vomiting));
                    break;
                case "Body Ache":
                    info.append(getString(R.string.info_body_ache));
                    break;
                case "Dizziness":
                    info.append(getString(R.string.info_dizziness));
                    break;
                case "Skin Rash":
                    info.append(getString(R.string.info_skin_rash));
                    break;
                case "Eye Discomfort":
                    info.append(getString(R.string.info_eye_discomfort));
                    break;
                case "Toothache":
                    info.append(getString(R.string.info_toothache));
                    break;
                case "Chest Pain":
                    info.append(getString(R.string.info_chest_pain));
                    break;
                case "Shortness of Breath":
                    info.append(getString(R.string.info_shortness_of_breath));
                    break;
                case "Nausea":
                    info.append(getString(R.string.info_nausea));
                    break;
                case "Weakness":
                    info.append(getString(R.string.info_weakness));
                    break;
                case "Weight Loss":
                    info.append(getString(R.string.info_weight_loss));
                    break;
                case "Blood in Stool":
                    info.append(getString(R.string.info_blood_in_stool));
                    break;
                case "Blood in Urine":
                    info.append(getString(R.string.info_blood_in_urine));
                    break;
                case "Diarrhea":
                    info.append(getString(R.string.info_diarrhea));
                    break;
                case "Stomach Ache":
                    info.append(getString(R.string.info_stomach_ache));
                    break;
            }
            info.append("\n\n");
        }
        if (info.length() == 0) info.append(getString(R.string.symptoms_fallback));
        return info.toString();
    }
}