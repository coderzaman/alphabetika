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

public class ArabicActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "ArabicPrefs";
    private static final String SELECTED_KEY = "SelectedTextView"; // Key for SharedPreferences
    private ImageView backbtn;
    private Map<Integer, Integer> audioMap;
    private MediaPlayer mediaPlayer;
    private int lastSelectedId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arabic);

        // Safe way to set title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("العربية");
        }

        // Initialize the audio map
        audioMap = new HashMap<>();
        audioMap.put(R.id.three, R.raw.arabic_03);
        audioMap.put(R.id.tow, R.raw.arabic_02);
        audioMap.put(R.id.one, R.raw.arabic_01);
        audioMap.put(R.id.six, R.raw.arabic_6);
        audioMap.put(R.id.five, R.raw.arabic_5);
        audioMap.put(R.id.four, R.raw.arabic_4);
        audioMap.put(R.id.nine, R.raw.arabic_9);
        audioMap.put(R.id.eight, R.raw.arabic_8);
        audioMap.put(R.id.seven, R.raw.arabic_7);
        audioMap.put(R.id.twelve, R.raw.arabic_12);
        audioMap.put(R.id.eleven, R.raw.arabic_11);
        audioMap.put(R.id.ten, R.raw.arabic_10);
        audioMap.put(R.id.fifteen, R.raw.arabic_15);
        audioMap.put(R.id.fourteen, R.raw.arabic_14);
        audioMap.put(R.id.thirteen, R.raw.arabic_13);
        audioMap.put(R.id.eighteen, R.raw.arabic_18);
        audioMap.put(R.id.seventeen, R.raw.arabic_17);
        audioMap.put(R.id.sixteen, R.raw.arabic_16);
        audioMap.put(R.id.twentyone, R.raw.arabic_21);
        audioMap.put(R.id.twenty, R.raw.arabic_20);
        audioMap.put(R.id.nineteen, R.raw.arabic_19);
        audioMap.put(R.id.twentyfour, R.raw.arabic_24);
        audioMap.put(R.id.twentythree, R.raw.arabic_23);
        audioMap.put(R.id.twentytwo, R.raw.arabic_22);
        audioMap.put(R.id.twentyseven, R.raw.arabic_27);
        audioMap.put(R.id.twentysix, R.raw.arabic_26);
        audioMap.put(R.id.twentyfive, R.raw.arabic_25);
        audioMap.put(R.id.twentyeight, R.raw.arabic_28);

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
        Intent intent = new Intent(ArabicActivity.this, BornomalaActivity.class);
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
