package com.ng.android.fantasticstories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {
//    DatabaseHelper dataBaseHelper;
//    private ListView reviewListView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.issue_list_layout);
//
//        dataBaseHelper = new DatabaseHelper(this);
//        reviewListView = findViewById(R.id.list_view);
//        populateListView();
//    }
//
//    private void populateListView() {
//        // Gets the data and appends to a list
//        Cursor data = dataBaseHelper.getStoryData();
//        ArrayList<String> listData = new ArrayList<>();
//        while (data.moveToNext()){
//            // gets the value from the database in column 1 then add it to the ArrayList
//            listData.add(data.getString(1));
//        }
//        //creates the list adapter and sets the adapter
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        reviewListView.setAdapter(adapter);
//    }
}
