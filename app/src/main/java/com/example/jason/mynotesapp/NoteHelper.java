package com.example.jason.mynotesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jason.mynotesapp.entity.note;

import java.util.ArrayList;

//import static android.provider.BaseColumns._ID;
//import static android.provider.MediaStore.Files.FileColumns.TITLE;
//import static android.provider.MediaStore.Images.ImageColumns.DESCRIPTION;
//import static android.provider.Telephony.BaseMmsColumns.DATE;
import static android.provider.BaseColumns._ID;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.DATE;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.DESCRIPTION;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.TITLE;
import static com.example.jason.mynotesapp.DatabaseContract.TABLE_NOTE;

public class NoteHelper {
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public NoteHelper(Context context) {
        this.context = context;
    }

    public NoteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public ArrayList<note> query() {
        ArrayList<note> arrayList = new ArrayList<note>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        note notes;
        if (cursor.getCount() > 0) {
            do {
                notes = new note();
                notes.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                notes.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                notes.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                notes.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                arrayList.add(notes);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(note notes) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, notes.getTitle());
        initialValues.put(DESCRIPTION, notes.getDescription());
        initialValues.put(DATE, notes.getDate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(note notes) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, notes.getTitle());
        initialValues.put(DESCRIPTION, notes.getDescription());
        initialValues.put(DATE, notes.getDate());
        return database.update(DATABASE_TABLE, initialValues, _ID + " = '" + notes.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NOTE, _ID + " ='" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values,_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID +" = ?", new String[]{id});
    }
}
