package com.example.svarp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;
import java.util.Random;

public class Main_Screen extends AppCompatActivity {

    static final String PREFS_NAME = "notification_prefs";
    static final String KEY_TOGGLE = "reminder_enabled";
    static final String KEY_HOUR = "reminder_hour";
    static final String KEY_MINUTE = "reminder_minute";

    private SwitchMaterial toggle;
    private TextView txtTime, sub5;
    private SharedPreferences reminderPrefs;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler noticeHandler;
    private Runnable noticeRunnable;
    private int lastIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        reminderPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // ── Find views ────────────────────────────────────────────────────────────
        MaterialButton btnMic = findViewById(R.id.btn_mic);
        ImageView appSetting = findViewById(R.id.appSetting);
        ConstraintLayout cardSymptoms = findViewById(R.id.cardSymptoms);
        toggle = findViewById(R.id.toggle);
        txtTime = findViewById(R.id.time);
        sub5 = findViewById(R.id.sub5);

        // ── Request notification permission (Android 13+) ────────────────────────
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // ── Restore saved time & toggle ───────────────────────────────────────────
        txtTime.setText(formatTime(
                reminderPrefs.getInt(KEY_HOUR, 8),
                reminderPrefs.getInt(KEY_MINUTE, 0)));
        toggle.setChecked(reminderPrefs.getBoolean(KEY_TOGGLE, false));

        // ── Toggle listener ───────────────────────────────────────────────────────
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            reminderPrefs.edit().putBoolean(KEY_TOGGLE, isChecked).apply();
            if (isChecked) {
                scheduleReminder(
                        reminderPrefs.getInt(KEY_HOUR, 8),
                        reminderPrefs.getInt(KEY_MINUTE, 0));
                Toast.makeText(this, getString(R.string.reminder_enabled), Toast.LENGTH_SHORT).show();
            } else {
                cancelReminder();
                Toast.makeText(this, getString(R.string.reminder_disabled), Toast.LENGTH_SHORT).show();
            }
        });

        // ── Time picker ───────────────────────────────────────────────────────────
        txtTime.setOnClickListener(v -> {
            int h = reminderPrefs.getInt(KEY_HOUR, 8);
            int m = reminderPrefs.getInt(KEY_MINUTE, 0);
            new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                reminderPrefs.edit()
                        .putInt(KEY_HOUR, hourOfDay)
                        .putInt(KEY_MINUTE, minute1)
                        .apply();
                txtTime.setText(formatTime(hourOfDay, minute1));
                if (toggle.isChecked()) scheduleReminder(hourOfDay, minute1);
            }, h, m, false).show();
        });

        // ── Navigation ────────────────────────────────────────────────────────────
        btnMic.setOnClickListener(v -> startActivity(new Intent(this, TalkToSvarp.class)));
        appSetting.setOnClickListener(v -> startActivity(new Intent(this, setting.class)));
        cardSymptoms.setOnClickListener(v -> startActivity(new Intent(this, Select_Symptoms.class)));

        // ── Double back to exit ───────────────────────────────────────────────────
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finish();
                } else {
                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(Main_Screen.this,
                            getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
                    new Handler(Looper.getMainLooper()).postDelayed(
                            () -> doubleBackToExitPressedOnce = false, 2000);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startNoticeRotation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopNoticeRotation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopNoticeRotation();
    }

    // ── Schedule exact daily alarm ────────────────────────────────────────────────
    private void scheduleReminder(int hour, int minute) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (am.canScheduleExactAlarms())
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            else
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        }
    }

    // ── Cancel alarm ──────────────────────────────────────────────────────────────
    private void cancelReminder() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        am.cancel(pi);
    }

    // ── Format time to 12hr string ────────────────────────────────────────────────
    @SuppressLint("DefaultLocale")
    private String formatTime(int hour, int minute) {
        String period = hour >= 12 ? "PM" : "AM";
        int h12 = hour % 12;
        if (h12 == 0) h12 = 12;
        return String.format("%02d:%02d %s", h12, minute, period);
    }

    // ── Notice rotation ───────────────────────────────────────────────────────────
    private void startNoticeRotation() {
        stopNoticeRotation();
        String[] notices = getResources().getStringArray(R.array.health_notices);
        noticeHandler = new Handler(Looper.getMainLooper());
        noticeRunnable = new Runnable() {
            @Override
            public void run() {
                int randomIndex;
                do {
                    randomIndex = new Random().nextInt(notices.length);
                } while (randomIndex == lastIndex);
                lastIndex = randomIndex;
                sub5.setText(notices[randomIndex]);
                noticeHandler.postDelayed(this, 5000);
            }
        };
        noticeHandler.post(noticeRunnable);
    }

    private void stopNoticeRotation() {
        if (noticeHandler != null && noticeRunnable != null) {
            noticeHandler.removeCallbacks(noticeRunnable);
            noticeHandler = null;
            noticeRunnable = null;
        }
    }
}