package com.ng.android.noteapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;

import java.util.ArrayList;

/**
 * Custom RecyclerView.Adapter
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private ArrayList<String> notesArrayList;
    private SelectionTracker<Long> notesSelectionTracker;
    private Context context;

    /**
     * Constructor.
     * @param notesArrayList the source of data for the RecyclerView
     * @param context refers to current activity
     */
    public NotesAdapter(ArrayList<String> notesArrayList, Context context) {
        this.notesArrayList = notesArrayList;
        this.context = context;
    }

    /**
     * Allows to set the SelectionTracker
     * @param notesSelectionTracker the tracking of the selections in the RecyclerView
     */
    public void setSelectionTracker(SelectionTracker<Long> notesSelectionTracker) {
        this.notesSelectionTracker = notesSelectionTracker;
    }

    /**
     * Gets id based on items position
     * @param position items position on the arrayList
     * @return item position as an id
     */
    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * Creates a new view holder when there are no existing view holders which the RecyclerView
     * can reuse
     * @param parent view that hosts this ViewHolder
     * @param viewType the view type of this ViewHolder
     * @return viewHolder with information about its layout
     */
    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        //Sets the design of rows in RecyclerView, in this case making them show only one line of text.
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list_layout, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(rowView);
        return viewHolder;
    }

    /**
     * Binds the ViewHolders and the items from ArrayList
     * @param holder the ViewHolder specified
     * @param position the position on the ArrayList
     */
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.noteTextView.setText(notesArrayList.get(position));
        holder.noteDetails.position = position;
        this.context = holder.noteTextView.getContext();
    }

    /**
     * Gets the current number of items on ArrayList
     * @return information about ArrayList size
     */
    @Override
    public int getItemCount() {
        //in opposition to a regular Array, with ArrayList we cant use the array.length to know it's size.
        return notesArrayList.size();
    }

    /**
     * Provides information about position of the item within RecyclerView
     */
    private class NoteDetails extends ItemDetailsLookup.ItemDetails<Long> {
        long position = 0;

        /**
         * Sets item position in the RecyclerView
         * @param position current position
         */
        public void setPosition(long position){
            this.position = position;
        }

        /**
         * Gets item position in the RecycerView
         * @return information about position expressed in an integer
         */
        @Override
        public int getPosition() {
            return (int) position;
        }

        /**
         * Provides information about position of the item in RecyclerView
         * @return the position of the item
         */
        @Nullable
        @Override
        public Long getSelectionKey() {
            return position;
        }
    }

    /**
     * Provides information about the area of the screen that was touched by the user.
     */
    static class NoteDetailsLookup extends ItemDetailsLookup<Long> {
        private RecyclerView notesRecyclerView;

        /**
         * Constructor.
         * @param notesRecyclerView
         */
        NoteDetailsLookup(RecyclerView notesRecyclerView) {
            this.notesRecyclerView = notesRecyclerView;
        }

        /**
         * Provides information about the area of the screen that was clicked
         * @param e movement event that occured
         * @return information about ViewHolder that was selected
         */
        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = notesRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder noteViewHolder = notesRecyclerView.getChildViewHolder(view);
                if (noteViewHolder instanceof NoteViewHolder) {
                    return ((NoteViewHolder) noteViewHolder).getItemDetails();
                }
            }
            return null;
        }
    }

    /**
     * Handles layout inflation and child view use
     */
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTextView;
        public NoteDetails noteDetails;

        /**
         * Constructor.
         * Sets the text of the proper item from the notesArrayList with the suitable ViewHolder.
         * @param view view that was touched
         */
        public NoteViewHolder(View view) {
            super(view);
            noteTextView = view.findViewById(R.id.note_text_view);
            noteDetails = new NoteDetails();
            view.setOnClickListener(new View.OnClickListener(){
                /**
                 * Opens the ReadNoteActivity with the full text of the note that was clicked on the screen.
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    //Uses an Intent to open new Activity and pass the text of the note.
                    Intent sentNoteIntent = new Intent(context,
                            ReadNoteActivity.class);
                    sentNoteIntent.putExtra("sending_note", noteTextView.getText());
                    context.startActivity(sentNoteIntent);
                }
            });
        }

        /**
         * Gets information about the item that was selected on the screen
         * @return information about a selected item
         */
        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return noteDetails;
        }
    }
}




