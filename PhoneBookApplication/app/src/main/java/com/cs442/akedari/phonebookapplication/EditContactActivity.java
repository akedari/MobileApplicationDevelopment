package com.cs442.akedari.phonebookapplication;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditContactActivity extends AppCompatActivity {
    int rawContactID;
    private int RESULT_CODE_CONFIRM=1;
    private int RESULT_CODE_CANCEL=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Button btnUpdate=(Button)findViewById(R.id.updateContact);
        //final int id = 1;

        final String lastname = "Dhanave";


        final ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        rawContactID = ops.size();
        final Intent intent = getIntent();
        Button addContact = (Button)findViewById(R.id.updateContact);
        final EditText name = (EditText) findViewById(R.id.tvUpdateName);
        final EditText phoneNumber=(EditText)findViewById(R.id.tvUpdatePhNumber);

        final String oldname = intent.getStringExtra("oldName");
        final String oldnumber = intent.getStringExtra("oldNumber");
        name.setText(oldname);
        phoneNumber.setText(oldnumber);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id=  intent.getIntExtra("idtoupdate", 0);

                final String firstname = name.getText().toString();
                final String number = phoneNumber.getText().toString();
                //final int = Integer.parseInt(temp);



                //Name
                ContentProviderOperation.Builder builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
                builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
                //builder.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastname);
                builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, firstname);
                ops.add(builder.build());

                //Number
                builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
                builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE+ "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)});
                builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
                ops.add(builder.build());

                System.out.println("tttt Name - "+firstname);
                System.out.println("tttt Phone number -"+number);
                //update
                try
                {
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                setResult(RESULT_CODE_CONFIRM, intent);
                finish();
            }
        });
    }
}
