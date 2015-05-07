package com.example.amatunimovsisyan.home22;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private final String LOG = "MY Log";

    public DBHelper(Context context) {
        super(context, DBConstans.DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG, "database created");
        String query = "CREATE TABLE `users` (" +
                "`_id`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`username`TEXT NOT NULL," +
                "`password`TEXT NOT NULL" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

