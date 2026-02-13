package com.example.svarp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 1500;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_ONBOARDING_DONE = "onboarding_done";
    private static final String KEY_LANGUAGE = "selected_language";

    private Handler handler;
    private Runnable splashRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean onboardingDone =
                prefs.getBoolean(KEY_ONBOARDING_DONE, false);

        String selectedLanguage =
                prefs.getString(KEY_LANGUAGE, null);

        handler = new Handler(Looper.getMainLooper());

        splashRunnable = () -> {

            Intent intent;

            if (!onboardingDone) {
                intent = new Intent(this, OnboardingActivity.class);
            } else if (selectedLanguage == null) {
                intent = new Intent(this, language_selection.class);
            } else {
                intent = new Intent(this, Main_Screen.class);
            }

            startActivity(intent);
            finish();
        };

        handler.postDelayed(splashRunnable, SPLASH_DELAY_MS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && splashRunnable != null) {
            handler.removeCallbacks(splashRunnable);
        }
    }
}
