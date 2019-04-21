package com.ng.android.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    private Button saveButton, backButton;
    private EditText noteEditText;
    private AllNotes allNotes = new AllNotes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_laytout);
        noteEditText = findViewById(R.id.note_edit_text);
        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.go_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getBackIntent = new Intent(AddNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteText = String.valueOf(noteEditText.getText());
                allNotes.addNote(noteText);
                noteEditText.setText("");
                Intent getBackIntent = new Intent(AddNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

    }

}








