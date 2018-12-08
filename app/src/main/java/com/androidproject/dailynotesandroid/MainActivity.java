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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn;
    ListView sujectListView;

    String[] subjects= {"Maths", "English", "Science"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (FloatingActionButton) findViewById(R.id.btn);
        sujectListView = (ListView) findViewById(R.id.subjectListView);
        CustomAdapter customAdapter = new CustomAdapter();
        sujectListView.setAdapter(customAdapter);

//        final Dialog dialog = new Dialog(getApplicationContext());
//
//        dialog.setContentView(R.layout.add_subject);
//        dialog.setTitle("Custom Alert Dialog");
//
//        final EditText editText = (EditText) dialog.findViewById(R.id.editText);
//        Button btnSave          = (Button) dialog.findViewById(R.id.save);
//        Button btnCancel        = (Button) dialog.findViewById(R.id.cancel);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                showDialog();

//                Toast.makeText(getApplicationContext(), "Floater Clicked", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(), NoteListActivity.class);
//                startActivity(i);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Add Subject");
        alertDialogBuilder.setView(R.layout.add_subject);
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
                Toast.makeText(getApplicationContext(), "Save Clicked", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return subjects.length;
        }

        @Override
        public Object getItem(int i) {
//            Toast.makeText(getApplicationContext(), "row number" + i, Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        public long getItemId(int i) {
            Toast.makeText(getApplicationContext(), "row number" + i, Toast.LENGTH_SHORT).show();
            return 0;
        }



        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.subject_table_cell, null);

            TextView sujectTextView = (TextView)view.findViewById(R.id.SubjectTextView);
            sujectTextView.setText(subjects[i]);

            return view;
        }
    }
}
