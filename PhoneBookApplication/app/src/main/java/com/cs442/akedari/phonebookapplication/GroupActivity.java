package com.cs442.akedari.phonebookapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GroupActivity extends AppCompatActivity {
   /* ArrayList<String> al;
    ArrayAdapter<String> ad;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        al=new ArrayList<String>();
        Intent intent=getIntent();
        al=intent.getStringArrayListExtra("Array");
        ad=new ArrayAdapter<String>(this,R.layout.grouplist,al);
        lv=(ListView) findViewById(R.id.lstGroup);
        lv.setAdapter(ad);
    }
}*/

    public ListView outputList;
    private ArrayAdapter<String> itemsAdapter;
    ArrayList<String> groups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        outputList = (ListView) findViewById(R.id.listView2);

        groups=new ArrayList<String>();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,groups);
        outputList.setAdapter(itemsAdapter);

        outputList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getItemAtPosition(position);
                Intent intent2 = new Intent(GroupActivity.this, GroupMemberActivity.class);
                intent2.putExtra("group",item);
                startActivityForResult(intent2, 1);


                return true;
            }
        });
        fetchgroups();

    }

    public void fetchgroups(){
        final String[] GROUP_PROJECTION = new String[] {ContactsContract.Groups._ID, ContactsContract.Groups.TITLE };
        Cursor gC = getContentResolver().query(ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION,null,null,null);
        gC.moveToFirst();
        while (!gC.isAfterLast()) {
            int idcolumn = gC.getColumnIndex(ContactsContract.Groups.TITLE);
            String Id = gC.getString(idcolumn);
            groups.add(Id);
            gC.moveToNext();
        }
        LinkedHashSet<String> s = new LinkedHashSet<String>();
        s.addAll(groups);
        groups.clear();
        groups.addAll(s);
        itemsAdapter.notifyDataSetChanged();
    }

}
