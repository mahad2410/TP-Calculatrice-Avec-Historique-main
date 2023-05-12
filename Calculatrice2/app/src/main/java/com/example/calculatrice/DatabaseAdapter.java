package com.example.calculatrice;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myCalculatrice.db";
    private static final String TABLE_NAME = "HistoriqueCalcul";

    private static final String ID = "_id";
    private static final String EXPRESS = "_expression";
    private static final String RESULTAT = "_resultat";
    SQLiteDatabase db;
    Context context;
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + ID + " integer primary key autoincrement, " + EXPRESS + " text, " + RESULTAT + " text)";

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String expression, String resultat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseAdapter.EXPRESS, expression);
        cv.put(DatabaseAdapter.RESULTAT, resultat);
        long result = db.insert(DatabaseAdapter.TABLE_NAME, null, cv);
        return result != -1;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "select * from HistoriqueCalcul";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> Historiques = new HashMap<>();
            Historiques.put("_id", cursor.getString(cursor.getColumnIndex(ID)));
            Historiques.put("_expression", cursor.getString(cursor.getColumnIndex(EXPRESS)));
            Historiques.put("_resultat", cursor.getString(cursor.getColumnIndex(RESULTAT)));
            userList.add(Historiques);
        }
        return userList;
    }


}
