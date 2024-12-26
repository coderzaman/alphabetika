package com.example.alphabetika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alphabetika.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check login state
        SharedPreferences sharedPreferences = getSharedPreferences("UserSessionPref", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Redirect to LoginActivity if not logged in
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        // Set ActionBar title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Dashboard");
        }

        // Initialize button listeners
        initializeButtonListeners();
    }

    private void initializeButtonListeners() {
        findViewById(R.id.btnShoroborno).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ShorobornoActivity.class)));
        findViewById(R.id.btnBenjonBorno).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, BenjonBornoActivity.class)));
        findViewById(R.id.btnEng).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, EnglishAlphabetActivity.class)));
        findViewById(R.id.btnNumber).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NumberActivity.class)));
        findViewById(R.id.btnArabic).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ArabicActivity.class)));
        findViewById(R.id.btnArabicNumerical).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ArabicNumberActivity.class)));
        findViewById(R.id.btnShognka).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SongkaActivity.class)));
        findViewById(R.id.btnKhela).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, GameMainScreen.class)));
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
}
