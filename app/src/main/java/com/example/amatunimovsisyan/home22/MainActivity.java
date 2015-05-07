package com.example.amatunimovsisyan.home22;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private String username;
    private String password;
    private final String LOG = "MY Log";
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor cursor;
    private ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    String country;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_all_users, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d("MyLog", "menuItemSelected");
                break;
        }

        return true;
        // return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_regist).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cv = new ContentValues();
        registerForContextMenu(findViewById(R.id.edt_login));
        final String[] countries = {"", "USA", "ARM", "RUS"};
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);
        spinner = (Spinner) findViewById(R.id.country_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = (String) spinner.getItemAtPosition(position);
                Log.d("MyLog", "selected item is " + country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.edt_login).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActionMode(callback);
                return true;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:
                Intent intent = new Intent(MainActivity.this, RegistrActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_login:
                String usernameDB;
                String passwordDB;
                int id;
                cursor = db.query(DBConstans.TABLE_NAME, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        usernameDB = cursor.getString(cursor.getColumnIndex(DBConstans.COLUMN_USERNAME));
                        passwordDB = cursor.getString(cursor.getColumnIndex(DBConstans.COLUMN_PASSWORD));
                        Log.d(LOG, "username " + usernameDB + "  " + "password " + passwordDB);
                        username = ((EditText) findViewById(R.id.edt_login)).getText().toString();
                        password = ((EditText) findViewById(R.id.edt_pass)).getText().toString();
                        if (usernameDB.equals(username) && passwordDB.equals(password)) {
                            id = cursor.getCount();

                            Log.d(LOG, "username " + usernameDB + "  " + "password " + passwordDB);
                            Log.d(LOG, "id=" + id);
                            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", id);
                            intent1.putExtras(bundle);
                            startActivity(intent1);
                        } else {
                            Log.d(LOG, "chilnum");

                        }
                    }
                    while (cursor.moveToNext());
                } else {
                    Log.d(LOG, "no users");
                    Toast.makeText(getApplicationContext(), "No Users!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            Toast.makeText(getApplicationContext(), "Registration Success!", Toast.LENGTH_SHORT).show();
    }

    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add("edit");
            menu.add(0,1,0,"delete");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch ( item.getItemId())
            {case 0 :
                Log.d("MyLog", "edit item");
                break;
            case 1:
                Log.d("MyLog", "delete item");
                break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };


}