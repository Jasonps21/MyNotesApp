package com.example.jason.mynotesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jason.mynotesapp.entity.note;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.example.jason.mynotesapp.FormAddUpdateAcitivity.REQUEST_UPDATE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvNote;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    private LinkedList<note> list;
    public NoteAdapter adapter;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Notes");

        rvNote = findViewById(R.id.rv_notes);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setHasFixedSize(true);
        progressBar = findViewById(R.id.progress_bar);
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        noteHelper = new NoteHelper(this);
        noteHelper.open();
        list = new LinkedList<>();

        adapter = new NoteAdapter(this);
        adapter.setListNotes(list);
        rvNote.setAdapter(adapter);

        new LoadNoteAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab_add){
            Intent intent = new Intent(this, FormAddUpdateAcitivity.class);
            startActivityForResult(intent, FormAddUpdateAcitivity.REQUEST_ADD);
        }
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<note>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if(list.size()>0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<note> doInBackground(Void... voids) {
            return noteHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<note> notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);
            list.addAll(notes);
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            if(list.size()==0){
                showSnackbarMessage("Tidak Ada data saat ini");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==FormAddUpdateAcitivity.REQUEST_ADD){
            if(resultCode == FormAddUpdateAcitivity.RESULT_ADD){
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil ditambah");
            }
        }else if(requestCode == REQUEST_UPDATE){
            if(resultCode == FormAddUpdateAcitivity.RESULT_UPDATE){
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil diupdate");
            }else if(resultCode == FormAddUpdateAcitivity.RESULT_DELETE){
                int position = data.getIntExtra(FormAddUpdateAcitivity.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListNotes(list);
                adapter.notifyDataSetChanged();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noteHelper!=null){
            noteHelper.close();
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNote, message, Snackbar.LENGTH_SHORT).show();
    }
}
