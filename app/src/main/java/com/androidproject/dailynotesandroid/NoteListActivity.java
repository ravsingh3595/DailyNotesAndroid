package com.androidproject.dailynotesandroid;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NoteListActivity extends AppCompatActivity {

    FloatingActionButton addNote;
    ListView noteListView;
    View notePage;
    private EditText etSearch;

    private static final String TAG = "NoteListActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    DBImage dbImage = new DBImage(NoteListActivity.this);
    DBNote dbNote = new DBNote(NoteListActivity.this);

    String[] notes = {"I love Maths", "I love English", "I love Science", "I love Coding"};
    String[] notesDate = {"Dec 21", "Mar 5", "June 2", "Apr 10"};

    ArrayList<Note> savedNoteArrayList = new ArrayList<>();
    ArrayList<Image> savedImageArrayList = new ArrayList<>();
    Bundle subjectName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        addNote = (FloatingActionButton) findViewById(R.id.addNote);
        noteListView = (ListView) findViewById(R.id.noteListView);
        notePage = (View) findViewById(R.id.notePage);

        etSearch = (EditText) findViewById(R.id.etSearch);


//        savedNoteArrayList = dbNote.getAllNote(NoteListActivity.this);



        subjectName = getIntent().getExtras();
        if (subjectName != null){

//            Toast.makeText(getApplicationContext(), "Subject " +  subjectName.get("SubjectName"), Toast.LENGTH_SHORT).show();
            setTitle(subjectName.get("SubjectName").toString());

            Toast.makeText(getApplicationContext(), "Subject " +  subjectName.get("SubjectName"), Toast.LENGTH_SHORT).show();


        }
        savedNoteArrayList = dbNote.getNoteOfSubject(NoteListActivity.this, subjectName.getString("SubjectName"));



        savedNoteArrayList.add(new Note("Note 1", "Sonia"));
        savedNoteArrayList.add(new Note("Note 2", "vaneet"));
        savedNoteArrayList.add(new Note("Note 3", "laxmi"));
        savedNoteArrayList.add(new Note("Note 4", "geeta"));
        savedNoteArrayList.add(new Note("Note 5", "vani"));
        savedNoteArrayList.add(new Note("Note 6", "soni"));

        final CustomAdapter adapter = new CustomAdapter(NoteListActivity.this, savedNoteArrayList);

        noteListView.setAdapter(adapter);


//        savedNoteArrayList = dbNote.getAllNote(NoteListActivity.this);
//        savedImageArrayList = dbImage.getAllImages(NoteListActivity.this);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToEditNote = new Intent(getApplicationContext(), AddNote.class);
                Bundle EditNoteBundle = new Bundle();
                EditNoteBundle.putBoolean("isEdit", true);
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
                    b.putString("subjectName", (String) subjectName.get("SubjectName"));
////                    b.putDouble("LatFromNote", latLng.latitude);
////                    b.putDouble("LngFromNote", latLng.longitude);
                    intentToNote.putExtras(b);
                    startActivity(intentToNote);
                }

            }
        });

        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
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

    class CustomAdapter extends  BaseAdapter implements Filterable {

        private ArrayList<Note> mDisplayedValues;
        private ArrayList<Note> mOriginalValues;
        LayoutInflater inflater;

//        @Override
//        public int getCount() {
//            return notes.length;
//        }

        public CustomAdapter(Context context, ArrayList<Note> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

//             return mDisplayedValues.size();

            if (savedNoteArrayList != null){
                return savedNoteArrayList.size();
            }
            else {
                return notes.length;
            }

        }

//        @Override
//        public Object getItem(int i) {
//            return null;
//        }

        @Override
        public Object getItem(int position) {
            return position;
        }

//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.note_table_cell, null);

            TextView noteTextView = (TextView)view.findViewById(R.id.noteTextView);
            TextView dateTextView = (TextView)view.findViewById(R.id.dateTextView);


//            noteTextView.setText(notes[i]);

            if (savedNoteArrayList != null){
                noteTextView.setText(savedNoteArrayList.get(i).getNoteTitle());
                dateTextView.setText(savedNoteArrayList.get(i).getDateTime());

            }else {
                noteTextView.setText(notes[i]);

//            dateTextView.setText(notesDate[i]); // comment by sonia

            noteTextView.setText(mDisplayedValues.get(i).getNoteTitle());


                /* sonia changes */
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(new Date()); // pass date that get from database
                dateTextView.setText(strDate);
                /********************************/
            }



            return view;
        }


        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<Note>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Note> FilteredArrList = new ArrayList<Note>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Note>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();


//                        ArrayList<Integer> indexList = new ArrayList<Integer>();
//                        for (int i = 0; i < mDisplayedValues.size(); i++)
//                            if(constraint.toString().equals(mDisplayedValues.get(i)))
//                                indexList.add(i);
//
//                        for (int i = 0; i < mOriginalValues.size(); i++) {
//                            for (int j=0; j<indexList.size(); j++){
//                                if (j==i){
//                                    FilteredArrList.add(new Note(mOriginalValues.get(i).getSubjectName(), mOriginalValues.get(i).getNoteId(), mOriginalValues.get(i).getNoteTitle(), mOriginalValues.get(i).getNoteContent(), mOriginalValues.get(i).getAudio(), mOriginalValues.get(i).getDateTime(), mOriginalValues.get(i).getLatitude(), mOriginalValues.get(i).getLongitude(), mOriginalValues.get(i).getImageId()));
//
//                                }
//                            }
//                        }



                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getNoteTitle();

                            String data1 = mOriginalValues.get(i).getNoteContent();


//                            data = mOriginalValues.get(i).getNoteContent();
                            if (data.toLowerCase().startsWith(constraint.toString()) || data1.toLowerCase().startsWith(constraint.toString())) {
//                                FilteredArrList.add(new Product(mOriginalValues.get(i).name,mOriginalValues.get(i).price));
                                FilteredArrList.add(new Note(mOriginalValues.get(i).getSubjectName(), mOriginalValues.get(i).getNoteId(), mOriginalValues.get(i).getNoteTitle(), mOriginalValues.get(i).getNoteContent(), mOriginalValues.get(i).getAudio(), mOriginalValues.get(i).getDateTime(), mOriginalValues.get(i).getLatitude(), mOriginalValues.get(i).getLongitude(), mOriginalValues.get(i).getImageId()));

//                                (String subjectName, int noteId, String noteTitle, String noteContent, String audio, Date dateTime, float latitude, float longitude, int imageId)
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
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