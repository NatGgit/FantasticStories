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
        // Init top level data
        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);
        Collections.addAll(listDataHeader, mItemHeaders);
        ExpandableListView mExpandableListView = findViewById(R.id.expandableListView_Parent);
        if (mExpandableListView != null) {
            IssueListParentLevelAdapter parentLevelAdapter = new IssueListParentLevelAdapter(this, listDataHeader);
            mExpandableListView.setAdapter(parentLevelAdapter);
        }
    }

}
