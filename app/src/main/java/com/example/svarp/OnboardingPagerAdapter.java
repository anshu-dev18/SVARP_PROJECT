package com.example.svarp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OnboardingPagerAdapter extends FragmentStateAdapter {

    private static final Page[] PAGES = new Page[]{
            new Page(
                    R.drawable.medical,
                    R.string.onboarding_H1,
                    R.string.onboarding_T1
            ),
            new Page(
                    R.drawable.chat__bot,
                    R.string.onboarding_H2,
                    R.string.onboarding_T2
            ),
            new Page(
                    R.drawable.checklist,
                    R.string.onboarding_H3,
                    R.string.onboarding_T3
            )
    };

    public OnboardingPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < 0 || position >= PAGES.length) {
            throw new IllegalStateException(
                    "Invalid onboarding page index: " + position
            );
        }

        Page page = PAGES[position];
        boolean isLast = position == PAGES.length - 1;

        return OnboardingFragment.newInstance(
                page.imageRes,
                page.headingRes,
                page.taglineRes,
                isLast
        );
    }

    @Override
    public int getItemCount() {
        return PAGES.length;
    }

    private static final class Page {
        final int imageRes;
        final int headingRes;
        final int taglineRes;

        Page(int imageRes, int headingRes, int taglineRes) {
            this.imageRes = imageRes;
            this.headingRes = headingRes;
            this.taglineRes = taglineRes;
        }
    }
}