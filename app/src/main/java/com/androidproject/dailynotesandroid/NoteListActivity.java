package com.androidproject.dailynotesandroid;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;


public class NoteListActivity extends AppCompatActivity {

    FloatingActionButton addNote;
    ListView noteListView;

    String[] notes = {"I love Maths", "I love English", "I love Science", "I love Coding"};
    String[] notesDate = {"Dec 21", "Mar 5", "June 2", "Apr 10"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        addNote = (FloatingActionButton) findViewById(R.id.addNote);
        noteListView = (ListView) findViewById(R.id.noteListView);


        Bundle subjectName = getIntent().getExtras();
        if (subjectName != null){
            Toast.makeText(getApplicationContext(), "Subject " + subjectName, Toast.LENGTH_SHORT).show();
        }

//
        CustomAdapter adapter = new CustomAdapter();
        noteListView.setAdapter(adapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToNote = new Intent(getApplicationContext(), AddNote.class);
//                intentToNote.putExtra("NoteData", );
                startActivity(intentToNote);
                Toast.makeText(getApplicationContext(), "Pressed" + notesDate[i], Toast.LENGTH_SHORT).show();
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
                Intent intentToNote = new Intent(getApplicationContext(), AddNote.class);
                startActivity(intentToNote);
            }
        });


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