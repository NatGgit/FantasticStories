package com.ng.android.fantasticstories;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueListParentLevelAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> firstLevelList;
    private final Map<String, List<String>> secondLevelMap;
    private final Map<String, List<String>> thirdLevelMap;
    public IssueListParentLevelAdapter(Context context, List<String> firstLevelList) {
        this.context = context;
        this.firstLevelList = new ArrayList<>();
        this.firstLevelList.addAll(firstLevelList);
        // Initializes second level data
        String[] secondLevelItemArray;
        secondLevelMap = new HashMap<>();
        int parentCount = firstLevelList.size();
        for (int i = 0; i < parentCount; i++) {
            String content = firstLevelList.get(i);
            switch (content) {
                case "Level 1.1":
                    secondLevelItemArray = context.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
                    break;
                case "Level 1.2":
                    secondLevelItemArray = context.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
                    break;
                default:
                    secondLevelItemArray = context.getResources().getStringArray(R.array.items_array_expandable_other_child);
            }
            secondLevelMap.put(firstLevelList.get(i), Arrays.asList(secondLevelItemArray));
        }
        // Initializes third level data
        String[] thirdLevelItemArray;
        List<String> thirdLevelItemList;
        thirdLevelMap = new HashMap<>();
        for (Object o : secondLevelMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object object = entry.getValue();
            if (object instanceof List) {
                List<String> stringList = new ArrayList<>();
                Collections.addAll(stringList, (String[]) ((List) object).toArray());
                for (int i = 0; i < stringList.size(); i++) {
                    thirdLevelItemArray = context.getResources().getStringArray(R.array.items_array_expandable_level_three);
                    thirdLevelItemList = Arrays.asList(thirdLevelItemArray);
                    thirdLevelMap.put(stringList.get(i), thirdLevelItemList);
                }
            }
        }
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.context);
        String parentNode = (String) getGroup(groupPosition);
        secondLevelExpListView.setAdapter(new IssueListChildLevelAdapter(this.context, secondLevelMap.get(parentNode), thirdLevelMap));
        secondLevelExpListView.setGroupIndicator(null);
        return secondLevelExpListView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.firstLevelList.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this.firstLevelList.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.issue_list_first_level, parent, false);
        }
        TextView issueListFirstLevelTextView = convertView.findViewById(R.id.issue_list_first_level_text_view);
        issueListFirstLevelTextView.setTypeface(null, Typeface.BOLD);
        issueListFirstLevelTextView.setText(headerTitle);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    } }