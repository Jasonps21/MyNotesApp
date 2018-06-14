package com.example.jason.dicodingnotesapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NOTE = "note";

    public static final class NoteColums implements BaseColumns{
        //note title
        public static String TITLE = "title";
        //note description
        public static String DESCRIPTION = "description";
        //note date
        public static String DATE = "date";
    }

    public static final String AUTHORITY = "com.example.jason.mynotesapp";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
