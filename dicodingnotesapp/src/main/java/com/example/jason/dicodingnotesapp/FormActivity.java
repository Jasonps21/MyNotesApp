package com.example.jason.dicodingnotesapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.dicodingnotesapp.entity.NoteItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.jason.dicodingnotesapp.DatabaseContract.CONTENT_URI;
import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.DATE;
import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.DESCRIPTION;
import static com.example.jason.dicodingnotesapp.DatabaseContract.NoteColums.TITLE;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTitle, edtDescription;
    Button btnsubmit;

    public static String EXTRA_NOTE_ITEM = "extra_note_item";
    private NoteItem noteItem = null;
    private Boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        btnsubmit = findViewById(R.id.btn_submit);
        btnsubmit.setOnClickListener(this);

        Uri uri = getIntent().getData();

        if(uri!=null){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if(cursor!=null){
                if(cursor.moveToFirst())noteItem = new NoteItem(cursor);
                cursor.close();
            }
        }

        String actionBarTitle = null;
        String btnActionTitle = null;
        if(noteItem!=null){
            isUpdate = true;

            actionBarTitle = "Update";
            btnActionTitle = "Simpan";

            edtTitle.setText(noteItem.getTitle());
            edtDescription.setText(noteItem.getDescription());
        }else {
            actionBarTitle = "Tambah Baru";
            btnActionTitle = "Submit";
        }

        btnsubmit.setText(btnActionTitle);
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_submit){
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            boolean isEmptyField = false;
            if(TextUtils.isEmpty(title)){
                isEmptyField = true;
                edtTitle.setError("Field tidak boleh kosong");
            }

            if(!isEmptyField){
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE, title);
                contentValues.put(DESCRIPTION, description);
                contentValues.put(DATE, getCurrentDate());

                if(isUpdate){
                    Uri uri = getIntent().getData();
                    getContentResolver().update(uri,contentValues,null, null);
                    Toast.makeText(this, "Satu catatan berhasil diupdate", Toast.LENGTH_SHORT).show();
                }else {
                    getContentResolver().insert(CONTENT_URI, contentValues);
                    Toast.makeText(this, "Satu catatan berhasil ditambah", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        }
    }

    public String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isUpdate){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        if(item.getItemId() == R.id.action_delete){
            showDeleteAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteAlertDialog() {
        String dialogMessage = "Apakah anda yakin ingin menghapus item ini ?";
        String dialogTitle = "Hapus Note";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = getIntent().getData();
                        getContentResolver().delete(uri,null, null);
                        Toast.makeText(FormActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}