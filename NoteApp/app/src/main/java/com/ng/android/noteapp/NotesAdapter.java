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

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private ArrayList<String> notesArrayList;
    private SelectionTracker<Long> notesSelectionTracker;
    private Context context;

    public NotesAdapter(ArrayList<String> notesArrayList, Context context) {
        this.notesArrayList = notesArrayList;
        this.context = context;
    }

    public void setSelectionTracker(
            SelectionTracker<Long> notesSelectionTracker) {
        this.notesSelectionTracker = notesSelectionTracker;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list_layout, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.noteTextView.setText(notesArrayList.get(position));
        holder.noteDetails.position = position;
        this.context = holder.noteTextView.getContext();
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    private class NoteDetails extends ItemDetailsLookup.ItemDetails<Long> {
        long position = 0;

        public void setPosition(long position){
            this.position = position;
        }

        @Override
        public int getPosition() {
            return (int) position;
        }

        @Nullable
        @Override
        public Long getSelectionKey() {
            return position;
        }
    }

    static class NoteDetailsLookup extends ItemDetailsLookup<Long> {
        private RecyclerView notesRecyclerView;

        NoteDetailsLookup(RecyclerView notesRecyclerView) {
            this.notesRecyclerView = notesRecyclerView;
        }

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

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTextView;
        public NoteDetails noteDetails;

        public NoteViewHolder(View view) {
            super(view);
            noteTextView = view.findViewById(R.id.note_text_view);
            noteDetails = new NoteDetails();
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent sentNoteIntent = new Intent(context,
                            ReadNoteActivity.class);
                    sentNoteIntent.putExtra("sending_note", noteTextView.getText());
                    context.startActivity(sentNoteIntent);
                }
            });
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return noteDetails;
        }
    }

}




