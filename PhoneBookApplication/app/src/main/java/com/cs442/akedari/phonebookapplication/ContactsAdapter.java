package com.cs442.akedari.phonebookapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 3/6/2016.
 */
public class ContactsAdapter  extends ArrayAdapter<Contact> {

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contact contact = getItem(position);

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_contact, parent, false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);

        tvName.setText(contact.name);
        tvPhone.setText("");

        //System.out.println("*****************************************" + contact.numbers.size());

        if (contact.numbers.size() > 0 && contact.numbers.get(0) != null) {
            tvPhone.setText(contact.numbers.get(0).number);

        }
        return view;
    }
}
