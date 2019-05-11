package com.ng.android.noteapp;

import java.util.ArrayList;

/**
 * Contains the notes created and saved by the user in AddNoteActivity.
 */
public class AllNotes {
    /**
     * Stores all notes inserted by the user.
     */
    static ArrayList<String> notesArrayList = new ArrayList<>();

    /**
     * Constructor with no parameters.
     */
    public AllNotes() {
    }

    /**
     * Allows to get the notes saved by the user.
     * @return this ArrayList
     */
    public static ArrayList<String> getNotesArrayList() {
        return notesArrayList;
    }

    /**
     * Allows to substitute the saved notes by own notes.
     * @param notesArrayList the ArrayList to set.
     */
    public static void setNotesArrayList(ArrayList<String> notesArrayList) {
        AllNotes.notesArrayList = notesArrayList;
    }

    /**
     * Allows to save notes in the notesArrayList.
     * @param note the text inserted by a user in MainActivity.
     */
    public void addNote(String note){
        notesArrayList.add(note);
    }
}




