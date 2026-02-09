package com.example.svarp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class language_selection extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        recyclerView = findViewById(R.id.recyclerLanguages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> languages = Arrays.asList(
                getString(R.string.lang_english),
                getString(R.string.lang_hindi),
                getString(R.string.lang_konkani),
                getString(R.string.lang_marathi),
                getString(R.string.lang_tamil),
                getString(R.string.lang_telugu),
                getString(R.string.lang_bengali)
        );

        recyclerView.setAdapter(new LanguageAdapter(languages));
    }
}