package com.example.alphabetika;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 2; // Updated version to 2 for schema changes

    // User Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Create table SQL
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FULL_NAME + " TEXT NOT NULL,"
                    + COLUMN_EMAIL + " TEXT NOT NULL,"
                    + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL,"
                    + COLUMN_PASSWORD + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // Method to add a new user with full name and email
    public long addUser(String fullName, String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        // Check if the username or email already exists
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{username, email},
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            return -1; // Username or email already exists
        }

        cursor.close();
        return db.insert(TABLE_USERS, null, values);
    }

    // Method to check user credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount > 0;
    }

    // Method to retrieve user details (optional for displaying user profile)
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, new String[]{COLUMN_FULL_NAME, COLUMN_EMAIL, COLUMN_USERNAME},
                COLUMN_USERNAME + " = ?", new String[]{username},
                null, null, null);
    }
}
