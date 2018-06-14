package com.example.jason.mynotesapp;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NOTE = "note";

    static final class NoteColums implements BaseColumns{
        //note title
        static String TITLE = "title";
        //note description
        static String DESCRIPTION = "description";
        //note date
        static String DATE = "date";
    }
}
