package com.ng.android.fantasticstories;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class IssueListChildLevelAdapter extends BaseExpandableListAdapter
{
    private final Context context;
    private final List<String> secondLevelList;
    private final Map<String, List<String>> thirdLevelMap;
    public IssueListChildLevelAdapter    (Context context, List<String> secondLevelList, Map<String, List<String>> thirdLevelMap) {
        this.context = context;
        this.secondLevelList = secondLevelList;
        this.thirdLevelMap = thirdLevelMap;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.thirdLevelMap.get(this.secondLevelList.get(groupPosition))
                .get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.issue_list_third_level, parent, false);
        }
        TextView issueListThirdLevelTextView = convertView.findViewById(R.id.issue_list_third_level_text_view);
        issueListThirdLevelTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        issueListThirdLevelTextView.setText(childText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition)
    {
        try {
            return this.thirdLevelMap.get(this.secondLevelList.get(groupPosition)).size();
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public Object getGroup(int groupPosition)
    {
        return this.secondLevelList.get(groupPosition);
    }
    @Override
    public int getGroupCount()
    {
        return this.secondLevelList.size();
    }
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent)
    {
        String secondLevelItem = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.issue_list_second_level, parent, false);
        }
        TextView issueListSecondLevelTextView = convertView.findViewById(R.id.issue_list_second_level_text_view);
        issueListSecondLevelTextView.setText(secondLevelItem);
        issueListSecondLevelTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
