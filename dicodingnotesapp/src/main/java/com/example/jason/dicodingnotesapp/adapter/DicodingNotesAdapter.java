package com.example.jason.dicodingnotesapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.jason.dicodingnotesapp.R;

import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.DATE;
import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.DESCRIPTION;
import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.TITLE;
import static com.example.jason.dicodingnotesapp.DatabaseContract.getColumnString;

public class DicodingNotesAdapter extends CursorAdapter {
    public DicodingNotesAdapter(Context context, Cursor c, Boolean autoRecovery) {
        super(context, c, autoRecovery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dicoding_note, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvTitle = view.findViewById(R.id.tv_item_title);
            TextView tvDescription = view.findViewById(R.id.tv_item_description);
            TextView tvDate = view.findViewById(R.id.tv_item_date);

            tvTitle.setText(getColumnString(cursor, TITLE));
            tvDescription.setText(getColumnString(cursor, DESCRIPTION));
            tvDate.setText(getColumnString(cursor, DATE));
        }
    }
}
