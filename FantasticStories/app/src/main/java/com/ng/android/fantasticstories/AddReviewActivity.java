package com.ng.android.fantasticstories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spinner chooseYearSpinner = findViewById(R.id.add_review_year_spinner);
        ArrayAdapter<CharSequence> yearSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_spinner_array, android.R.layout.simple_spinner_item);
        // Specifies the layout to use when the list of choices appears
        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the spinner
        chooseYearSpinner.setAdapter(yearSpinnerAdapter);
        // Applies the OnItemSelectedListener to the spinner
        chooseYearSpinner.setOnItemSelectedListener(this);

        Spinner chooseIssueSpinner = findViewById(R.id.add_review_issue_number_spinner);
        ArrayAdapter<CharSequence> issueSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_spinner_array, android.R.layout.simple_spinner_item);
        // Specifies the layout to use when the list of choices appears
        issueSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the spinner
        chooseIssueSpinner.setAdapter(issueSpinnerAdapter);
        // Applies the OnItemSelectedListener to the spinner
        chooseIssueSpinner.setOnItemSelectedListener(this);

        Spinner chooseStorySpinner = findViewById(R.id.add_review_title_and_author_spinner);
        ArrayAdapter<CharSequence> storySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_spinner_array, android.R.layout.simple_spinner_item);
        // Specifies the layout to use when the list of choices appears
        storySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the spinner
        chooseStorySpinner.setAdapter(storySpinnerAdapter);
        // Applies the OnItemSelectedListener to the spinner
        chooseStorySpinner.setOnItemSelectedListener(this);

        final EditText reviewTitleEditText =  findViewById(R.id.add_review_review_title_edit_text);
        final EditText ratingEditText = findViewById(R.id.add_review_rating_edit_text);
        final EditText reviewEditText = findViewById(R.id.add_review_edit_text);

        //sets the saveButton and allows it to be clickable
        Button saveButton = findViewById(R.id.add_review_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saves data (2 Strings and 1 int) from 3 EditTexts to the database by calling addReview method from
                // DataBaseHelper class
                String newTitle = reviewTitleEditText.getText().toString();
                String newRating = ratingEditText.getText().toString();
                int newRatingToInt = Integer.parseInt(newRating);
                String newReview = reviewEditText.getText().toString();
                dataBaseHelper.addReview(newRatingToInt, newTitle, newReview);
            }
        });

        //sets the quitButton and allows it to be clickable
        Button quitButton = findViewById(R.id.add_review_quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses the intent to open the main activity
                Intent getBackToMainIntent = new Intent( AddReviewActivity.this, MainActivity.class);
                startActivity(getBackToMainIntent);
            }
        });
    }

    // Allows spinner items to be clickable
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    // Decides what happens when no spinner item is selected
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
