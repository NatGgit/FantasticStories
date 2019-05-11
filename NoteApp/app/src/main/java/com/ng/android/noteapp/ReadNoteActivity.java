package com.ng.android.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Displays the note that was clicked in MainActivity and allows to delete it or to go back
 * to MainActivity.
 */
public class ReadNoteActivity extends AppCompatActivity {

    /**
     * Called on the creation of this activity.
     * Displays the note that was clicked in the RecyclerView and allows to delete it or to go back
     * to MainActivity.
     * @param savedInstanceState default parameter that allows the activity to restore itself to
     *                           a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_note_layout);

        // Allows the reception of the intent from MainActivity. Receives the content of the note
        // that was clicked, via getExtras, as a String.
        Intent receiveNoteIntent = getIntent();
        final String receivedNote = receiveNoteIntent.getExtras().getString("sending_note");

        // Connects the TextView in Java with its equivalent in XML,
        // displays the received note in it.
        final TextView receivedNoteTextView = findViewById(R.id.note_text_view);
        receivedNoteTextView.append(receivedNote);

        //Connects the back Button in Java with its equivalent in XML.
        //Sets the listener, allowing the Button to be clickable.
        Button backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Gets user back to MainActivity.
             * @param view refers to the view that was clicked
             */
            @Override
            public void onClick(View view) {
                Intent getBackIntent = new Intent(ReadNoteActivity.this,
                        MainActivity.class);
                startActivity(getBackIntent);
            }
        });

        //Connects the delete Button in Java with its equivalent in XML.
        //Sets the listener, allowing the Button to be clickable.
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Deletes the note displayed in this activity.
             * @param view refers to the view that was clicked.
             */
            // Looks for the text from the TextView in the list of saved notes in notesArrayList by
            // comparing one to another. If it finds it, it deletes it.
            @Override
            public void onClick(View view) {
                for (int i = 0; i < AllNotes.notesArrayList.size(); i++) {
                    if (receivedNote.equals(AllNotes.notesArrayList.get(i))) {
                        AllNotes.notesArrayList.remove(i);
                        // Allows to remove only one note in case there would be at least 2 identical ones.
                        break;
                    }
                }
                // Sets the content of this TextView to empty.
                receivedNoteTextView.setText("");
            }
        });
    }
}
