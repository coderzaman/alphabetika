package com.example.alphabetika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Dashboard");
        }

        // Button listeners
        findViewById(R.id.btnShoroborno).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShorobornoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnBenjonBorno).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, BenjonBornoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnEng).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EnglishAlphabetActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnNumber).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NumberActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnArabic).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ArabicActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnArabicNumerical).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ArabicNumberActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnShognka).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SongkaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnKhela).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, GameMainScreen.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu (add Sign Out button to ActionBar)
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ActionBar item clicks
        if (item.getItemId() == R.id.action_sign_out) {
            // Clear login state from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Clear all session data
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.alert);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage("Do you want to exit?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> finish());

        alertDialogBuilder.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}