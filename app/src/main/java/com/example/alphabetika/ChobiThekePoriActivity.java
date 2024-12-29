package com.example.alphabetika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ChobiThekePoriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chobithekepori);

        // Check login state
        SharedPreferences sharedPreferences = getSharedPreferences("UserSessionPref", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Redirect to LoginActivity if not logged in
            Intent intent = new Intent(ChobiThekePoriActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ছবি থেকে পড়ি");
        }

        // Initialize button listeners
        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        findViewById(R.id.btnShoroborno).setOnClickListener(v -> startActivity(new Intent(ChobiThekePoriActivity.this, SentenceShorobornoActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            // Navigate to the Profile page
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
            return true;

        } else if (id == R.id.action_sign_out) {
            // Handle Sign Out logic
            signOutUser();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void signOutUser() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.signOut(this); // Use the sign-out method from SessionManager
    }




    private void navigateToMainActivity() {
        Intent intent = new Intent(ChobiThekePoriActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
