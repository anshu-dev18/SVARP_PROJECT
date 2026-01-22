package com.example.svarp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class talkTOsvarp extends AppCompatActivity {

    private final List<ChatMessage> chatList = new ArrayList<>();

    private EditText etMessage;
    private ImageButton btnMic, btnSend;
    private RecyclerView rvChat;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_talk_to_svarp);

        applyWindowInsets();   // 🔑 fixes keyboard issue
        initViews();
        setupRecyclerView();
        setupInputToggle();
        setupSendAction();
    }

    /**
     * IMPORTANT: Makes input box move with keyboard (Edge-to-Edge safe)
     */
    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    imeInsets.bottom   // THIS keeps input visible
            );
            return insets;
        });
    }

    private void initViews() {
        etMessage = findViewById(R.id.etMessage);
        btnMic = findViewById(R.id.btnMic);
        btnSend = findViewById(R.id.btnSend);
        rvChat = findViewById(R.id.rvChat);
    }

    private void setupRecyclerView() {
        adapter = new ChatAdapter(chatList);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);
    }

    /**
     * Mic <-> Send toggle while typing
     */
    private void setupInputToggle() {
        btnMic.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.GONE);

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean hasText = !s.toString().trim().isEmpty();
                btnSend.setVisibility(hasText ? View.VISIBLE : View.GONE);
                btnMic.setVisibility(hasText ? View.GONE : View.VISIBLE);

                if (!chatList.isEmpty()) {
                    rvChat.scrollToPosition(chatList.size() - 1);
                }
            }
        });
    }

    /**
     * Send button logic + AI greeting
     */
    private void setupSendAction() {
        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            if (message.isEmpty()) return;

            // USER message
            addMessage(message, true);

            // AI reply (basic intent logic)
            handleAiReply(message);

            etMessage.setText("");
        });
    }

    /**
     * Adds message to chat safely
     */
    private void addMessage(String text, boolean isUser) {
        chatList.add(new ChatMessage(text, isUser));
        adapter.notifyItemInserted(chatList.size() - 1);
        rvChat.scrollToPosition(chatList.size() - 1);
    }

    /**
     * Simple AI greeting logic
     */
    private void handleAiReply(String userMessage) {
        String msg = userMessage.toLowerCase().trim();

        if (msg.equals("hi") || msg.equals("hello")) {
            addMessage(
                    "Hello!\n— I'm SVARP! How may I assist you today?",
                    false
            );
        }
    }
}
