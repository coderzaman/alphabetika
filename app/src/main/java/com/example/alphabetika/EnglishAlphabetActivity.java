package com.example.alphabetika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class EnglishAlphabetActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "EnglishPrefs";
    private static final String SELECTED_KEY = "SelectedTextView"; // Key for SharedPreferences
    private ImageView backbtn;
    private Map<Integer, Integer> audioMap;
    private MediaPlayer mediaPlayer;
    private int lastSelectedId = -1; // I
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_alphabet);

        // Set the title of the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Alphabet");
        }

        // Initialize the audio map
        audioMap = new HashMap<>();
        audioMap.put(R.id.kId, R.raw.english_a);
        audioMap.put(R.id.khId, R.raw.english_b);
        audioMap.put(R.id.gId, R.raw.english_c);
        audioMap.put(R.id.ghId, R.raw.english_d);
        audioMap.put(R.id.oaid, R.raw.english_e);
        audioMap.put(R.id.cId, R.raw.english_f);
        audioMap.put(R.id.chId, R.raw.english_g);
        audioMap.put(R.id.jId, R.raw.english_h);
        audioMap.put(R.id.jhId, R.raw.english_i);
        audioMap.put(R.id.eaId, R.raw.english_j);
        audioMap.put(R.id.tId, R.raw.english_k);
        audioMap.put(R.id.thId, R.raw.english_l);
        audioMap.put(R.id.dId, R.raw.english_m);
        audioMap.put(R.id.dhId, R.raw.english_n);
        audioMap.put(R.id.nhId, R.raw.english_o);
        audioMap.put(R.id.taId, R.raw.english_p);
        audioMap.put(R.id.tahId, R.raw.english_q);
        audioMap.put(R.id.daId, R.raw.english_r);
        audioMap.put(R.id.dahId, R.raw.english_s);
        audioMap.put(R.id.nId, R.raw.english_t);
        audioMap.put(R.id.pId, R.raw.english_u);
        audioMap.put(R.id.phId, R.raw.english_v);
        audioMap.put(R.id.bId, R.raw.english_w);
        audioMap.put(R.id.bhId, R.raw.english_x);
        audioMap.put(R.id.mId, R.raw.english_y);
        audioMap.put(R.id.ontoJId, R.raw.english_z);

        // Restore last selected TextView
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        lastSelectedId = preferences.getInt(SELECTED_KEY, -1);

        // Set up listeners for each TextView
        for (Map.Entry<Integer, Integer> entry : audioMap.entrySet()) {
            TextView textView = findViewById(entry.getKey());
            if (entry.getKey() == lastSelectedId) {
                textView.setBackgroundColor(Color.RED); // Set red background for the previously selected TextView
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAudio(entry.getValue(), textView, entry.getKey());
                }
            });
        }

        // Handle the back button
        backbtn = findViewById(R.id.backId);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });
    }

    private void playAudio(int audioResId, TextView textView, int selectedId) {
        // Release the current MediaPlayer if it exists
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Reset all button colors to default
        resetButtonColors();

        // Create and start MediaPlayer
        mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.start();

        // Change the background color of the clicked TextView
        textView.setBackgroundColor(Color.RED);

        // Save the currently selected TextView ID
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_KEY, selectedId);
        editor.apply();

        // Update the last selected ID
        lastSelectedId = selectedId;

        // Release MediaPlayer when audio playback is completed
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    private void resetButtonColors() {
        for (Integer key : audioMap.keySet()) {
            TextView textView = findViewById(key);
            textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // Reset to default color
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(EnglishAlphabetActivity.this, BornomalaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        // Release MediaPlayer when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
