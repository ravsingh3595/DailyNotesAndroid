package com.androidproject.dailynotesandroid;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidproject.dailynotesandroid.Database.DBImage;
import com.androidproject.dailynotesandroid.Database.DBNote;
import com.androidproject.dailynotesandroid.Model.Image;

import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidproject.dailynotesandroid.Model.Note;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.view.LayoutInflater;

public class AddNote extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    private static final String IMAGE_DIRECTORY = "/dailynote";
    private int GALLERY = 1, CAMERA = 2;


    Button saveNote;
    EditText txtNoteTitle;
    EditText txtNoteContent;

    boolean isEdit = false;
    DBImage dbImage = new DBImage(AddNote.this);
    DBNote dbNote = new DBNote(AddNote.this);



    private LinearLayout mGallery;
    ArrayList<Bitmap> mImgIds = new ArrayList<Bitmap>();

    private LayoutInflater mInflater;
    private HorizontalScrollView horizontalScrollView;

    private MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        saveNote = (Button) findViewById(R.id.saveNote);
        txtNoteTitle = (EditText) findViewById(R.id.txtNoteTitle);
        txtNoteContent = (EditText) findViewById(R.id.txtNoteContent);

        recyclerView = findViewById(R.id.rvAnimals);

        requestMultiplePermissions();


        Bundle noteData = getIntent().getExtras();
        if (noteData != null){
            isEdit = true;

        }

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){


                    //UPDATE Database

//                    dbNote.updateNote(note);
//                    dbImage.updateImage(image);
                }
                else{
                    //SAVE Database

//                    dbNote.insertNote(populateDataNote());
//                    dbImage.insertImage(populateDataImage());
                }


            }
        });
    }

    public Note populateDataNote(){
        Note note = new Note();
        note.setNoteTitle(txtNoteTitle.getText().toString());
        note.setNoteContent(txtNoteContent.getText().toString());
//        note.setAudio();
//        note.setDateTime();
//        note.setLatitude();
//        note.setLongitude();
//        note.setImageId();
        return note;
    }

    public Image populateDataImage(){
        Image image = new Image();
//        image.setImageLocation();
        return image;

        mInflater = LayoutInflater.from(this);

        setupRecyclerView();

    }

    private void setupRecyclerView() {

        ArrayList<String> animalNames = new ArrayList<>();

        // set up the RecyclerView
        if (mImgIds.size() > 0){

            for (int i = 0; i < mImgIds.size(); i++) {
                animalNames.add("");
                LinearLayoutManager horizontalLayoutManager
                        = new LinearLayoutManager(AddNote.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
                adapter = new MyRecyclerViewAdapter(this, mImgIds, animalNames);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
            }
        }

    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // handle action bar button activitiy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            // do something here
            if (mImgIds.size() > 0){
                Toast.makeText(this, "Array size: " + mImgIds.size(), Toast.LENGTH_SHORT).show();
                for (int i=0; i<mImgIds.size(); i++) {
                    saveImage(mImgIds.get(i));
                }
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void appendImgData(Bitmap img){
        mImgIds.add(img);
    }


    /* Button clicks */

    public void galleryButtonClick(View view) {
        choosePhotoFromGallary();
    }

    public void audioButtonClick(View view) {
        Intent intent = new Intent(AddNote.this, StoreAudioActivity.class);
        startActivity(intent);

    }

    public void mapButtonClick(View view) {
        Intent intent = new Intent(AddNote.this, ShowUserLocationActivity.class);
        startActivity(intent);
    }

    public void cameraButtonClick(View view) {
        takePhotoFromCamera();
    }


    /* Take image from gallery and camera */
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap); //uncomment to save
                    Toast.makeText(AddNote.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    imageview.setImageBitmap(bitmap);

                    appendImgData(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddNote.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            imageview.setImageBitmap(thumbnail);

            appendImgData(thumbnail);
//            saveImage(thumbnail);
            Toast.makeText(AddNote.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
//        initView();
        setupRecyclerView();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void  requestMultiplePermissions(){

        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                }

                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {
                    // show alert dialog navigating to Settings
                    //openSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread()
                .check();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }

    /* Record Audio file */

}
