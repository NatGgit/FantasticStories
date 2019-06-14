package com.ng.android.fantasticstories;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class StoryFullSpinnerAdapter extends CursorAdapter {
    private LayoutInflater storyFullSpinnerInflater;
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;
    Context context;

    public StoryFullSpinnerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        storyFullSpinnerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // creates new views
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return storyFullSpinnerInflater.inflate(R.layout.choose_story_spinner_row_full, parent, false);
    }

    //populates views with data from database
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView reviewTitleTextView = view.findViewById(R.id.spinner_full_review_title);
        String reviewTitle = cursor.getString(cursor.getColumnIndex(databaseHelper.REVIEW_TITLE_COLUMN));
        reviewTitleTextView.setText(reviewTitle);

        TextView reviewOriginalTitleTextView = view.findViewById(R.id.spinner_full_review_original_title);
        String reviewOriginalTitle = cursor.getString(cursor.getColumnIndex(databaseHelper.ORIGINAL_TITLE_COLUMN));
        reviewOriginalTitleTextView.setText(reviewOriginalTitle);

        TextView authorNameTextView = view.findViewById(R.id.spinner_full_authors_name_text_view);
        String authorName = cursor.getString(cursor.getColumnIndex(databaseHelper.AUTHOR_NAME_COLUMN));
        authorNameTextView.setText(authorName);

        TextView authorSurnameTextView = view.findViewById(R.id.spinner_full_authors_surname_text_view);
        String authorSurname = cursor.getString(cursor.getColumnIndex(databaseHelper.AUTHOR_SURNAME_COLUMN));
        authorSurnameTextView.setText(authorSurname);

    }
}
