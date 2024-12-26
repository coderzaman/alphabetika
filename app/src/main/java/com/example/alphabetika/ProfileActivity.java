package com.example.alphabetika;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewFullName, textViewEmail, textViewUsername;


    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Profile");
        }

        textViewFullName = findViewById(R.id.textViewFullName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewUsername = findViewById(R.id.textViewUsername);



        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Get the logged-in username
        String loggedInUsername = sessionManager.getUsername();


        // Retrieve user details from the database
        Cursor cursor = databaseHelper.getUserDetails(loggedInUsername);
        if (cursor != null && cursor.moveToFirst()) {
            String fullName = cursor.getString(0); // Full name
            String email = cursor.getString(1); // Email
            String username = cursor.getString(2); // Username

            textViewFullName.setText(fullName);
            textViewEmail.setText(email);
            textViewUsername.setText(username);

            cursor.close();
        }

    }
}
