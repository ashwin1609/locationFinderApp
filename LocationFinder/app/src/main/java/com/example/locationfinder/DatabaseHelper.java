package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public Context context;
    public static final String DATABASE_NAME = "Address.db";
    public static final String TABLE_NAME = "Location_Table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "Address";
    public static final String COL_3 = "Latitude";
    public static final String COL_4 = "Longitude";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT," + COL_3 + " TEXT," + COL_4 + " TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" drop Table if exists " + TABLE_NAME );
    }

    public Boolean insertDetails(String COL_2, String COL_3, String COL_4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Address", COL_2);
        values.put("Latitude", COL_3);
        values.put("Longitude", COL_4);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean deleteDetails(String COL_2){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Location_Table where Address = ?", new String[]{COL_2});
        if (cursor.getCount() > 0) {
            long result = db.delete(TABLE_NAME, "Address=?", new String[]{COL_2});
            if (result == -1){
                return false;
            } else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updateDetails(String COL_2, String COL_3, String COL_4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Latitude", COL_3);
        values.put("Longitude", COL_4);
        Cursor cursor = db.rawQuery("Select * from Location_Table where Address = ?", new String[]{COL_2});
        if (cursor.getCount() > 0) {
            long result = db.update(TABLE_NAME, values, "Address=?", new String[]{COL_2});
            if (result == -1){
                return false;
            } else{
                return true;
            }
        }else{
            return false;
        }
    }

    Cursor searchDetails(String COL_2){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Location_Table where Address = ?", new String[]{COL_2});
        return cursor;
    }

    public Cursor getDetails(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Location_Table", null);
        return cursor;

    }
}
