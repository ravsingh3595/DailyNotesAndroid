package com.androidproject.dailynotesandroid;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.dailynotesandroid.Database.DBImage;
import com.androidproject.dailynotesandroid.Database.DBNote;
import com.androidproject.dailynotesandroid.Model.Image;
import com.androidproject.dailynotesandroid.Model.Note;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class NoteListActivity extends AppCompatActivity {

    FloatingActionButton addNote;
    ListView noteListView;
    View notePage;

    private static final String TAG = "NoteListActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    DBImage dbImage = new DBImage(NoteListActivity.this);
    DBNote dbNote = new DBNote(NoteListActivity.this);

    String[] notes = {"I love Maths", "I love English", "I love Science", "I love Coding"};
    String[] notesDate = {"Dec 21", "Mar 5", "June 2", "Apr 10"};

    ArrayList<Note> savedNoteArrayList = new ArrayList<>();
    ArrayList<Image> savedImageArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        addNote = (FloatingActionButton) findViewById(R.id.addNote);
        noteListView = (ListView) findViewById(R.id.noteListView);
        notePage = (View) findViewById(R.id.notePage);




        Bundle subjectName = getIntent().getExtras();
        if (subjectName != null){
            Toast.makeText(getApplicationContext(), "Subject " +  subjectName.get("SubjectName"), Toast.LENGTH_SHORT).show();
        }

        CustomAdapter adapter = new CustomAdapter();
        noteListView.setAdapter(adapter);

//        savedNoteArrayList = dbNote.getAllNote(NoteListActivity.this);
//        savedImageArrayList = dbImage.getAllImages(NoteListActivity.this);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToEditNote = new Intent(getApplicationContext(), AddNote.class);
//                intentToEditNote.putExtra("NoteData", savedNoteArrayList.get(i));
//                intentToEditNote.putExtra("ImageData", savedImageArrayList.get(i));
                if(isServicesOK()) {
                    startActivity(intentToEditNote);
                    Toast.makeText(getApplicationContext(), "Pressed" + notesDate[i], Toast.LENGTH_SHORT).show();
                }
            }
        });


        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Long Pressed" + notes[i], Toast.LENGTH_SHORT).show();
                showDialog();
                return false;
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isServicesOK()){
//                    if(mLocationPermissionsGranted == true){
//                        LatLng latLng = getDeviceLocation();
//                        Log.d(TAG, "It came here");
//                        Toast.makeText(getApplicationContext(), "Lat " + latLng.latitude + "Long " + latLng.longitude, Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "No location Permissions", Toast.LENGTH_SHORT).show();
//                    }
                    Intent intentToNote = new Intent(getApplicationContext(), AddNote.class);
                    Bundle b = new Bundle();
////                    b.putDouble("LatFromNote", latLng.latitude);
////                    b.putDouble("LngFromNote", latLng.longitude);
                    intentToNote.putExtras(b);
                    startActivity(intentToNote);
                }

            }
        });

    }



    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NoteListActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NoteListActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    class CustomAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
            return notes.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.note_table_cell, null);

            TextView noteTextView = (TextView)view.findViewById(R.id.noteTextView);
            TextView dateTextView = (TextView)view.findViewById(R.id.dateTextView);

            noteTextView.setText(notes[i]);
            dateTextView.setText(notesDate[i]);

            return view;
        }
    }
    public void showDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NoteListActivity.this);
        alertDialogBuilder.setTitle("Do you really want to delete this Note?");

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(getApplicationContext(), "No Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }
}