package com.ng.android.noteapp;

import java.util.ArrayList;

public class AllNotes { // should I implement Serializable//

    public static ArrayList<String> notesArrayList = new ArrayList<>();

        public void AllNotes() {
    }

    public static ArrayList<String> getNotesArrayList() {
        return notesArrayList;
    }

    public static void setNotesArrayList(ArrayList<String> notesArrayList) {
        AllNotes.notesArrayList = notesArrayList;
    }

    public void addNote(String note){
        notesArrayList.add(note);
    }
}




