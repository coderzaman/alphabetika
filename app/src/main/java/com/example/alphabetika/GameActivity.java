package com.example.alphabetika;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView setInputForGame, gameResultId, gameScoreCountId, secondCountId;
    private ArrayList<Integer> winPredict = new ArrayList<>();
    private Button btn0, btn1, btn2, btn3;
    private int locationOfCorrectAnswer, score, wrongScore, a, b, question;
    private MediaPlayer correctSound, wrongSound, bellSound;

    private static final String[] ALL_SUMMATION = {"+", "-", "*"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide(); // Hides the ActionBar
        }

        // Setup fullscreen mode
        setFullscreen();

        // Initialize views
        initializeViews();

        // Initialize sound resources
        initializeSounds();

        // Disable back button initially
        disableBackButton();

        // Start the game
        startGame();
    }

    private void setFullscreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initializeViews() {
        setInputForGame = findViewById(R.id.gameInputId);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        gameResultId = findViewById(R.id.gameResultId);
        gameScoreCountId = findViewById(R.id.gameScoreCountId);
        secondCountId = findViewById(R.id.secondCountId);
    }

    private void initializeSounds() {
        correctSound = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        wrongSound = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        bellSound = MediaPlayer.create(getApplicationContext(), R.raw.bell);
    }

    private void startGame() {
        score = 0;
        wrongScore = 0;
        question = 0;
        gameResultId.setText("Game is Running.....");
        gameResultId.setVisibility(View.VISIBLE);
        secondCountId.setText("30s");
        gameScoreCountId.setText(score + "/" + wrongScore);

        // Start the countdown timer
        startCountdownTimer();
        // Load the first question
        loadQuestion();
    }

    private void startCountdownTimer() {
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                secondCountId.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                gameResultId.setVisibility(View.INVISIBLE);
                if (question < 10) {
                    bellSound.start();
                    navigateToResult();
                }
            }
        }.start();
    }

    private void loadQuestion() {
        Random random = new Random();
        String operation = ALL_SUMMATION[random.nextInt(ALL_SUMMATION.length)];
        a = random.nextInt(8) + 1;
        b = random.nextInt(8) + 1;

        // Adjust values for subtraction
        if (operation.equals("-") && a <= b) {
            b = random.nextInt(8) + 1;
        }

        // Set question text
        setInputForGame.setText(a + " " + operation + " " + b);

        locationOfCorrectAnswer = random.nextInt(4);
        winPredict.clear();

        // Populate options (including correct answer)
        for (int i = 0; i < 4; i++) {
            int answer = (locationOfCorrectAnswer == i) ? getCorrectAnswer(operation) : getWrongAnswer(random, operation);
            winPredict.add(answer);
        }

        // Set button text
        btn0.setText(String.valueOf(winPredict.get(0)));
        btn1.setText(String.valueOf(winPredict.get(1)));
        btn2.setText(String.valueOf(winPredict.get(2)));
        btn3.setText(String.valueOf(winPredict.get(3)));
    }

    private int getCorrectAnswer(String operation) {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                return 0; // Default case for unsupported operation
        }
    }

    private int getWrongAnswer(Random random, String operation) {
        int wrongAnswer;
        do {
            wrongAnswer = random.nextInt(42);
            if (operation.equals("+")) {
                wrongAnswer = random.nextInt(20);
            } else if (operation.equals("-")) {
                while (wrongAnswer == a - b) {
                    wrongAnswer = random.nextInt(20);
                }
            } else if (operation.equals("*")) {
                wrongAnswer = random.nextInt(40) + 1;
                while (wrongAnswer == a * b) {
                    wrongAnswer = random.nextInt(40) + 1;
                }
            }
        } while (wrongAnswer == a + b || wrongAnswer == a - b || wrongAnswer == a * b);
        return wrongAnswer;
    }

    public void checkAnswer(View view) {
        String selectedAnswer = view.getTag().toString();
        boolean isCorrect = Integer.toString(locationOfCorrectAnswer).equals(selectedAnswer);

        // Handle answer result
        handleAnswerResult(isCorrect);

        // Update scores
        updateScore();

        // Load next question
        question++;
        if (question == 10) {
            navigateToResult();
        } else {
            loadQuestion();
        }
    }

    private void handleAnswerResult(boolean isCorrect) {
        if (isCorrect) {
            gameResultId.setBackgroundColor(getResources().getColor(R.color.green));
            correctSound.start();
            gameResultId.setText("Congrats! Now your Score is: " + (score + 1));
            score++;
        } else {
            gameResultId.setBackgroundColor(Color.RED);
            wrongSound.start();
            gameResultId.setText("Ops! Wrong Answer.");
            wrongScore++;
        }
    }

    private void updateScore() {
        gameScoreCountId.setText(score + "/" + wrongScore);
    }

    private void navigateToResult() {
        // Enable the back button once the game is finished
        enableBackButton();

        // Move to the result activity
        Intent intent = new Intent(GameActivity.this, MainResult.class);
        intent.putExtra("message", Integer.toString(score));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer(correctSound);
        releaseMediaPlayer(wrongSound);
        releaseMediaPlayer(bellSound);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable back button until the game ends
        if (question < 10) {
            // Do nothing to disable back button
        } else {
            super.onBackPressed(); // Allow back navigation after the game ends
        }
    }

    // Function to enable back button
    private void enableBackButton() {
        super.onBackPressed();
    }

    // Function to disable back button
    private void disableBackButton() {
        // Do nothing on back press to disable the back button
    }
}
