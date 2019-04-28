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


    public MyAdapter(ArrayList<String> myArrayList) {
        this.myArrayList = myArrayList;
    }

    public void setSelectionTracker(
            SelectionTracker<Long> mySelectionTracker) {
        this.mySelectionTracker = mySelectionTracker;
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
                    return ((MyViewHolder) viewHolder).getItemDetails(); // I have no clue how to make it work
                }
            }
            return null;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.my_text_view);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
                Intent addReadActivityIntent = new Intent(myContext,
                        ReadNoteActivity.class);
                myContext.startActivity(addReadActivityIntent);
            }
        }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_adapter_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(myArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        for(String note : myArrayList) {
            if(myArrayList.get(position).equals(note)) {
                return position;
            }
        }
        return -1;
    }
}




