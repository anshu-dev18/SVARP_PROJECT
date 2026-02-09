package com.example.svarp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnboardingFragment extends Fragment {

    private static final String ARG_IMAGE = "arg_image";
    private static final String ARG_HEADING = "arg_heading";
    private static final String ARG_TAGLINE = "arg_tagline";
    private static final String ARG_LAST = "arg_last";

    public static OnboardingFragment newInstance(
            int imageRes,
            int headingRes,
            int taglineRes,
            boolean isLast
    ) {
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imageRes);
        args.putInt(ARG_HEADING, headingRes);
        args.putInt(ARG_TAGLINE, taglineRes);
        args.putBoolean(ARG_LAST, isLast);

        OnboardingFragment fragment = new OnboardingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

        ImageView icon = view.findViewById(R.id.imgIcon);
        TextView heading = view.findViewById(R.id.txtHeading);
        TextView tagline = view.findViewById(R.id.txtTagline);
        Button skip = view.findViewById(R.id.btnSkip);
        Button next = view.findViewById(R.id.btnNext);

        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException(
                    "OnboardingFragment must be created via newInstance()"
            );
        }

        boolean isLast = args.getBoolean(ARG_LAST, false);

        icon.setImageResource(args.getInt(ARG_IMAGE));
        heading.setText(args.getInt(ARG_HEADING));
        tagline.setText(args.getInt(ARG_TAGLINE));

        OnboardingActivity host = getHostActivity();
        if (host == null) {
            return view; // Fragment detached or invalid host
        }

        if (isLast) {
            skip.setVisibility(View.GONE);
            next.setText(R.string.get_started);
        }

        skip.setOnClickListener(v -> host.finishOnboarding());

        next.setOnClickListener(v -> {
            if (isLast) {
                host.finishOnboarding();
            } else {
                host.nextPage();
            }
        });

        return view;
    }

    @Nullable
    private OnboardingActivity getHostActivity() {
        if (getActivity() instanceof OnboardingActivity) {
            return (OnboardingActivity) getActivity();
        }
        return null;
    }
}