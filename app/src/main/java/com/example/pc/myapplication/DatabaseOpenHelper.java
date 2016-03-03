package com.example.pc.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Huat Sin on 2/29/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;

    private static String DATABASE_NAME = "ElderlyTracker_db";

    private static String TABLE_NAME = "registration";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_USERNAME = "username";
    private static String COLUMN_PASSW = "password";
    private static String COLUMN_ROLE = "role";
    private static String TABLE_CREATE = "create table registration (id integer primary key not null, " +
            "name text not null, username text not null, password text not null, role text not null);";

    public DatabaseOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion)
    {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
    public void insertDetail(Detail d)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME,d.getFullname());
        values.put(COLUMN_USERNAME,d.getUsername());
        values.put(COLUMN_PASSW,d.getPassword());
        values.put(COLUMN_ROLE, d.getRole());

        db.insert(TABLE_NAME, null, values);
    }
    public String searchPassw(String username)
    {
        db = this.getReadableDatabase();
        String query =  "select username, password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String uname, pass = "";
        if(cursor.moveToFirst())
        {
            do{
                uname = cursor.getString(0);

                if(uname.equals(username))
                {
                    pass = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return pass;
    }

    public String searchRole(String username)
    {
        db = this.getReadableDatabase();
        String query =  "select username, role from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String uname, role = "";
        if(cursor.moveToFirst())
        {
            do{
                uname = cursor.getString(0);

                if(uname.equals(username))
                {
                    role = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return role;
    }

}
