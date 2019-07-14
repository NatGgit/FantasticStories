package com.ng.android.fantasticstories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String DB_PATH;
    private static final String DB_NAME = "FS_database.db";
    private SQLiteDatabase fantasticDatabase;
    private final Context context;

    // that's the list of columns in the table
    final String REVIEWS_TABLE = "Reviews";
    final String ID_COLUMN = "_id";
    final String YEAR_COLUMN = "Year";
    final String ISSUE_COLUMN = "Issue";
    final String AUTHOR_NAME_COLUMN = "StoryAuthorFirstName";
    final String AUTHOR_SURNAME_COLUMN = "StoryAuthorSurname";
    final String STORY_TITLE_COLUMN = "StoryTitle";
    final String ORIGINAL_TITLE_COLUMN = "StoryOriginalTitle";
    final String RATING_COLUMN = "Rating";
    final String DATE_COLUMN = "ReviewCreationDate";
    final String REVIEW_TITLE_COLUMN = "ReviewTitle";
    final String REVIEW_TEXT_COLUMN = "ReviewText";

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application
     * assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context){
        super(context, DB_NAME,null, 1);
        this.context = context;
        DB_PATH = context.getFilesDir().getPath();
        try {
            copyDatabase();
        } catch (IOException e){
            throw new Error("IOException in createDatabase().");
        }
        //opens a database after copying it. Otherwise we get a null pointer exception when trying
        // to operate on it
        openDatabase();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void copyDatabase() throws IOException {
        boolean dbExist = checkIfDatabaseExists();
        if(dbExist){
            //does nothing - database already exist
        }else{
            //By calling this method an empty database will be created into the default system path
            //of the application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();
            try {
                copyDatabaseFile();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Checks if the database already exist to avoid re-copying the file each time you open the
     * application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkIfDatabaseExists(){
        SQLiteDatabase fantasticDataBase = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            fantasticDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(fantasticDataBase != null){
            fantasticDataBase.close();
        }
        return fantasticDataBase != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDatabaseFile() throws IOException{
        //Opens the local database as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Path to the just created empty database
        String outFileName = DB_PATH + DB_NAME;
        //Opens the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfers bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Closes the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLiteException{
        //Opens the database
        String myPath = DB_PATH + DB_NAME;
        fantasticDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        super.close();
        if(fantasticDatabase != null)
            fantasticDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // gets the list of years from the database.
    // Includes the _id column in the sqlite query for SimpleCursorAdapter to work properly
    public Cursor getYear (){
        String query = "SELECT " + ID_COLUMN + ", " + YEAR_COLUMN + " FROM " + REVIEWS_TABLE
                + " GROUP BY " + YEAR_COLUMN + " ORDER BY " + YEAR_COLUMN + " DESC";
        Cursor data = fantasticDatabase.rawQuery(query, null);
        return data;
    }

    // gets the list of issue numbers from a specific year from the database.
    // Includes the _id column in the sqlite query for SimpleCursorAdapter to work properly
    public Cursor getIssueNumber (String chosenYear){
        String query = "SELECT " + ID_COLUMN + ", " + ISSUE_COLUMN + " FROM " + REVIEWS_TABLE + " WHERE "
                + YEAR_COLUMN + " = \"" + chosenYear + "\" GROUP BY " + ISSUE_COLUMN + " ORDER BY "
                + ISSUE_COLUMN + " DESC";
        Cursor data = fantasticDatabase.rawQuery(query, null);
        return data;
    }

    // gets the list of stories (title, author's name and surname) from a specific issue from the database.
    public Cursor getStoryData(String chosenIssue){
           String query = "SELECT " + ID_COLUMN + ", " + STORY_TITLE_COLUMN + ", " + ORIGINAL_TITLE_COLUMN + ", " + AUTHOR_NAME_COLUMN + ", " +
                        AUTHOR_SURNAME_COLUMN  + " FROM " + REVIEWS_TABLE + " WHERE "
                        + ISSUE_COLUMN + " = \"" + chosenIssue + "\"";
           Cursor data = fantasticDatabase.rawQuery(query, null);
           return data;
    }

    public boolean addReview (String chosenStoryTitle, String chosenAuthor, int rating, String reviewTitle, String reviewText) {
        //gets the time of creation of the review from the system and saves it as a string
        Date creationDate = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String creationDateString = dateFormat.format(creationDate);

        //creates pairs column name - value to insert
        SQLiteDatabase fantasticStoriesDataBase = this.getWritableDatabase();
        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put(RATING_COLUMN, rating);
        valuesToInsert.put(DATE_COLUMN, creationDateString);
        valuesToInsert.put(REVIEW_TITLE_COLUMN, reviewTitle);
        valuesToInsert.put(REVIEW_TEXT_COLUMN, reviewText);

        String whereClause = STORY_TITLE_COLUMN + " = \"" + chosenStoryTitle + "\" AND "
                + AUTHOR_SURNAME_COLUMN + " = \"" + chosenAuthor + "\"";
        long result = fantasticStoriesDataBase.update(REVIEWS_TABLE, valuesToInsert, whereClause, null );

        // if data was inserted incorrectly it wil return -1
        if (result == -1){
            return false;
        } else{
            return true;
        }
    }
}