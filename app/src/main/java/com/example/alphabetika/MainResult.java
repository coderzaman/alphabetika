package com.example.alphabetika;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainResult extends AppCompatActivity {

    private ProgressBar brainProgress;
    private TextView brainScore, brainPercentage;
    private Button gotoGameAgain;
    private int score;
    private Button goHome;
    private static final int MAX_PROGRESS = 100; // Max progress for progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_result);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Game Score");
        }
        // Initialize views
        brainProgress = findViewById(R.id.brainProgress);
        brainScore = findViewById(R.id.brainScore);
        gotoGameAgain = findViewById(R.id.gotoGameAgain);
        brainPercentage = findViewById(R.id.percentageId);
        goHome = findViewById(R.id.goHome);
        // Get score from previous activity
        Bundle bundle = getIntent().getExtras();
        String message = bundle != null ? bundle.getString("message") : "0";
        brainScore.setText(message);
        score = Integer.parseInt(message);

        // Setup the progress bar and text based on score
        setupResultUI();

        // Start progress bar animation in a separate thread
        startProgressBarAnimation();

        // Handle the "Play Again" button click
        gotoGameAgain.setOnClickListener(v -> {
            Intent intent = new Intent(MainResult.this, GameActivity.class);
            startActivity(intent);
            finish();
        });
        goHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainResult.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupResultUI() {
        // Calculate percentage
        int percentage = score * 10;
        brainPercentage.setText(percentage + "%");

        // Set color and message based on score
        int color;
        String message;

        if (score <= 2) {
            color = Color.RED;
            message = "Ouch! Your Brain Score is: ";
        } else if (score <= 5) {
            color = Color.BLUE;
            message = "Not Bad! Your Brain Score is: ";
        } else if (score <= 7) {
            color = Color.GREEN;
            message = "Good! Your Brain Score is: ";
        } else {
            color = Color.YELLOW;
            message = "OW! Your Brain Score is: ";
        }

        brainProgress.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        brainScore.setText(message + score * 10 + "%");
        brainScore.setTextColor(color);
        brainPercentage.setTextColor(color);
    }

    private void startProgressBarAnimation() {
        // Use a Handler to update the progress bar on the UI thread
        final Handler handler = new Handler();
        new Thread(() -> {
            for (int progress = 0; progress <= score * 10; progress += 10) {
                try {
                    Thread.sleep(200); // Simulate a delay for the progress animation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update the progress bar on the UI thread
                final int finalProgress = progress;
                handler.post(() -> brainProgress.setProgress(finalProgress));
            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainResult.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Ensure the MainResult activity is closed
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(MainResult.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
