package com.ng.android.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_note_layout);

        Intent receiveNoteIntent = getIntent();
        String receivedNote = receiveNoteIntent.getExtras().getString("sending_note");

        TextView receivedNoteText = findViewById(R.id.note_text_view);
        receivedNoteText.append(receivedNote);


        Button backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getBackIntent = new Intent(ReadNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // not yet set
            }
        });
    }
}
