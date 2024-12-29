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

public class ShorobornoActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "ShorbornoPrefs";
    private static final String SELECTED_KEY = "SelectedTextView"; // Key for SharedPreferences
    private ImageView backbtn;
    private Map<Integer, Integer> audioMap;
    private MediaPlayer mediaPlayer;
    private int lastSelectedId = -1; // ID of the last selected TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoroborno);

        // Set the title of the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("স্বরবর্ণ");
        }

        // Initialize the audio map
        audioMap = new HashMap<>();
        audioMap.put(R.id.soro, R.raw.soro);
        audioMap.put(R.id.sora, R.raw.sora);
        audioMap.put(R.id.rose, R.raw.rose);
        audioMap.put(R.id.dirge, R.raw.dirgoe);
        audioMap.put(R.id.roso, R.raw.roso);
        audioMap.put(R.id.dirgo, R.raw.dirgo);
        audioMap.put(R.id.ri, R.raw.ri);
        audioMap.put(R.id.e, R.raw.e);
        audioMap.put(R.id.oi, R.raw.oi);
        audioMap.put(R.id.o, R.raw.o);
        audioMap.put(R.id.ao, R.raw.ow);

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
        backbtn = findViewById(R.id.backIds);
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
        Intent intent = new Intent(ShorobornoActivity.this, BornomalaActivity.class);
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
