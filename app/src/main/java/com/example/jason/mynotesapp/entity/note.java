package com.example.jason.mynotesapp.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.jason.mynotesapp.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.example.jason.mynotesapp.DatabaseContract.getColumnInt;
import static com.example.jason.mynotesapp.DatabaseContract.getColumnString;

public class note implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String date;

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
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.date);
    }

    public note() {

    }

    public note(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.NoteColums.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NoteColums.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.NoteColums.DATE);
    }

    protected note(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Creator<note> CREATOR = new Creator<note>() {
        @Override
        public note createFromParcel(Parcel in) {
            return new note(in);
        }

        @Override
        public note[] newArray(int size) {
            return new note[size];
        }
    };
}
