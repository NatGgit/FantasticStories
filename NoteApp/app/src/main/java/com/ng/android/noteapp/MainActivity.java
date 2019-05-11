/**
 * Simple app letting take, save and delete notes.
 * @author Natalia Grzywalska
 */
package com.ng.android.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;

/**
 * At first shows only the "New note" button, but as user starts to write down the notes,
 * it starts to display the list of first sentences of them. Those are clickable and link to
 * {@link ReadNoteActivity}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called on the creation of this activity.
     * Sets the RecyclerView and its elements: LayoutManager, Adapter, SelectionTracker.
     * Sets the "New note" Button, which opens {@link AddNoteActivity}.

     * @param savedInstanceState default parameter that allows the activity to restore itself to
     *                          a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the RecyclerView and its LayoutManager (in this case LinearLayoutManager).
        RecyclerView notesRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);

        //Sets the custom Adapter that will mediate between ArrayList and the RecyclerView.
        NotesAdapter notesAdapter = new NotesAdapter(AllNotes.getNotesArrayList(), this);
        //Ensures that the adapter uses unique Ids for every item.
        notesAdapter.setHasStableIds(true);
        notesRecyclerView.setAdapter(notesAdapter);

        //Sets a line separating items (not an obligatory element)
        DividerItemDecoration notesDivider = new DividerItemDecoration(notesRecyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        notesRecyclerView.addItemDecoration(notesDivider);

        //Sets the SelectionTracker with StableIdKeyProvider and Long StorageStrategy for items Ids.
        SelectionTracker<Long> notesSelectionTracker = new SelectionTracker.Builder<>("mySelection",
                notesRecyclerView,
                new StableIdKeyProvider(notesRecyclerView),
                new NotesAdapter.NoteDetailsLookup(notesRecyclerView),
                StorageStrategy.createLongStorage())
                .build();

        notesAdapter.setSelectionTracker(notesSelectionTracker);

        // Sets the "Add Note" Button and allows it to be clickable.
        Button addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens AddNoteActivity.
             * @param view
             */
            @Override
            public void onClick(View view) {
                //Uses the intent to open new activity
                Intent addNoteActivityIntent = new Intent(MainActivity.this,
                        AddNoteActivity.class);
                startActivity(addNoteActivityIntent);
            }
        });
        //Notifies the Adapter if the list of items was changed.
        notesAdapter.notifyDataSetChanged();
    }
}

