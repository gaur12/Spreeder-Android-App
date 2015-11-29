package com.example.user.spreeder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 09-Oct-15.
 */
public class TblPassage extends SQLiteOpenHelper{
    public static final String TABLE_PASSAGE = "tbl_passages";
    public static final String STRING = "strings";
    SQLiteDatabase db = this.getReadableDatabase();
    SQLiteDatabase db1 = this.getWritableDatabase();

    TblPassage(Context context) {
        super(context, TABLE_PASSAGE, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PASSAGES);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // create new tables

    }
    // create table query
    public static final String CREATE_TABLE_PASSAGES = "create table if not exists " + TABLE_PASSAGE + "("
           + STRING + " text not null);";

    public Cursor fetchPassages() {
        String strSQL = "SELECT * FROM " + TABLE_PASSAGE + ";";
        Cursor pCur = db.rawQuery(strSQL,null);
        return pCur;
    }
    public void insertPassage(String a) {
       //String strSQL = "insert into " + TABLE_PASSAGE + " (" + STRING + ", "
         //       + ") value(" + a + ");";
        // db1.execSQL(strSQL);
        ContentValues values = new ContentValues();
        values.put(STRING, a);
        db1.insert(TABLE_PASSAGE,null,values);
    }
}
