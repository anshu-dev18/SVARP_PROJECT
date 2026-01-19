package com.example.svarp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Medicine extends AppCompatActivity {

    private AutoCompleteTextView actForm;
    private EditText etMedicine, etStrength, etProblem;
    private RadioGroup rgPrescribed;
    private MaterialButton btnAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicine);

        // Views
        actForm = findViewById(R.id.actForm);
        etMedicine = findViewById(R.id.etMedicine);
        etStrength = findViewById(R.id.etStrength);
        etProblem = findViewById(R.id.etProblem);
        rgPrescribed = findViewById(R.id.rgPrescribed);
        btnAnalyze = findViewById(R.id.btnAnalyze);

        // Initially hidden
        btnAnalyze.setVisibility(View.GONE);

        setupFormDropdown();
        setupListeners();
    }

    private void setupFormDropdown() {
        String[] forms = {"Tablet", "Capsule", "Syrup", "Injection", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.simple_dropdown_item,
                forms
        );

        actForm.setAdapter(adapter);
        actForm.setThreshold(1);

        // FORCE dropdown to open on click
        actForm.setOnClickListener(v -> actForm.showDropDown());

        actForm.setOnItemClickListener((parent, view, position, id) -> {
            String selectedForm = forms[position];

            switch (selectedForm) {

                case "Tablet":
                    etStrength.setHint("Dose (e.g., 500 mg)");
                    break;

                case "Capsule":
                    etStrength.setHint("Dose (e.g., 250 mg)");
                    break;

                case "Syrup":
                    etStrength.setHint("Dose (e.g., 10 ml)");
                    break;

                case "Injection":
                    etStrength.setHint("Dose (e.g., 40 IU or 2 ml)");
                    break;

                case "Other":
                    etStrength.setHint("Dose (e.g., 1 puff, 5 drops)");
                    break;
            }

            checkFormCompletion();
        });
    }


    private void setupListeners() {

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFormCompletion();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etMedicine.addTextChangedListener(watcher);
        etStrength.addTextChangedListener(watcher);
        etProblem.addTextChangedListener(watcher);

        rgPrescribed.setOnCheckedChangeListener((group, checkedId) -> checkFormCompletion());
    }

    private void checkFormCompletion() {

        boolean isFormSelected = !actForm.getText().toString().trim().isEmpty();
        boolean isMedicineFilled = !etMedicine.getText().toString().trim().isEmpty();
        boolean isDoseFilled = !etStrength.getText().toString().trim().isEmpty();
        boolean isProblemFilled = !etProblem.getText().toString().trim().isEmpty();
        boolean isPrescribedSelected = rgPrescribed.getCheckedRadioButtonId() != -1;

        if (isFormSelected && isMedicineFilled && isDoseFilled
                && isPrescribedSelected) {
            btnAnalyze.setVisibility(View.VISIBLE);
        } else {
            btnAnalyze.setVisibility(View.GONE);
        }
    }
}
