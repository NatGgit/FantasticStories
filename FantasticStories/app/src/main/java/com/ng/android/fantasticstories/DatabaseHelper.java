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
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String DB_PATH;
    private static final String DB_NAME = "FS_database.db";
    private SQLiteDatabase fantasticDatabase;
    private final Context context;

    // that's the list of columns in the table
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
            fantasticDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
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
        fantasticDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READONLY);
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
            return true;
        }
    }

    //gets all the data from the database
    public Cursor getallStoryData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = fantasticDatabase.rawQuery(query, null);
        return data;
    }

    //gets the year from the database.
    // Includes the _id column in the sqlite query for SimpleCursorAdapter to work properly
    public Cursor getYear (){
        String query = "SELECT " + ZERO_COLUMN_NAME + ", " + FIRST_COLUMN_NAME + " FROM " + TABLE_NAME
                + " GROUP BY " + FIRST_COLUMN_NAME + " ORDER BY " + FIRST_COLUMN_NAME + " DESC";
        Cursor data = fantasticDatabase.rawQuery(query, null);
        return data;
    }

    // gets the issue numbers from a specific year from the database.
    // Includes the _id column in the sqlite query for SimpleCursorAdapter to work properly
    public Cursor getIssueNumbers (String chosenYear){
        // if year is null, we get runtime exception, so to avoid it it sets year to current year
        if(chosenYear != null){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            chosenYear = Integer.toString(year);
        }
        String query = "SELECT " + ZERO_COLUMN_NAME + ", " + SECOND_COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE "
                + FIRST_COLUMN_NAME + " = " + chosenYear + " GROUP BY " + SECOND_COLUMN_NAME;
        Cursor data = fantasticDatabase.rawQuery(query, null);
        return data;
    }
}