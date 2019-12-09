package com.example.ondrej.pacman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pacman.db";

    private static final String SQL_CREATE_DATABASE =
        "CREATE TABLE " + ScoreEntry.TABLE_NAME + "(" +
        ScoreEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
        ScoreEntry.COLUMN_NAME + " TEXT," +
        ScoreEntry.COLUMN_LEVEL + " INTEGER," +
        ScoreEntry.COLUMN_SCORE + " INTEGER)";

    private static class ScoreEntry {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_SCORE = "score";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME);
        onCreate(db);*/
    }

    public ArrayList<HighScore> getScoresByLevelId(int leveId) {
        ArrayList<HighScore> highScores = new ArrayList<>();
        String[] projection = {
                DatabaseHelper.ScoreEntry.COLUMN_NAME,
                DatabaseHelper.ScoreEntry.COLUMN_SCORE,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseHelper.ScoreEntry.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { String.valueOf(leveId) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DatabaseHelper.ScoreEntry.COLUMN_SCORE + " DESC";

        Cursor cursor = getWritableDatabase().query(
            DatabaseHelper.ScoreEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        );

        int position = 1;
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ScoreEntry.COLUMN_NAME));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ScoreEntry.COLUMN_SCORE));
            highScores.add(new HighScore(name, position++, score));
        }
        cursor.close();

        return highScores;
    }

    public void addScore(String name, int score, int levelId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ScoreEntry.COLUMN_NAME, name);
        contentValues.put(DatabaseHelper.ScoreEntry.COLUMN_LEVEL, levelId);
        contentValues.put(DatabaseHelper.ScoreEntry.COLUMN_SCORE, score);

        getWritableDatabase().insert(DatabaseHelper.ScoreEntry.TABLE_NAME, null, contentValues);
    }
}
