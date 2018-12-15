package com.androidproject.dailynotesandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.BitSet;

public class ShowFullImageActivity extends Activity {

    ImageView imageView;

    boolean isImageFitToScreen = false;
    boolean isEdit = true;
    ArrayList<String> mImgUrls = new ArrayList<String>();
    ArrayList<Bitmap> mImgBitmaps = new ArrayList<Bitmap>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_image);

        imageView = (ImageView) findViewById(R.id.imageView);

//        Intent intent = getIntent();
//        Bitmap bitmapImage = (Bitmap) intent.getParcelableExtra("BitmapImage");

        Bitmap bitmapImage;

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bitmapImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmapImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);

                }else{
                    isImageFitToScreen=true;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });
    }
}
