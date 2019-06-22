package com.ng.android.fantasticstories;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class StorySpinnerAdapter extends CursorAdapter {
    private LayoutInflater storySpinnerInflater;
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    public StorySpinnerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    // creates new views based on the type of story language defined in original languages list
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.choose_story_spinner_row, parent, false);
    }

    //populates views with data from database
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView storyTitleTextView = view.findViewById(R.id.spinner_story_title_text_view);
        String storyTitle = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.STORY_TITLE_COLUMN));
        storyTitleTextView.setText(storyTitle);

        TextView storyOriginalTitleTextView = view.findViewById(R.id.spinner_original_title_text_view);
        String storyOriginalTitle = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.ORIGINAL_TITLE_COLUMN));
        storyOriginalTitleTextView.setText(storyOriginalTitle);

        TextView authorNameTextView = view.findViewById(R.id.spinner_authors_name_text_view);
        String authorName = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.AUTHOR_NAME_COLUMN));
        authorNameTextView.setText(authorName);

        TextView authorSurnameTextView = view.findViewById(R.id.spinner_authors_surname_text_view);
        String authorSurname = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.AUTHOR_SURNAME_COLUMN));
        authorSurnameTextView.setText(authorSurname);

        View divider = view.findViewById(R.id.spinner_divider);
    }

}
