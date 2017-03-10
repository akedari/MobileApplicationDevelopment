package com.cs442.akedari.assignment5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Abhishek on 2/27/2016.
 */
public class MyDB {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public final static String FOODMENU_TABLE="FoodMenu"; // name of table
    public final static String ORDERID="orderid"; // id value for employee
    public final static String ORDERTIMESTAMP="ordertimestamp";  // name of employee
    public final static String ORDEREDNAMES="orderednames";  // name of employee
    public final static String TOTALORDEREDPRICE="totalorderprice";

    public MyDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database =  dbHelper.getWritableDatabase();
    }

    public long createRecord(String id , String timestamp,String menus,String price){
        System.out.println("Inside createRecord");
        //System.out.println("ID:"+id);

        String[] col = new String[]{ORDERID,ORDERTIMESTAMP,ORDEREDNAMES,TOTALORDEREDPRICE};
        Cursor myCursor = database.query(true,FOODMENU_TABLE,col,null,null,null,null,null,null);
        if(myCursor!=null && myCursor.getCount()>0){
                myCursor.moveToLast();
                System.out.println(myCursor.getString(0));
                String orderid = myCursor.getString(0);
                int oid = Integer.parseInt(orderid);
                oid=oid+1;
                id = Integer.toString(oid); // String.valueOf(oid);
        }
        else
        {
                id = "1";
        }

        ContentValues values = new ContentValues();
        values.put(ORDERID,id);
        values.put(ORDERTIMESTAMP,timestamp);
        values.put(ORDEREDNAMES,menus);
        values.put(TOTALORDEREDPRICE,price);
        return  database.insert(FOODMENU_TABLE,null,values);
    }

    public Cursor selectRecord(){

        //System.out.println("\n Insdie the selectRecord() \n");
        String[] col = new String[]{ORDERID,ORDERTIMESTAMP,ORDEREDNAMES,TOTALORDEREDPRICE};
        Cursor myCursor = database.query(true,FOODMENU_TABLE,col,null,null,null,null,null,null);

        /*for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
            String id = myCursor.getString(myCursor.getColumnIndex(ORDERID));
            String timestamp = myCursor.getString(myCursor.getColumnIndex(ORDERTIMESTAMP));
            String menus = myCursor.getString(myCursor.getColumnIndex(ORDEREDNAMES));
            String price = myCursor.getString(myCursor.getColumnIndex(TOTALORDEREDPRICE));

            System.out.println(" ID - "+id);
            System.out.println(" ORDERTIMESTAMP - "+timestamp);
            System.out.println(" ORDEREDNAMES - "+menus);
            System.out.println(" TOTALORDEREDPRICE - "+price);
            System.out.println("");

        }*/
        //myCursor.close();
        return myCursor;
    }


}
