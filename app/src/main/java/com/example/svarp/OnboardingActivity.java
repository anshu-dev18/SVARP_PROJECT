package com.example.svarp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

public class OnboardingActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_ONBOARDING_DONE = "onboarding_done";
    private ViewPager2 viewPager;
    private ConstraintLayout layoutDots;
    private Flow dotsFlow;
    private OnboardingPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        layoutDots = findViewById(R.id.layoutDots);

        adapter = new OnboardingPagerAdapter(this);
        viewPager.setAdapter(adapter);

        setupDots(adapter.getItemCount());
        updateDots(0);

        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        updateDots(position);
                    }
                }
        );

        if (savedInstanceState != null) {
            viewPager.setCurrentItem(
                    savedInstanceState.getInt("current_page", 0),
                    false
            );
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_page", viewPager.getCurrentItem());
    }

    public void nextPage() {
        int current = viewPager.getCurrentItem();
        if (current < adapter.getItemCount() - 1) {
            viewPager.setCurrentItem(current + 1, true);
        }
    }

    public void finishOnboarding() {

        // Mark onboarding as completed
        SharedPreferences prefs =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        prefs.edit()
                .putBoolean(KEY_ONBOARDING_DONE, true)
                .apply();

        // Move to language selection
        Intent intent = new Intent(this, language_selection.class);
        startActivity(intent);
        finish();
    }

    private void setupDots(int count) {
        layoutDots.removeAllViews();

        dotsFlow = new Flow(this);
        dotsFlow.setId(View.generateViewId());
        dotsFlow.setWrapMode(Flow.WRAP_NONE);
        dotsFlow.setHorizontalStyle(Flow.CHAIN_PACKED);
        dotsFlow.setHorizontalGap(dpToPx());

        ConstraintLayout.LayoutParams flowParams =
                new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );

        dotsFlow.setLayoutParams(flowParams);
        layoutDots.addView(dotsFlow);

        int size = dpToPx();
        int[] dotIds = new int[count];

        for (int i = 0; i < count; i++) {
            View dot = new View(this);
            dot.setId(View.generateViewId());

            ConstraintLayout.LayoutParams params =
                    new ConstraintLayout.LayoutParams(size, size);
            dot.setLayoutParams(params);

            dot.setBackground(
                    ContextCompat.getDrawable(this, R.drawable.dot_inactive)
            );

            layoutDots.addView(dot);
            dotIds[i] = dot.getId();
        }

        dotsFlow.setReferencedIds(dotIds);
    }

    private void updateDots(int currentPage) {
        int[] ids = dotsFlow.getReferencedIds();

        for (int i = 0; i < ids.length; i++) {
            View dot = layoutDots.findViewById(ids[i]);

            dot.setBackground(
                    ContextCompat.getDrawable(
                            this,
                            i == currentPage
                                    ? R.drawable.dot_active
                                    : R.drawable.dot_inactive
                    )
            );
        }
    }

    private int dpToPx() {
        return Math.round(
                8 * getResources().getDisplayMetrics().density
        );
    }
}