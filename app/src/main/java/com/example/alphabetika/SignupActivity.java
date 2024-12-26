package com.example.alphabetika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;




public class SignupActivity extends AppCompatActivity {
    private EditText editTextFullName, editTextEmail, editTextNewUsername, editTextNewPassword, editTextConfirmPassword;
    private Button buttonCreateAccount;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Sign Up");
        }

        databaseHelper = new DatabaseHelper(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String username = editTextNewUsername.getText().toString();
                String password = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = databaseHelper.addUser(fullName, email, username, password);
                if (result > 0) {
                    Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                    // Create session and navigate to HomeActivity
                    SessionManager sessionManager = new SessionManager(SignupActivity.this);
                    sessionManager.createLoginSession(username, email, fullName);

                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

