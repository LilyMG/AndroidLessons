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
    private long currentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cursor = db.query(DBConstans.TABLE_NAME, null, null, null, null, null, null);
        listView = (ListView) findViewById(R.id.listview);
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.all_users_list_item, cursor, columns, ids);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItem = id-1;
            }
        });
        registerForContextMenu(listView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // super.onCreateContextMenu(menu, v, menuInfo);.
        getMenuInflater().inflate(R.menu.menu_all_users, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_delete:
                Log.d("MyLog", "item for delete is : " + currentItem);
                View view = listView.getChildAt((int) currentItem).findViewById(R.id.get_id);
                String id = ((TextView) view).getText().toString();
                db.delete(DBConstans.TABLE_NAME, DBConstans.COLUMN_ID + "=" + id, null);
                Log.d("MyLog", "id to delete is " + id);
                simpleCursorAdapter.notifyDataSetChanged();
                break;
            case R.id.context_edit:
                break;

        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
