package com.example.amatunimovsisyan.home22;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class AllUsersActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;
    private SimpleCursorAdapter simpleCursorAdapter;
    private String[] columns = {DBConstans.COLUMN_USERNAME, DBConstans.COLUMN_PASSWORD, DBConstans.COLUMN_ID};
    private int[] ids = {R.id.user_login, R.id.user_password, R.id.get_id};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cursor = db.query(DBConstans.TABLE_NAME, null, null, null, null, null, null);
        listView = (ListView) findViewById(R.id.listview);
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.all_users_list_item, cursor, columns, ids, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(simpleCursorAdapter);
        registerForContextMenu(listView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_all_users, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        long id = info.id;
        switch (item.getItemId()) {
            case R.id.context_delete:
                db.delete(DBConstans.TABLE_NAME, DBConstans.COLUMN_ID + "=" + id, null);
                Log.d("MyLog", "id to delete is " + id);
                cursor.requery();
                simpleCursorAdapter.notifyDataSetChanged();
                break;
            case R.id.context_edit:
                simpleCursorAdapter.getItem((int) id);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
