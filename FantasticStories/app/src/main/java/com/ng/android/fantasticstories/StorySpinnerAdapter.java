package com.ng.android.fantasticstories;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class StorySpinnerAdapter extends CursorAdapter {
    private LayoutInflater storySpinnerInflater;
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    //this arraylist should be populated each time the originalLAnguagesList is populated in AddReviewActivity
    ArrayList originalLanguagesList = AddReviewActivity.originalLanguagesList;

    public static final int TYPE_POLISH = 0;
    public static final int TYPE_FOREIGN = 1;
    public static final int NUMBER_OF_TYPES = 2;

    public StorySpinnerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        storySpinnerInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // creates new views based on the type of story language defined in original languages list
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        for (int i = 0; i <= getCount(); i++) {
            if (getItemViewType(i) == 1) {
                return storySpinnerInflater.inflate(R.layout.choose_story_spinner_row_full, parent, false);
            } else {
                return storySpinnerInflater.inflate(R.layout.choose_story_spinner_row_simple, parent, false);
            }
        }
        //not sure if this return statement is correct
        return null;
    }

    //populates views with data from database
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        for (int i = 0; i <= getCount(); i++) {
            if (getItemViewType(i) == 1) {
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
            } else {
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
    }


    @Override
    public int getItemViewType(int position){
        return originalLanguagesList.contains("Polish") ? TYPE_POLISH : TYPE_FOREIGN;
    }

    @Override
    public int getViewTypeCount(){
        return NUMBER_OF_TYPES;
    }

    @Override
    public int getCount(){
        return originalLanguagesList.size();
    }


    @Override
    public long getItemId(int position){
        return position;
    }
}
