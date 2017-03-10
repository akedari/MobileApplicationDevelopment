package com.cs442.akedari.assignment5;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class orderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        /*setResult(1, null);
        finish();*/


        ListView listView = (ListView)findViewById(R.id.listView2);

        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);

        MyDB myDB = new MyDB(getApplicationContext());
        Cursor myCursor = myDB.selectRecord();

        if(myCursor!=null && myCursor.getCount()>0){
            for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {

                list.add(myCursor.getString(0)+" \t" + myCursor.getString(2)+"\n " +
                        myCursor.getString(1)+" \t$"+ myCursor.getString(3));
            }
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /*public void finishorderHistoryActivity(View v) {
        orderHistoryActivity.this.finish();
    }*/
}
