package com.ng.android.fantasticstories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path to application database.
    private static final String DB_PATH = "/data/data/com.ng.android.fantasticstories/databases/";
    private static final String DB_NAME = "FS_database.db";

    // that's the list of columns in the table
    private final String TAG = "DataBaseHelper";
    private final String TABLE_NAME = "Reviews";
    private final String ZERO_COLUMN_NAME = "_id";
    private final String FIRST_COLUMN_NAME = "Year";
    private final String SECOND_COLUMN_NAME = "Issue";
    private final String THIRD_COLUMN_NAME = "StoryAuthorFirstName";
    private final String FORTH_COLUMN_NAME = "StoryAuthorSurname";
    private final String FIFTH_COLUMN_NAME = "StoryTitle";
    private final String SIXTH_COLUMN_NAME = "StoryOriginalTitle";
    private final String SEVENTH_COLUMN_NAME = "OriginalLanguage";
    private final String EIGHTH_COLUMN_NAME = "Rating";
    private final String NINTH_COLUMN_NAME = "ReviewCreationDate";
    private final String TENTH_COLUMN_NAME = "ReviewTitle";
    private final String ELEVENTH_COLUMN_NAME = "ReviewText";

    private SQLiteDatabase fantasticStoriesDataBase;
    private final Context context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application
     * assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME,null, 1);
        this.context = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            //does nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of the application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
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
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        //Opens the local database as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Path to the just created empty database
        String outFileName = DB_PATH + DB_NAME;
        //Opens the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfers bytes from the inputfile to the outputfile
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

    public void openDataBase() throws SQLiteException{
        //Opens the database
        String myPath = DB_PATH + DB_NAME;
        fantasticStoriesDataBase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(fantasticStoriesDataBase != null)
            fantasticStoriesDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public boolean addReview (int rating, String reviewTitle, String reviewText) {
        //gets the time of creation of the review from the system and saves it as a string
        Date creationDate = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String creationDateString = dateFormat.format(creationDate);

        SQLiteDatabase fantasticStoriesDataBase = this.getWritableDatabase();
        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put(EIGHTH_COLUMN_NAME, rating);
        valuesToInsert.put(NINTH_COLUMN_NAME, creationDateString);
        valuesToInsert.put(TENTH_COLUMN_NAME, reviewTitle);
        valuesToInsert.put(ELEVENTH_COLUMN_NAME, reviewText);
        long result = fantasticStoriesDataBase.insert(TABLE_NAME,null, valuesToInsert);

        // if data was inserted incorrectly it wil return -1
        if (result == -1){
            return false;
        } else{
            Log.d(TAG, "addData: Adding rating " + rating + " title " + reviewTitle + " text "
                    + reviewText + TABLE_NAME );
            return true;
        }
    }

    //gets all the data from the database
    public Cursor getallStoryData(){
        SQLiteDatabase fantasticStoriesDataBase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = fantasticStoriesDataBase.rawQuery(query, null);
        return data;
    }

    //gets the issue numbers from a specific year from the database
    public Cursor getIssueNumbers (String chosenYear){
        SQLiteDatabase fantasticStoriesDataBase = this.getWritableDatabase();
        String query = "SELECT " + SECOND_COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + FIRST_COLUMN_NAME + " = " + chosenYear;
        Cursor data = fantasticStoriesDataBase.rawQuery(query, null);
        return data;
    }
}