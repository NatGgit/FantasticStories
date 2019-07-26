package com.ng.android.fantasticstories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_list_layout);
        // creates an array list for first level of expandable list view and fills it with data from
        // suitable array in string resources
        List<String> firstLevelList = new ArrayList<>();
        Collections.addAll(firstLevelList, getResources().getStringArray(R.array.items_array_expandable_level_one));
        // sets an expandable list view with adapter for its first level
        ExpandableListView issuesExpandableListView = findViewById(R.id.expandable_list_view_issue_list);
        if (issuesExpandableListView != null) {
            IssueListParentLevelAdapter parentLevelAdapter = new IssueListParentLevelAdapter(this, firstLevelList);
            issuesExpandableListView.setAdapter(parentLevelAdapter);
        }
    }

}
