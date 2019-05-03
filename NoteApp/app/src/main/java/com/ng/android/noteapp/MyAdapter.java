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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<String> myArrayList;
    private SelectionTracker<Long> mySelectionTracker;
    private Context myContext;

    public MyAdapter(ArrayList<String> myArrayList, Context myContext) {
        this.myArrayList = myArrayList;
        this.myContext = myContext;
    }

    public void setSelectionTracker(
            SelectionTracker<Long> mySelectionTracker) {
        this.mySelectionTracker = mySelectionTracker;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_list_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(myArrayList.get(position));
        holder.details.position = position;
        this.myContext = holder.textView.getContext();
    }

    @Override
    public int getItemCount() {
        return myArrayList.size();
    }



    private class Details extends ItemDetailsLookup.ItemDetails<Long> {
        long position;

        // apparently not used
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

        //should I add it?
        @Override
        public boolean inSelectionHotspot(MotionEvent event){
            return true;
        }
    }

    static class MyDetailsLookup extends ItemDetailsLookup<Long> {
        private RecyclerView myRecyclerView;

        MyDetailsLookup(RecyclerView myRecyclerView) {
            this.myRecyclerView = myRecyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = myRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = myRecyclerView.getChildViewHolder(view);
                if (viewHolder instanceof MyViewHolder) {
                    return ((MyViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Details details;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.my_text_view);
            details = new Details();
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent sentNoteIntent = new Intent(myContext,
                            ReadNoteActivity.class);
                    sentNoteIntent.putExtra("sending_note", mySelectionTracker.getSelection().toString());
                    myContext.startActivity(sentNoteIntent);
                }
            });
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return details;
        }
    }

}




