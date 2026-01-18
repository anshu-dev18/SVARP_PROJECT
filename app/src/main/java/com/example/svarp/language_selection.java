package com.example.svarp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButton;

public class language_selection extends AppCompatActivity {

    private ConstraintLayout english, hindi, konkani;
    private ConstraintLayout selectedLanguage = null;
    private MaterialButton btnNext;
    private ObjectAnimator nudgeAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language);

        // Views
        english = findViewById(R.id.langEnglish);
        hindi = findViewById(R.id.langHindi);
        konkani = findViewById(R.id.langKonkani);
        btnNext = findViewById(R.id.btnNext);

        // Next button hidden initially
        btnNext.setVisibility(View.GONE);

        // Prepare nudge animation (DO NOT start yet)
        nudgeAnimator = ObjectAnimator.ofFloat(
                btnNext,
                "translationX",
                0f,
                12f
        );
        nudgeAnimator.setDuration(500);
        nudgeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        nudgeAnimator.setRepeatCount(ValueAnimator.INFINITE);

        // Language selection listeners
        english.setOnClickListener(v -> selectLanguage(english));
        hindi.setOnClickListener(v -> selectLanguage(hindi));
        konkani.setOnClickListener(v -> selectLanguage(konkani));

        // NEXT button navigation
        btnNext.setOnClickListener(v -> {
            // Stop animation cleanly
            if (nudgeAnimator != null) {
                nudgeAnimator.cancel();
            }
            btnNext.setTranslationX(0f);

            Intent intent = new Intent(language_selection.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });
    }

    private void selectLanguage(ConstraintLayout selectedView) {

        // Reset previous selection
        if (selectedLanguage != null) {
            selectedLanguage.setBackgroundResource(R.drawable.lang_box_default);
        }

        // Apply selected drawable (keeps shape + outline)
        selectedView.setBackgroundResource(R.drawable.lang_box_selected);
        selectedLanguage = selectedView;

        // Reveal Next button + start attention animation
        if (btnNext.getVisibility() != View.VISIBLE) {
            btnNext.setAlpha(0f);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start();

            nudgeAnimator.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nudgeAnimator != null) {
            nudgeAnimator.cancel();
        }
    }
}
