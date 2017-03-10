package com.cs442.akedari.kedari_abhijeet_a6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    //Calling this method on Start of Service
    public  void startService(View view)
    {
        int countStart;
        EditText editText = (EditText)findViewById(R.id.editText);

        Intent intent = new Intent(this,MyService.class);
        intent.setType("text/plain");

        try
        {
            Integer.parseInt(editText.getText().toString());
            Log.i("entered value", "value is numerical");
            intent.putExtra("value",Integer.parseInt(editText.getText().toString()));
        }
        catch (NumberFormatException e)
        {
            Log.i("entered value", "value isn't numeric so it will start with 1");
            intent.putExtra("value",1);
        }
        startService(intent);

    }

    //Calling this method on Stop of Service
    public  void stopService(View view)
    {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }
}
