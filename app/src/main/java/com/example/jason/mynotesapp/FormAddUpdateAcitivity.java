package com.example.jason.mynotesapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.jason.mynotesapp.entity.note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.jason.mynotesapp.DatabaseContract.CONTENT_URI;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.DATE;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.DESCRIPTION;
import static com.example.jason.mynotesapp.DatabaseContract.NoteColums.TITLE;

public class FormAddUpdateAcitivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTitle, edtDescription;
    Button btnSubmit;

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private note notes;
    //private int position;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_update_acitivity);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null,null);
            if(cursor!=null){
                if(cursor.moveToFirst())notes = new note(cursor);
                cursor.close();
            }
        }

        String actionBarTitle = null;
        String btnTitle = null;

        if (notes!=null) {
            isEdit = true;

            actionBarTitle = "Ubah";
            btnTitle = "Edit";

            edtTitle.setText(notes.getTitle());
            edtDescription.setText(notes.getDescription());
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setText(btnTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null) {
            noteHelper.close();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            boolean isEmpty = false;

            if (TextUtils.isEmpty(title)) {
                isEmpty = true;
                edtTitle.setError("Field Can not be blank");
            }

            if (!isEmpty) {

                //gunakan contentValue untuk menampung data
                ContentValues values = new ContentValues();
                values.put(TITLE, title);
                values.put(DESCRIPTION, description);

                if(isEdit){
                    getContentResolver().update(getIntent().getData(), values, null, null);
                    setResult(RESULT_UPDATE);
                    finish();
                }else {
                    values.put(DATE, getCurrentDate());
                    getContentResolver().insert(CONTENT_URI, values);
                    setResult(RESULT_ADD);
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    final int ALERT_DIALOG_DELETE = 20;
    final int ALERT_DIALOG_CLOSE = 10;

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
        super.onBackPressed();
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null, dialogMessage = null;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogTitle = "Hapus Note";
            dialogMessage = "Apakah anda ingin menghapus note ini?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            getContentResolver().delete(getIntent().getData(), null, null);
                            setResult(RESULT_DELETE, null);
                            finish();
                        }
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

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }
}
