package com.androidproject.dailynotesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return subjects.length;
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
            sujectTextView.setText(subjects[i]);

            return view;
        }
    }
}
