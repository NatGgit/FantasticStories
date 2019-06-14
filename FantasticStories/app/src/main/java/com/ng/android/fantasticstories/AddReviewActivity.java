package com.ng.android.fantasticstories;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper databaseHelper = MainActivity.databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_layout);

        // declares 3 spinners that use data from fantasticDatabase
        final Spinner chooseYearSpinner = findViewById(R.id.add_review_year_spinner);
        final Spinner chooseIssueSpinner = findViewById(R.id.add_review_issue_number_spinner);
        final Spinner chooseStorySpinner = findViewById(R.id.add_review_title_and_author_spinner);

        // uses the simple cursor adapter to populate the first spinner with the data from the
        // "Year" column in database. Two arrays in adapter mean "from" and "to".
        SimpleCursorAdapter yearSpinnerAdapter = new SimpleCursorAdapter(AddReviewActivity.this, android.R.layout.simple_spinner_item,
                databaseHelper.getYear(), new String[]{"Year"}, new int[]{android.R.id.text1}, 0 );
        // Specifies the layout to use when the list of choices appears
        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the first spinner
        chooseYearSpinner.setAdapter(yearSpinnerAdapter);

        // Applies the OnItemSelectedListener to the first spinner
        chooseYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //retrieves the choice from the first spinner as a String
                Cursor yearFromDatabase = (Cursor) chooseYearSpinner.getSelectedItem();
                String chosenYear = yearFromDatabase.getString(yearFromDatabase.getColumnIndex("Year"));
                //uses the simple cursor adapter to populate the second spinner with the data from the database.
                SimpleCursorAdapter issueSpinnerAdapter = new SimpleCursorAdapter(AddReviewActivity.this, android.R.layout.simple_spinner_item,
                        databaseHelper.getIssueNumber(chosenYear), new String[]{"Issue"}, new int[]{android.R.id.text1}, 0 );
                // Specifies the layout to use when the list of choices appears
                issueSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applies the adapter to the spinner
                chooseIssueSpinner.setAdapter(issueSpinnerAdapter);
                // Applies the OnItemSelectedListener to the spinner
                 chooseIssueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         //retrieves the choice from the second spinner as a String
                         Cursor issueFromDatabase = (Cursor) chooseIssueSpinner.getSelectedItem();
                         String chosenIssue = issueFromDatabase.getString(issueFromDatabase.getColumnIndex("Issue"));
                         // checks if the story original language is Polish in order to determine if
                         // there is a need for a simple or full layout. Full layout contains the additional
                         // information about story original title
                         if(databaseHelper.checkIfStoryLanguageIsPolish(chosenIssue)) {
                             // uses custom spinner adapter with simpler layout for the row in spinner
                             // for the stories originally in Polish
                               StorySimpleSpinnerAdapter storySimpleSpinnerAdapter = new StorySimpleSpinnerAdapter(
                                       AddReviewActivity.this, databaseHelper.getPolishStoryData(chosenIssue), 0);
                             // Applies the adapter to the spinner
                             chooseStorySpinner.setAdapter(storySimpleSpinnerAdapter);
                         } else {
                             // uses custom spinner adapter with full layout for the row in spinner
                             // for the stories of foreign origin
                             StoryFullSpinnerAdapter storyFullSpinnerAdapter = new StoryFullSpinnerAdapter(
                                     AddReviewActivity.this, databaseHelper.getForeignStoryData(chosenIssue), 0);
                             // Applies the adapter to the spinner
                             chooseStorySpinner.setAdapter(storyFullSpinnerAdapter);
                         }
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parent) {
                     }
                 });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final TextView ratingTextView = findViewById(R.id.add_review_rating_text_view);

        // This spinner uses the array of 0-10 ratings from the app resources
        final Spinner ratingSpinner = findViewById(R.id.add_review_rating_spinner);
        ArrayAdapter<CharSequence> ratingSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.rating_spinner_array, android.R.layout.simple_spinner_item);
        // Specifies the layout to use when the list of choices appears
        ratingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the spinner
        ratingSpinner.setAdapter(ratingSpinnerAdapter);


        final EditText reviewTitleEditText =  findViewById(R.id.add_review_review_title_edit_text);

        final EditText reviewEditText = findViewById(R.id.add_review_edit_text);

        //sets the saveButton and allows it to be clickable
        Button saveButton = findViewById(R.id.add_review_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //collects data from 3 EditTexts as Strings
                String newTitle = reviewTitleEditText.getText().toString();
                String newReview = reviewEditText.getText().toString();
                String newRating = ratingSpinner.getSelectedItem().toString();
                int newRatingToInt = Integer.parseInt(newRating);
                // saves data to the database by calling addReview method from DatabaseHelper class
                boolean insertData = databaseHelper.addReview(newRatingToInt, newTitle, newReview);
                //shows a short message (aka Toast) confirming that the review was saved
                if (insertData) {
                    Toast.makeText(AddReviewActivity.this, "Your review has been saved",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddReviewActivity.this, "An error occurred, please try again",
                            Toast.LENGTH_SHORT).show();
                }
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
                               int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    // Decides what happens when no spinner item is selected
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
