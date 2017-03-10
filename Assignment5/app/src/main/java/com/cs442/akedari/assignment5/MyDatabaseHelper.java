package com.cs442.akedari.assignment5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Abhishek on 2/27/2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "FoodDatabase";
    private static final int DATABASE_VERSION = 3;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table FoodMenu ( orderid text primary key,ordertimestamp text not null, orderednames text, totalorderprice text);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Inside onCreate()");
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS FoodMenu");
        onCreate(db);
    }
}
