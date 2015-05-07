package com.example.amatunimovsisyan.home22;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegistrActivity extends ActionBarActivity implements View.OnClickListener {
    private  String username;
    private String password;
    private final String LOG = "MY Log";
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        findViewById(R.id.btn_regist_succes).setOnClickListener(this);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cv = new ContentValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist_succes:
                Long id;
                cv.clear();
                username = ((EditText) findViewById(R.id.edt_setUser)).getText().toString();
                password = ((EditText) findViewById(R.id.edt_setPass)).getText().toString();
                cv.put(DBConstans.COLUMN_USERNAME,username);
                cv.put(DBConstans.COLUMN_PASSWORD,password);
                id= db.insert(DBConstans.TABLE_NAME,"",cv);
                Log.d(LOG, "inserted row id is:" + id);
                ((EditText) findViewById(R.id.edt_setUser)).setText(" ");
                ((EditText) findViewById(R.id.edt_setPass)).setText(" ");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}

