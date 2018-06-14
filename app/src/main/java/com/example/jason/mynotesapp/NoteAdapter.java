package com.example.jason.mynotesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jason.mynotesapp.entity.note;

import java.util.LinkedList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private LinkedList<note> listNotes;
    private Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public LinkedList<note> getListNotes() {
        return listNotes;
    }

    public void setListNotes(LinkedList<note> listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {

        holder.tvtitle.setText(getListNotes().get(position).getTitle());
        holder.tvDescription.setText(getListNotes().get(position).getDescription());
        holder.tvDate.setText(getListNotes().get(position).getDate());
        holder.cvNote.setOnClickListener(new CostumOnItemClickListener(position, new CostumOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormAddUpdateAcitivity.class);
                intent.putExtra(FormAddUpdateAcitivity.EXTRA_NOTE, getListNotes().get(position));
                intent.putExtra(FormAddUpdateAcitivity.EXTRA_POSITION, position);
                activity.startActivityForResult(intent, FormAddUpdateAcitivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
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
