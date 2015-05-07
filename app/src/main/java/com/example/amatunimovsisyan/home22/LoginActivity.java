package com.example.amatunimovsisyan.home22;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Amatuni.Movsisyan on 4/25/2015.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        findViewById(R.id.btn_upd).setOnClickListener(this);
        findViewById(R.id.btn_users).setOnClickListener(this);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cv = new ContentValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_users:
                Intent intent = new Intent(this, AllUsersActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_upd:
                Intent intent2=getIntent();
                int id=intent2.getIntExtra("id",-1);
                String updateUsername = ((EditText) findViewById(R.id.edt_updUser)).getText().toString();
                String updatePassword = ((EditText) findViewById(R.id.edt_updPass)).getText().toString();
                cv.clear();
                cv.put(DBConstans.COLUMN_USERNAME, updateUsername);
                cv.put(DBConstans.COLUMN_PASSWORD, updatePassword);
                db.update(DBConstans.TABLE_NAME, cv,  "id="+ id, null);
                Toast.makeText(getApplicationContext(), "Username and Password changed!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
