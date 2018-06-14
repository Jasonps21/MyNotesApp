package com.example.jason.mynotesapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jason.mynotesapp.entity.note;

import java.util.LinkedList;

import static com.example.jason.mynotesapp.DatabaseContract.CONTENT_URI;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private Cursor listNotes;
    private Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {
        final note notes = getItem(position);
        holder.tvtitle.setText(notes.getTitle());
        holder.tvDescription.setText(notes.getDescription());
        holder.tvDate.setText(notes.getDate());
        holder.cvNote.setOnClickListener(new CostumOnItemClickListener(position, new CostumOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormAddUpdateAcitivity.class);
                Uri uri = Uri.parse(CONTENT_URI+"/"+notes.getId());
                intent.setData(uri);
                activity.startActivityForResult(intent, FormAddUpdateAcitivity.REQUEST_UPDATE);
            }
        }));
    }

    private note getItem(int position) {
        if(!listNotes.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new note(listNotes);
    }

    @Override
    public int getItemCount() {
        if(listNotes == null) return 0;
        return listNotes.getCount();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle, tvDescription, tvDate;
        CardView cvNote;
        public NoteViewHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tv_item_title);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
