package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {


    TextView showTitle, showDetails;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setTitle("Notes Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showTitle = findViewById(R.id.ShowTitle);
        showDetails = findViewById(R.id.ShowDetails);

        NoteDatabase db = new NoteDatabase(this);
        Intent intent = getIntent();

        id = intent.getIntExtra("ID", 0);

        NoteModel noteModel = db.getNotes(id);

        showTitle.setText(noteModel.getNoteTitle());
        showDetails.setText(noteModel.getNoteDetails());
        Toast.makeText(getApplicationContext(),"id"+noteModel.getId(), Toast.LENGTH_SHORT).show();

    }

}