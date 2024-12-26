package com.example.alphabetika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSessionPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "userEmail";
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_USERNAME = "username";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Create login session
    public void createLoginSession(String username, String email, String fullName) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.apply(); // Use apply() for asynchronous saving
    }

    // Check login status
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Get stored session data
    public String getUsername() {
        return pref.getString(KEY_USERNAME, ""); // Return empty string if not found
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, ""); // Return empty string if not found
    }

    public String getFullName() {
        return pref.getString(KEY_FULL_NAME, ""); // Return empty string if not found
    }

    // Check if session is valid
    public boolean isValidSession() {
        return isLoggedIn() && !getUsername().isEmpty() && !getUserEmail().isEmpty();
    }

    // Logout and redirect to LoginActivity
    public void signOut(Context currentContext) {
        editor.clear();
        editor.apply(); // Use apply() for asynchronous clearing

        Intent intent = new Intent(currentContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentContext.startActivity(intent);
    }
}
