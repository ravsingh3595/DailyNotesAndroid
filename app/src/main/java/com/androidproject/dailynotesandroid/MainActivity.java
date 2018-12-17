package com.androidproject.dailynotesandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.dailynotesandroid.Database.DBNote;
import com.androidproject.dailynotesandroid.Database.DBSubject;
import com.androidproject.dailynotesandroid.Model.Subject;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn;
    ListView subjectListView;
    EditText addSubjectEditBox;

    DBSubject dbSubject = new DBSubject(MainActivity.this);
    DBNote dbNote =  new DBNote(MainActivity.this);
    ArrayList<Subject> savedSubjectsArraylist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (FloatingActionButton) findViewById(R.id.btn);
        subjectListView = (ListView) findViewById(R.id.subjectListView);
        CustomAdapter customAdapter = new CustomAdapter();
        subjectListView.setAdapter(customAdapter);

        savedSubjectsArraylist = dbSubject.getAllSubject(MainActivity.this);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentToNote = new Intent(getApplicationContext(), NoteListActivity.class);
                intentToNote.putExtra("SubjectName", savedSubjectsArraylist.get(i).getSubjectName());
                startActivity(intentToNote);
            }
        });

        subjectListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "row number  at starting " + i, Toast.LENGTH_SHORT).show();
                showDialogForDelete(i);
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                showDialog();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Add Subject");
        final View dialogView = getLayoutInflater().inflate(R.layout.add_subject, null);
        alertDialogBuilder.setView(dialogView);



        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                addSubjectEditBox = (EditText) dialogView.findViewById(R.id.addSubjectEditText);

                dbSubject.insertSubject(addSubject(addSubjectEditBox.getText().toString()));
                Toast.makeText(getApplicationContext(), "Save Clicked " + addSubjectEditBox.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
            }
        });

        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();

    }

    public void showDialogForDelete(final int i1)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Do you really want to delete this Subject, All notes will be deleted!!!");

        alertDialogBuilder.setCancelable(true);

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
                Toast.makeText(getApplicationContext(), "row number" + i1, Toast.LENGTH_SHORT).show();
                dbSubject.deleteSubject(savedSubjectsArraylist.get(i1));
                dbNote.deleteNoteWithSubject((savedSubjectsArraylist.get(i1)));


                //Delete all the notes in this subject here.




                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
            }
        });



        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }

    public Subject addSubject(String subjectName){
        Subject addSubject = new Subject();
        addSubject.setSubjectName(subjectName);
        return addSubject;
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return savedSubjectsArraylist.size();
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
            view = getLayoutInflater().inflate(R.layout.subject_table_cell, null);

            TextView sujectTextView = (TextView)view.findViewById(R.id.SubjectTextView);

            ImageView image = (ImageView) findViewById(R.id.SubjectImageView);


            sujectTextView.setText(savedSubjectsArraylist.get(i).getSubjectName());
//            Toast.makeText(getApplicationContext(), "Value for i " + savedSubjectsArraylist.get(i).getSubjectId(), Toast.LENGTH_SHORT).show();

            return view;
        }
    }
}
