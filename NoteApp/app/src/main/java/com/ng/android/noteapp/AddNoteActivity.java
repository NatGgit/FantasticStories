package com.ng.android.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Allows to write down and save a note (in the notesArrayList) and then
 * to go back to MainActivity.
 */
public class AddNoteActivity extends AppCompatActivity {
    private EditText noteEditText;
    private AllNotes allNotes = new AllNotes();

    /**
     * Called on the creation of this activity.
     * Sets the EditText and two Buttons: "save" button and "go back" button.
     * @param savedInstanceState default parameter that allows the activity to restore itself to
     *                          a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_laytout);
        noteEditText = findViewById(R.id.note_edit_text);
        Button saveButton = findViewById(R.id.save_button);
        Button backButton = findViewById(R.id.go_back_button);

        //Allows the Button to be clickable.
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Gets user back to MainActivity.
             * @param view refers to the view that was clicked
             */
            @Override
            public void onClick(View view) {
                Intent getBackIntent = new Intent(AddNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

        //Allows the Button to be clickable.
        saveButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Saves the user's note to the notesArrayList and gets the user back to MainActivity.
             * @param view refers to the view that was clicked.
             */
            @Override
            public void onClick(View view) {
                //Reads the text that was passed by the user in the EditText and saves it to the notesArrayList.
                String noteText = String.valueOf(noteEditText.getText());
                allNotes.addNote(noteText);
                //Sets the content of the EditText to empty.
                noteEditText.setText("");
                //Gets user back to MainActivity using Intent.
                Intent getBackIntent = new Intent(AddNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

    }

}








