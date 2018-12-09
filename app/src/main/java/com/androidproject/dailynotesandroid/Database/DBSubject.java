package com.androidproject.dailynotesandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidproject.dailynotesandroid.Model.Subject;

import java.util.ArrayList;

public class DBSubject {

    public static final String TABLE_SUBJECT = "tblSubject";
    public static final String SUBJECT_ID = "subjectId";
    public static final String SUBJECT_NAME = "subjectName";


    private Context context;
    private DBHelper dbHelper;

    public DBSubject(Context context)
    {
        this.context = context;
    }

    public void insertSubject(Subject subject)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT_ID, subject.getSubjectId());
        contentValues.put(SUBJECT_NAME, subject.getSubjectName());

        database.insert(TABLE_SUBJECT, null, contentValues);
        database.close();

    }

    public void updateSubject(Subject subject)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT_NAME, subject.getSubjectName());

        database.update(TABLE_SUBJECT, contentValues, SUBJECT_ID + "=?", new String[]{String.valueOf(subject.getSubjectId())});
        database.close();

    }

    public void deleteSubject(Subject subject)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_SUBJECT, SUBJECT_ID + "=?", new String[]{String.valueOf(subject.getSubjectId())});
        database.close();
    }

    public ArrayList<Subject> getAllSubject (Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUBJECT;
        Cursor cursor =database.rawQuery(selectQuery, null);

        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() > 0)
            {
                Subject subject = new Subject();
                while (cursor.moveToNext())
                {

                    subject.setSubjectName(cursor.getString(1));

                    Log.d("SubjectData", subject.getSubjectName());
                    subjectArrayList.add(subject);
                }
            }
        }
        database.close();
        return subjectArrayList;
    }

}
