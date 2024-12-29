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
public class BenjonBornoActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "BenjonPrefs";
    private static final String SELECTED_KEY = "SelectedTextView"; // Key for SharedPreferences
    private ImageView backbtn;
    private Map<Integer, Integer> audioMap;
    private MediaPlayer mediaPlayer;
    private int lastSelectedId = -1; // I

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benjonborno);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ব্যঞ্জনবর্ণ");
        }

        // Initialize the audio map
        audioMap = new HashMap<>();
        audioMap = new HashMap<>();
        audioMap.put(R.id.kId, R.raw.k);
        audioMap.put(R.id.khId, R.raw.kh);
        audioMap.put(R.id.gId, R.raw.g);
        audioMap.put(R.id.ghId, R.raw.gh);
        audioMap.put(R.id.oaid, R.raw.oa);
        audioMap.put(R.id.cId, R.raw.c);
        audioMap.put(R.id.chId, R.raw.ch);
        audioMap.put(R.id.jId, R.raw.j);
        audioMap.put(R.id.jhId, R.raw.jh);
        audioMap.put(R.id.eaId, R.raw.ea);
        audioMap.put(R.id.tId, R.raw.t);
        audioMap.put(R.id.thId, R.raw.th);
        audioMap.put(R.id.dId, R.raw.d);
        audioMap.put(R.id.dhId, R.raw.dh);
        audioMap.put(R.id.nhId, R.raw.nh);
        audioMap.put(R.id.taId, R.raw.ta);
        audioMap.put(R.id.tahId, R.raw.tah);
        audioMap.put(R.id.daId, R.raw.da);
        audioMap.put(R.id.dahId, R.raw.dah);
        audioMap.put(R.id.nId, R.raw.n);
        audioMap.put(R.id.pId, R.raw.p);
        audioMap.put(R.id.phId, R.raw.ph);
        audioMap.put(R.id.bId, R.raw.b);
        audioMap.put(R.id.bhId, R.raw.bh);
        audioMap.put(R.id.mId, R.raw.m);
        audioMap.put(R.id.ontoJId, R.raw.ontoj);
        audioMap.put(R.id.rId, R.raw.r);
        audioMap.put(R.id.lId, R.raw.l);
        audioMap.put(R.id.shId, R.raw.sh);
        audioMap.put(R.id.morshId, R.raw.mshid);
        audioMap.put(R.id.donshoId, R.raw.s);
        audioMap.put(R.id.hId, R.raw.h);
        audioMap.put(R.id.dorId, R.raw.dor);
        audioMap.put(R.id.dorrId, R.raw.dorr);
        audioMap.put(R.id.onaId, R.raw.ona);
        audioMap.put(R.id.honId, R.raw.kondota);
        audioMap.put(R.id.onusId, R.raw.onus);
        audioMap.put(R.id.bisId, R.raw.bisg);
        audioMap.put(R.id.condroId, R.raw.conb);
        // Add the rest of your mappings here...

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
        Intent intent = new Intent(BenjonBornoActivity.this, BornomalaActivity.class);
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
