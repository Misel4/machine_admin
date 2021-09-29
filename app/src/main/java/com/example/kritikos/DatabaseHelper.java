package com.example.kritikos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "machine.db";
    public static final String TABLE_NAME = "machine_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "MACHINE";
    public static final String COL_3 = "DATES";
    public static final String COL_4 = "NOTES";
    public static final String COL_5 = "CATEGORY";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,MACHINE VARCHAR,DATES DATE,NOTES TEXT,CATEGORY VARCHAR)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getReadableDatabase();
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }

    public boolean insertData( String date, String machine,String category , CharSequence notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,machine);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4,(String)notes);
        contentValues.put(COL_5,category);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * FROM " + TABLE_NAME ,null);
        return res;
    }

    public Cursor getItemID(String machine){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_1 + " FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " = '" + machine + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteItem(int id, String machine){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL_1 + " = '" + id + "'" +
                " AND " + COL_2 + " = '" + machine + "'";
        db.execSQL(query);
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_2 +
                " = '" + newName + "' WHERE " + COL_1 + " = '" + id + "'" +
                " AND " + COL_2 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void updateDate(String newDate, int id, String oldDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_3 +
                " = '" + newDate + "' WHERE " + COL_1 + " = '" + id + "'" +
                " AND " + COL_3 + " = '" + oldDate + "'";
        db.execSQL(query);
    }

    public void updateNote(String newNote, int id, String oldNote){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_4 +
                " = '" + newNote + "' WHERE " + COL_1 + " = '" + id + "'" +
                " AND " + COL_4 + " = '" + oldNote + "'";
        db.execSQL(query);
    }

}
