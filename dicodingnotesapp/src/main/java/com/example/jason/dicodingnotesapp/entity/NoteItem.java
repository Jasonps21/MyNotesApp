package com.example.jason.dicodingnotesapp.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.jason.dicodingnotesapp.DatabaseContract;

import static com.example.jason.dicodingnotesapp.DatabaseContract.getColumnInt;
import static com.example.jason.dicodingnotesapp.DatabaseContract.getColumnString;

public class NoteItem implements Parcelable {
    private int id;
    private String title, description, date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(date);
    }

    public NoteItem() {

    }

    public NoteItem(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.NoteColums._ID);
        this.title = getColumnString(cursor, DatabaseContract.NoteColums.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NoteColums.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.NoteColums.DATE);
    }

    protected NoteItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<NoteItem> CREATOR = new Creator<NoteItem>() {
        @Override
        public NoteItem createFromParcel(Parcel in) {
            return new NoteItem(in);
        }

        @Override
        public NoteItem[] newArray(int size) {
            return new NoteItem[size];
        }
    };
}
