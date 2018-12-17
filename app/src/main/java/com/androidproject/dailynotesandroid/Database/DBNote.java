package com.androidproject.dailynotesandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidproject.dailynotesandroid.Model.Note;
import com.androidproject.dailynotesandroid.Model.Subject;

import java.util.ArrayList;

public class DBNote {

    public static final String TABLE_NOTE = "tblNote";
    public static final String SUBJECT_NAME = "subjectName";
    public static final String NOTE_ID = "noteId";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_CONTENT = "noteContent";
    public static final String AUDIO = "audio";
    public static final String DATETIME = "dateTime";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IMAGE_ID = "imageId";
    public static final String IMAGE_1 = "image1";
    public static final String IMAGE_2 = "image2";
    public static final String IMAGE_3 = "image3";

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
        contentValues.put(SUBJECT_NAME, note.getSubjectName());
        contentValues.put(NOTE_TITLE, note.getNoteTitle());
        contentValues.put(NOTE_CONTENT, note.getNoteContent());
        contentValues.put(AUDIO, note.getAudio());
        contentValues.put(DATETIME, (note.getDateTime()));
        contentValues.put(LATITUDE, note.getLatitude());
        contentValues.put(LONGITUDE, note.getLongitude());
        contentValues.put(IMAGE_ID, note.getImageId());
        contentValues.put(IMAGE_1, note.getImageId());
        contentValues.put(IMAGE_2, note.getImageId());
        contentValues.put(IMAGE_3, note.getImageId());

        database.insert(TABLE_NOTE, null, contentValues);
        database.close();

    }

    public void updateNote(Note note)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, note.getNoteTitle());
        contentValues.put(NOTE_CONTENT, note.getNoteContent());
        contentValues.put(AUDIO, note.getAudio());
        contentValues.put(DATETIME, String.valueOf(note.getDateTime()));
        contentValues.put(LATITUDE, note.getLatitude());
        contentValues.put(LONGITUDE, note.getLongitude());
        contentValues.put(IMAGE_ID, note.getImageId());
        contentValues.put(IMAGE_1, note.getImageId());
        contentValues.put(IMAGE_2, note.getImageId());
        contentValues.put(IMAGE_3, note.getImageId());

        database.update(TABLE_NOTE, contentValues, NOTE_ID + "=?", new String[]{String.valueOf(note.getNoteId())});
        database.close();

    }

    public void deleteNote(Note note)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NOTE, NOTE_ID + "=?", new String[]{String.valueOf(note.getNoteId())});
        database.close();
    }
    public void deleteNoteWithSubject(Subject subjectName)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NOTE, SUBJECT_NAME + "=?", new String[]{subjectName.getSubjectName()});
        database.close();
    }

    public ArrayList<Note> getAllNote (Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NOTE;
        Cursor cursor = database.rawQuery(selectQuery, null);

        ArrayList<Note> noteArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() > 0)
            {

                while (cursor.moveToNext())
                {
                    Note note = new Note();
                    note.setNoteId(cursor.getInt(0));
                    note.setSubjectName(cursor.getString(1));
                    note.setNoteTitle(cursor.getString(2));
                    note.setNoteContent(cursor.getString(3));
                    note.setAudio(cursor.getString(4));
                    note.setDateTime(cursor.getString(5));
                    note.setLatitude(cursor.getDouble(6));
                    note.setLongitude(cursor.getDouble(7));
                    note.setImageId(cursor.getInt(8));
                    note.setImage1(cursor.getString(9));
                    note.setImage2(cursor.getString(10));
                    note.setImage3(cursor.getString(11));



                    Log.d("AllNoteData", note.getNoteTitle());
                    noteArrayList.add(note);
                }
            }
        }
        database.close();
        return noteArrayList;
    }

    public ArrayList<Note> getNoteByDate (Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NOTE;
        String[] stringOfAruguments = {null, null, "dateTime"};
        Cursor cursor = database.rawQuery(selectQuery, stringOfAruguments);


        ArrayList<Note> noteArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() > 0)
            {

                while (cursor.moveToNext())
                {
                    Note note = new Note();
                    note.setNoteId(cursor.getInt(0));
                    note.setSubjectName(cursor.getString(1));
                    note.setNoteTitle(cursor.getString(2));
                    note.setNoteContent(cursor.getString(3));
                    note.setAudio(cursor.getString(4));
                    note.setDateTime(cursor.getString(5));
                    note.setLatitude(cursor.getDouble(6));
                    note.setLongitude(cursor.getDouble(7));
                    note.setImageId(cursor.getInt(8));
                    note.setImage1(cursor.getString(9));
                    note.setImage2(cursor.getString(10));
                    note.setImage3(cursor.getString(11));



                    Log.d("AllNoteData", note.getNoteTitle());
                    noteArrayList.add(note);
                }
            }
        }
        database.close();
        return noteArrayList;
    }

    public ArrayList<Note> getNote (Context context, Note noteData){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();


        String[] columns ={NOTE_ID, SUBJECT_NAME, NOTE_TITLE, NOTE_CONTENT, AUDIO, DATETIME, LATITUDE, LONGITUDE, IMAGE_ID, IMAGE_1, IMAGE_2, IMAGE_3};

        Cursor cursor = database.query(TABLE_NOTE,
                columns,
                NOTE_ID + "=?",
                new String[]{String.valueOf(noteData.getNoteId())},
                null,
                null,
                null);

        ArrayList<Note> noteArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() >=1)
            {
                while (cursor.moveToNext()) {
                    Note note = new Note();
                    note.setNoteId(cursor.getInt(0));
                    note.setSubjectName(cursor.getString(1));
                    note.setNoteTitle(cursor.getString(2));
                    note.setNoteContent(cursor.getString(3));
                    note.setAudio(cursor.getString(4));
                    note.setDateTime(cursor.getString(5));
                    note.setLatitude(cursor.getDouble(6));
                    note.setLongitude(cursor.getDouble(7));
                    note.setImageId(cursor.getInt(8));
                    note.setImage1(cursor.getString(9));
                    note.setImage2(cursor.getString(10));
                    note.setImage3(cursor.getString(11));


                    Log.d("NoteDataWithSubject", (cursor.getColumnName(1)));
                    noteArrayList.add(note);
                }
            }
        }
        database.close();
        return noteArrayList;
    }

    public ArrayList<Note> getNoteOfSubject (Context context, String subjectName){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();


        String[] columns ={NOTE_ID, SUBJECT_NAME, NOTE_TITLE, NOTE_CONTENT, AUDIO, DATETIME, LATITUDE, LONGITUDE, IMAGE_ID, IMAGE_1, IMAGE_2, IMAGE_3};

        Cursor cursor = database.query(TABLE_NOTE,
                columns,
                SUBJECT_NAME + "=?",
                new String[]{subjectName},
                null,
                null,
                null);

        ArrayList<Note> noteArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() >=1)
            {
                while (cursor.moveToNext()) {
                    Note note = new Note();
                    note.setNoteId(cursor.getInt(0));
                    note.setSubjectName(cursor.getString(1));
                    note.setNoteTitle(cursor.getString(2));
                    note.setNoteContent(cursor.getString(3));
                    note.setAudio(cursor.getString(4));
                    note.setDateTime(cursor.getString(5));
                    note.setLatitude(cursor.getDouble(6));
                    note.setLongitude(cursor.getDouble(7));
                    note.setImageId(cursor.getInt(8));
                    note.setImage1(cursor.getString(9));
                    note.setImage2(cursor.getString(10));
                    note.setImage3(cursor.getString(11));


                    Log.d("NoteDataWithSubject", (cursor.getColumnName(1)));
                    noteArrayList.add(note);
                }
            }
        }
        database.close();
        return noteArrayList;
    }
}
