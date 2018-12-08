package com.androidproject.dailynotesandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidproject.dailynotesandroid.Model.Subject;

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

}
