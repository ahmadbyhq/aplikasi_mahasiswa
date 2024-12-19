package com.example.aplikasisqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    // Versi database
    private static final int DATABASE_VERSION = 2;

    // Nama database
    private static final String DATABASE_NAME = "digitaltalent.db";

    // Nama tabel dan kolom database
    public static final String TABLE_SQLite = "sqlite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    // Konstruktor untuk DbHelper
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method onCreate untuk membuat tabel database
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL" +
                " )";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    // Method onUpgrade untuk mengelola upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    // Method untuk mengambil semua data dari database
    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        try (Cursor cursor = database.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put(COLUMN_ID, cursor.getString(0));
                    map.put(COLUMN_NAME, cursor.getString(1));
                    map.put(COLUMN_ADDRESS, cursor.getString(2));
                    wordList.add(map);
                } while (cursor.moveToNext());
            }
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    // Method untuk menyisipkan data ke database
    public void insert(String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (" + COLUMN_NAME + ", " + COLUMN_ADDRESS + ") " +
                "VALUES ('" + name + "', '" + address + "')";

        Log.e("insert sqlite", queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    // Method untuk memperbarui data di database
    public void update(int id, String name, String address){
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_SQLite + " SET "
                + COLUMN_NAME + "='" + name + "', "
                + COLUMN_ADDRESS + "='" + address + "'"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    // Method untuk menghapus data dari database
    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + COLUMN_ID + "=" + id;

        Log.e("delete sqlite", deleteQuery);
        database.execSQL(deleteQuery);
        database.close();
    }
}
