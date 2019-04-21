package com.ng.android.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView notesRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        MyAdapter myAdapter = new MyAdapter(AllNotes.notesArrayList);

        notesRecyclerView.setAdapter(myAdapter);
        notesRecyclerView.setHasFixedSize(true);

        // still need to set a method working like setOnClickListener (which does not apply to RecyclerView)

        Button addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent addNoteActivityIntent = new Intent(MainActivity.this,
                        AddNoteActivity.class);
                startActivity(addNoteActivityIntent);
            }
        });
    }
}

