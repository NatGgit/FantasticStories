package com.ng.android.fantasticstories;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class StorySimpleSpinnerAdapter extends CursorAdapter {
    private LayoutInflater storySimpleSpinnerInflater;
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;
    Context context;

    public StorySimpleSpinnerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        storySimpleSpinnerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // creates new views
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return storySimpleSpinnerInflater.inflate(R.layout.choose_story_spinner_row_simple, parent, false);
    }

    //populates views with data from database
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView reviewTitleTextView = view.findViewById(R.id.spinner_simple_review_title);
        String reviewTitle = cursor.getString(cursor.getColumnIndex(databaseHelper.REVIEW_TITLE_COLUMN));
        reviewTitleTextView.setText(reviewTitle);

        TextView authorNameTextView = view.findViewById(R.id.spinner_simple_authors_name_text_view);
        String authorName = cursor.getString(cursor.getColumnIndex(databaseHelper.AUTHOR_NAME_COLUMN));
        authorNameTextView.setText(authorName);

        TextView authorSurnameTextView = view.findViewById(R.id.spinner_simple_authors_surname_text_view);
        String authorSurname = cursor.getString(cursor.getColumnIndex(databaseHelper.AUTHOR_SURNAME_COLUMN));
        authorSurnameTextView.setText(authorSurname);

    }
}
