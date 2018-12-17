package com.androidproject.dailynotesandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME  = "dbNotes";
    private static final int DB_VERSION = 1;
    private static final String TAG = DBHelper.class.getCanonicalName();

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate Called");

        String subjectTable = "CREATE TABLE " + DBSubject.TABLE_SUBJECT + "(" +
                 DBSubject.SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 DBSubject.SUBJECT_NAME + " TEXT default null)";

        sqLiteDatabase.execSQL(subjectTable);

        Log.d(TAG, "onCreate Success for SubjectTable");

        String noteTable = "CREATE TABLE " + DBNote.TABLE_NOTE + "("
                + DBNote.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBNote.SUBJECT_NAME + " TEXT NOT NULL,"
                + DBNote.NOTE_TITLE + " TEXT default null,"
                + DBNote.NOTE_CONTENT + " TEXT default null,"
                + DBNote.AUDIO + " TEXT default null,"
                + DBNote.DATETIME + " TEXT default null,"
                + DBNote.LATITUDE + " DOUBLE default null,"
                + DBNote.LONGITUDE + " DOUBLE default null,"
//                + DBNote.LONGITUDE + " DOUBLE default null,"
//                + DBNote.LONGITUDE + " DOUBLE default null,"


                + DBNote.IMAGE_ID + " INTEGER default null)";

        sqLiteDatabase.execSQL(noteTable);

        Log.d(TAG, "onCreate Success for NoteTable");

        String imageTable = "CREATE TABLE " + DBImage.TABLE_IMAGE + "(" +
                DBImage.IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBImage.IMAGE_LOCATION + " TEXT default null," +
                DBImage.NOTE_ID + " INTEGER default null)";

        sqLiteDatabase.execSQL(imageTable);

        Log.d(TAG, "onCreate Success for imageTable");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBSubject.TABLE_SUBJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBNote.TABLE_NOTE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
}
