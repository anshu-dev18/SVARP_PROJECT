package com.example.svarp;

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

    // ── Notification prefs ─────────────────────────────
    static final String PREFS_NAME = "notification_prefs";
    static final String KEY_TOGGLE = "reminder_enabled";
    static final String KEY_HOUR = "reminder_hour";
    static final String KEY_MINUTE = "reminder_minute";

    private SwitchMaterial toggle;
    private TextView txtTime;
    private SharedPreferences reminderPrefs;

    // ── English notices ─────────────────────────────────
    private final String[] noticesEn = {
            "Staying hydrated helps reduce headache and fatigue. Aim for 8 glasses of water daily.",
            "Getting 7–9 hours of sleep each night helps your immune system stay strong.",
            "A 10-minute walk can improve your mood and reduce stress significantly.",
            "Eating a variety of colourful fruits and vegetables provides essential vitamins.",
            "Washing hands for at least 20 seconds prevents the spread of most infections.",
            "Deep breathing for 5 minutes can lower your heart rate and reduce anxiety.",
            "Limiting screen time before bed improves sleep quality and reduces eye strain.",
            "Regular meals maintain stable blood sugar and help you stay energised all day.",
            "Sunlight exposure for 15–20 minutes daily supports healthy vitamin D levels.",
            "Stretching for a few minutes after waking up reduces muscle stiffness.",
            "Reducing added sugar intake lowers your risk of diabetes and heart disease.",
            "Spending time outdoors in nature has proven benefits for mental wellbeing.",
            "Listening to music you enjoy can reduce cortisol — your body's stress hormone.",
            "Keeping a symptom journal helps you spot patterns and share them with your doctor.",
            "Laughing genuinely boosts your immune system and relieves physical tension."
    };

    private final String[] noticesHi = {
            "हाइड्रेटेड रहने से सिरदर्द और थकान कम होती है। रोज़ 8 गिलास पानी पिएं।",
            "रात को 7–9 घंटे की नींद आपकी रोग प्रतिरोधक क्षमता को मज़बूत रखती है।",
            "10 मिनट की सैर आपके मूड को बेहतर बना सकती है और तनाव कम कर सकती है।",
            "रंग-बिरंगे फल और सब्ज़ियां खाने से ज़रूरी विटामिन मिलते हैं।",
            "20 सेकंड तक हाथ धोने से अधिकांश संक्रमणों का प्रसार रुकता है।",
            "5 मिनट की गहरी सांस लेने से दिल की धड़कन धीमी होती है और चिंता कम होती है।",
            "सोने से पहले स्क्रीन कम देखने से नींद की गुणवत्ता बेहतर होती है।",
            "नियमित खाना खाने से ब्लड शुगर स्थिर रहती है और दिनभर ऊर्जा बनी रहती है।",
            "रोज़ 15–20 मिनट धूप लेने से शरीर में विटामिन डी का स्तर बना रहता है।",
            "सुबह उठकर कुछ मिनट स्ट्रेचिंग करने से मांसपेशियों की अकड़न दूर होती है।",
            "अतिरिक्त चीनी कम खाने से मधुमेह और हृदय रोग का खतरा घटता है।",
            "प्रकृति में समय बिताने से मानसिक स्वास्थ्य पर सिद्ध लाभ होते हैं।",
            "पसंदीदा संगीत सुनने से तनाव हार्मोन कोर्टिसोल कम होता है।",
            "लक्षणों की डायरी रखने से पैटर्न पहचानने और डॉक्टर को बताने में मदद मिलती है।",
            "सच्ची हंसी आपकी रोग प्रतिरोधक क्षमता बढ़ाती है और शारीरिक तनाव दूर करती है।"
    };

    private boolean doubleBackToExitPressedOnce = false;
    private Handler noticeHandler;
    private Runnable noticeRunnable;
    private int lastIndex = -1;

    private TextView head1, sub1, head2, sub2, head5, sub5, syncText;
    private MaterialButton btnMic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        reminderPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // ── Find views ────────────────────────────────────────────────────────────
        btnMic        = findViewById(R.id.btn_mic);
        ImageView appSetting          = findViewById(R.id.appSetting);
        ConstraintLayout cardSymptoms = findViewById(R.id.cardSymptoms);
        toggle        = findViewById(R.id.toggle);
        txtTime       = findViewById(R.id.time);
        head1         = findViewById(R.id.head1);
        sub1          = findViewById(R.id.sub1);
        head2         = findViewById(R.id.head2);
        sub2          = findViewById(R.id.sub2);
        head5         = findViewById(R.id.head5);
        sub5          = findViewById(R.id.sub5);
        syncText      = findViewById(R.id.sync);

        // ── Request notification permission (Android 13+) ────────────────────────
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // ── Restore saved time ────────────────────────────────────────────────────
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
                boolean isHindi = isHindiSelected();
                Toast.makeText(this,
                        isHindi ? "रिमाइंडर चालू किया गया" : "Reminder Enabled",
                        Toast.LENGTH_SHORT).show();
            } else {
                cancelReminder();
                boolean isHindi = isHindiSelected();
                Toast.makeText(this,
                        isHindi ? "रिमाइंडर बंद किया गया" : "Reminder Disabled",
                        Toast.LENGTH_SHORT).show();
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
                if (toggle.isChecked()) {
                    scheduleReminder(hourOfDay, minute1);
                }
            }, h, m, false).show();
        });

        // ── Navigation ────────────────────────────────────────────────────────────
        btnMic.setOnClickListener(v ->
                startActivity(new Intent(this, TalkToSvarp.class)));

        appSetting.setOnClickListener(v ->
                startActivity(new Intent(this, setting.class)));

        cardSymptoms.setOnClickListener(v ->
                startActivity(new Intent(this, Select_Symptoms.class)));

        // ── Double back to exit ───────────────────────────────────────────────────
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        boolean isHindi = isHindiSelected();
                        if (doubleBackToExitPressedOnce) {
                            finish();
                        } else {
                            doubleBackToExitPressedOnce = true;
                            String msg = isHindi
                                    ? "बाहर निकलने के लिए फिर से दबाएं"
                                    : "Press back again to exit";
                            Toast.makeText(Main_Screen.this, msg, Toast.LENGTH_SHORT).show();
                            new Handler(Looper.getMainLooper()).postDelayed(
                                    () -> doubleBackToExitPressedOnce = false, 2000);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyLanguage();
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
        PendingIntent pi = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (am.canScheduleExactAlarms()) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            } else {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            }
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        }
    }

    // ── Cancel alarm ──────────────────────────────────────────────────────────────
    private void cancelReminder() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        am.cancel(pi);
    }

    // ── Format time to 12hr string ────────────────────────────────────────────────
    private String formatTime(int hour, int minute) {
        String period = hour >= 12 ? "PM" : "AM";
        int h12 = hour % 12;
        if (h12 == 0) h12 = 12;
        return String.format("%02d:%02d %s", h12, minute, period);
    }

    // ── Read language preference ──────────────────────────────────────────────────
    private boolean isHindiSelected() {
        SharedPreferences prefs = getSharedPreferences(
                LanguageAdapter.PREFS_NAME, MODE_PRIVATE);
        return LanguageAdapter.LANG_HINDI.equals(
                prefs.getString(LanguageAdapter.KEY_LANGUAGE, LanguageAdapter.LANG_ENGLISH));
    }

    // ── Apply language to all visible text ───────────────────────────────────────
    private void applyLanguage() {
        boolean isHindi = isHindiSelected();
        if (isHindi) {
            head1.setText("मैं कैसे मदद कर सकता हूँ?");
            sub1.setText("जल्दी आकलन के लिए अपने लक्षण बताएं");
            btnMic.setText("वॉइस चैट शुरू करें");
            head2.setText("लक्षण चुनें");
            sub2.setText("अपने मौजूदा लक्षण चुनने के लिए टैप करें");
            head5.setText("सार्वजनिक स्वास्थ्य सूचना");
            syncText.setText("अंतिम सिंक: 8:22 PM");
        } else {
            head1.setText(getString(R.string.greeting));
            sub1.setText(getString(R.string.tell_symptoms));
            btnMic.setText(getString(R.string.talk));
            head2.setText(getString(R.string.select_Symptoms));
            sub2.setText(getString(R.string.choose_symptoms));
            head5.setText(getString(R.string.notice));
            syncText.setText(getString(R.string.sync));
        }
    }

    // ── Notice rotation ───────────────────────────────────────────────────────────
    private void startNoticeRotation() {
        stopNoticeRotation();
        noticeHandler = new Handler(Looper.getMainLooper());
        noticeRunnable = new Runnable() {
            @Override
            public void run() {
                boolean isHindi = isHindiSelected();
                String[] notices = isHindi ? noticesHi : noticesEn;
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