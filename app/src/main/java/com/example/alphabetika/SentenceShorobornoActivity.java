package com.example.alphabetika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SentenceShorobornoActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "ShorbornoSenPrefs";
    private static final String SELECTED_KEY = "SelectedImageView"; // Key for SharedPreferences
    private ImageView backbtn;
    private Map<Integer, Integer> audioMap;
    private MediaPlayer mediaPlayer;
    private int lastSelectedId = -1; // ID of the last selected ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_shorborno);

        // Set the title of the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("স্বরবর্ণ");
        }

        // Initialize the audio map
        audioMap = new HashMap<>();
        audioMap.put(R.id.sen_sor_01, R.raw.sor_sen01);
        audioMap.put(R.id.sen_sor_02, R.raw.sor_sen02);
        audioMap.put(R.id.sen_sor_03, R.raw.sor_sen03);
        audioMap.put(R.id.sen_sor_04, R.raw.sor_sen04);
        audioMap.put(R.id.sen_sor_05, R.raw.sor_sen05);
        audioMap.put(R.id.sen_sor_06, R.raw.sor_sen06);
        audioMap.put(R.id.sen_sor_07, R.raw.sor_sen07);
        audioMap.put(R.id.sen_sor_08, R.raw.sor_sen08);
        audioMap.put(R.id.sen_sor_09, R.raw.sor_sen09);
        audioMap.put(R.id.sen_sor_10, R.raw.sor_sen10);
        audioMap.put(R.id.sen_sor_11, R.raw.sor_sen11);

        // Restore last selected ImageView
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        lastSelectedId = preferences.getInt(SELECTED_KEY, -1);

        // Set up listeners for each ImageView
        for (Map.Entry<Integer, Integer> entry : audioMap.entrySet()) {
            ImageView imageView = findViewById(entry.getKey());
            if (entry.getKey() == lastSelectedId) {
                setBorder(imageView, true); // Add border for the previously selected ImageView
            } else {
                setBorder(imageView, false); // Ensure no border for others
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAudio(entry.getValue(), imageView, entry.getKey());
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

    private void playAudio(int audioResId, ImageView imageView, int selectedId) {
        // Release the current MediaPlayer if it exists
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Reset all button borders
        resetButtonBorders();

        // Create and start MediaPlayer
        mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.start();

        // Add a border to the clicked ImageView
        setBorder(imageView, true);

        // Save the currently selected ImageView ID
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

    private void resetButtonBorders() {
        for (Integer key : audioMap.keySet()) {
            ImageView imageView = findViewById(key);
            setBorder(imageView, false); // Remove border for all ImageViews
        }
    }

    private void setBorder(ImageView imageView, boolean isSelected) {
        GradientDrawable border = new GradientDrawable();
        if (isSelected) {
            border.setStroke(10, getResources().getColor(R.color.colorPrimary)); // Add border
        } else {
            border.setStroke(0, Color.TRANSPARENT); // Remove border
        }
        border.setCornerRadius(8); // Optional: Add rounded corners
        imageView.setBackground(border);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SentenceShorobornoActivity.this, ChobiThekePoriActivity.class);
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
