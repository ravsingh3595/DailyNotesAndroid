package com.androidproject.dailynotesandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidproject.dailynotesandroid.Model.Image;

import java.util.ArrayList;

public class DBImage {

    public static final String TABLE_IMAGE = "tblImage";
    public static final String IMAGE_ID = "imageId";
    public static final String IMAGE_LOCATION = "imageLocation";

    private Context context;
    private DBHelper dbHelper;

    public DBImage(Context context)
    {
        this.context = context;
    }

    public void insertImage(Image image)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_LOCATION, image.getImageLocation());

        database.insert(TABLE_IMAGE, null, contentValues);
        database.close();
    }

    public void updateImage(Image image)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_LOCATION, image.getImageLocation());

        database.update(TABLE_IMAGE, contentValues, IMAGE_ID + "=?", new String[]{(String.valueOf(image.getImageId()))});
        database.close();

    }

    public void deleteImage(Image image)
    {
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_IMAGE, IMAGE_ID + "=?", new String[]{(String.valueOf(image.getImageId()))});
        database.close();
    }

    public ArrayList<Image> getAllImages (Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGE;
        Cursor cursor =database.rawQuery(selectQuery, null);

        ArrayList<Image> imageArrayList = new ArrayList<>();
        if(cursor !=null)
        {
            if(cursor.getCount() > 0)
            {
                Log.d("Count for the cursor", String.valueOf(cursor.getCount()));

                while (cursor.moveToNext())
                {
                    Image image1 = new Image();
                    image1.setImageLocation(cursor.getString(1));

                    Log.d("ImageData", image1.getImageLocation());
                    imageArrayList.add(image1);
                }
            }
        }
        database.close();
        return imageArrayList;
    }

}
