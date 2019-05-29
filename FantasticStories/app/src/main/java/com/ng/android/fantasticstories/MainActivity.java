package com.ng.android.fantasticstories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper fsDataBaseHelper = new DataBaseHelper(this);

        //sets the issueListButton and allows it to be clickable
        Button issueListButton = findViewById(R.id.look_through_issues_button);
        issueListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Uses the intent to open new activity
                Intent issueListActivityIntent = new Intent(MainActivity.this,
                        IssueListActivity.class);
                startActivity(issueListActivityIntent);
            }
        });

        //sets the findStoryButton and allows it to be clickable
        Button findStoryButton = findViewById(R.id.look_for_a_story_button);
        findStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses the intent to open new activity
                Intent findStoryActivityIntent = new Intent(MainActivity.this,
                        FindStoryActivity.class);
                startActivity(findStoryActivityIntent);
            }
        });

        CardView cardView = findViewById(R.id.cardView);
        TextView myReviewsTextView = findViewById(R.id.my_reviews_textview);

        //Sets the spinner with obligatory adapter using the string array from resources and
        // a default spinner layout
        Spinner mainActivitySpinner = findViewById(R.id.main_activity_spinner);
        ArrayAdapter<CharSequence> mainSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.main_spinner_array, android.R.layout.simple_spinner_item);
        // Specifies the layout to use when the list of choices appears
        mainSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applies the adapter to the spinner
        mainActivitySpinner.setAdapter(mainSpinnerAdapter);
        // Applies the OnItemSelectedListener to the spinner
        mainActivitySpinner.setOnItemSelectedListener(this);


        RecyclerView mainReviewRecyclerView = findViewById(R.id.main_review_recyclerview);

        //sets the moreReviewsButton and allows it to be clickable
        Button moreReviewsButton = findViewById(R.id.more_reviews_button);
        moreReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses the intent to open new activity
                Intent reviewListActivityIntent = new Intent(MainActivity.this,
                        ReviewListActivity.class);
                startActivity(reviewListActivityIntent);
            }
        });

        //sets the addReviewButton and allows it to be clickable
        Button addReviewButton = findViewById(R.id.add_review_button);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uses the intent to open new activity
                Intent addReviewActivityIntent = new Intent(MainActivity.this,
                        AddReviewActivity.class);
                startActivity(addReviewActivityIntent);
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
