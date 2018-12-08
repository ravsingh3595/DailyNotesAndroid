package com.androidproject.dailynotesandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void galleryButtonClick(View view) {

    }

    public void audioButtonClick(View view) {

    }

    public void mapButtonClick(View view) {

    }

    public void cameraButtonClick(View view) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivity(intent);

    }
}
