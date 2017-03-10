package com.cs442.akedari.phonebookapplication;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GroupMemberActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
    }*/

    public ListView outputList;
    TextView textview;
    private ArrayAdapter<String> itemsAdapter;
    ArrayList<String> groups;
    String group;
    ArrayList<String> contactList=new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        outputList = (ListView) findViewById(R.id.listView3);
        textview = (TextView) findViewById(R.id.textView1);



        groups=new ArrayList<String>();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,groups);
        outputList.setAdapter(itemsAdapter);

        Intent intent = getIntent();
        group=intent.getStringExtra("group");
        textview.setText("Members of Group: "+group);

        getSampleContactList(Integer.parseInt(getGroupId(group)));
    }


    public void fetchgroupMembers(){
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

    public void getSampleContactList(int groupID) {


        Uri groupURI = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID };

        Cursor c = getContentResolver().query(
                groupURI,
                projection,
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                        + "=" + groupID, null, null);

        while (c.moveToNext()) {
            String id = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));
            Cursor pCur = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[] { id }, null);

            while (pCur.moveToNext()) {

                String Name = pCur
                        .getString(pCur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String PhoneNumber = pCur
                        .getString(pCur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactList.add(Name+"\n"+PhoneNumber);
            }

            pCur.close();

            groups.addAll(contactList);
            itemsAdapter.notifyDataSetChanged();

        }
    }


    private String getGroupId(String groupTitle) {
        Cursor cursor = getContentResolver().query(ContactsContract.Groups.CONTENT_URI,new String[]{ContactsContract.Groups._ID,ContactsContract.Groups.TITLE}, null, null, null);
        cursor.moveToFirst();
        int len = cursor.getCount();

        String groupId = null;
        for (int i = 0; i < len; i++) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));

            if (title.equals(groupTitle)) {
                groupId = id;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();

        return groupId;
    }


}


