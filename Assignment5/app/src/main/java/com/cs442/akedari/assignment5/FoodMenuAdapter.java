package com.cs442.akedari.assignment5;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuAdapter extends ArrayAdapter<String> {

    public ArrayList<String> menulist = new ArrayList<String>();
    private static LayoutInflater mInflater;

    public FoodMenuAdapter(Context context, int resource,ArrayList<String> itemsToSet) {
        super(context, resource);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menulist=itemsToSet;
    }

    public void addItem(final String item) {
        menulist.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return menulist.size();
    }

    @Override
    public String getItem(int position) {
        return menulist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("my view"+position + " "+ convertView);
        View view= convertView;

        if(convertView ==null)
        {
            view = mInflater.inflate(R.layout.activity_food_menu_adapter,null);
        }

        TextView orderID = (TextView) view.findViewById(R.id.orderID);
        TextView OrderName = (TextView) view.findViewById((R.id.orderName));
        TextView OrderBill = (TextView) view.findViewById(R.id.orderBill);
        TextView OrderTimeStamp = (TextView) view.findViewById((R.id.orderTimeStamp));

        MyDB myDB = new MyDB(this.getContext());
        Cursor myCursor = myDB.selectRecord();

        if(myCursor!=null && myCursor.getCount()>0){
            for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
                orderID.setText(myCursor.getString(0));
                OrderTimeStamp.setText(myCursor.getString(1));
                OrderName.setText(myCursor.getString(2));
                OrderBill.setText(myCursor.getString(3));
            }
        }

        return view;
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu_adapter);
    }*/
}
