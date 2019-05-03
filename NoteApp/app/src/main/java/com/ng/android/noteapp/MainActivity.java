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


public class MainActivity extends AppCompatActivity {
    private SelectionTracker<Long> mySelectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // would it be better to set all this as another metod, lets say "setRecyclerView"?
        RecyclerView notesRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);

        MyAdapter myAdapter = new MyAdapter(AllNotes.notesArrayList, this);
        myAdapter.setHasStableIds(true);
        myAdapter.setSelectionTracker(mySelectionTracker);
        notesRecyclerView.setAdapter(myAdapter);

        DividerItemDecoration myDivider = new DividerItemDecoration(notesRecyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        notesRecyclerView.addItemDecoration(myDivider);

        mySelectionTracker = new SelectionTracker.Builder<>("mySelection",
                notesRecyclerView,
                new StableIdKeyProvider(notesRecyclerView),
                new MyAdapter.MyDetailsLookup(notesRecyclerView),
                StorageStrategy.createLongStorage())
                .build();

        if (savedInstanceState != null){
            mySelectionTracker.onRestoreInstanceState(savedInstanceState);
        }



        // should I add an Observer?
//        mySelectionTracker.addObserver(SelectionTracker.SelectionObserver<Long>(){
//            @Override
//            public void onSelectionChanged(){
//                super.onSelectionChanged();
//            }
//        }
//        );

        Button addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent addNoteActivityIntent = new Intent(MainActivity.this,
                        AddNoteActivity.class);
                startActivity(addNoteActivityIntent);
            }
        });

        myAdapter.notifyDataSetChanged();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mySelectionTracker.onSaveInstanceState(outState);
    }

}

