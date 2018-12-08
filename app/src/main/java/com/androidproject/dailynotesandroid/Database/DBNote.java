package com.androidproject.dailynotesandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidproject.dailynotesandroid.Model.Note;

public class DBNote {


    public static final String TABLE_NOTE = "tblNote";
    public static final String NOTE_ID = "noteId";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_CONTENT = "noteContent";
    public static final String AUDIO = "audio";
    public static final String DATETIME = "dateTime";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IMAGE_ID = "imageId";


    private Context context;
    private DBHelper dbHelper;

    public DBNote(Context context)
    {
        this.context = context;
    }

    public void insertNote(Note note)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_ID, note.getNoteId());
        contentValues.put(NOTE_TITLE, note.getNoteTitle());
        contentValues.put(NOTE_CONTENT, note.getNoteContent());
        contentValues.put(AUDIO, note.getAudio());
        contentValues.put(DATETIME, String.valueOf(note.getDateTime()));
        contentValues.put(LATITUDE, note.getLatitude());
        contentValues.put(LONGITUDE, note.getLongitude());
        contentValues.put(IMAGE_ID, note.getImageId());

        database.insert(TABLE_NOTE, null, contentValues);
        database.close();

    }
}
