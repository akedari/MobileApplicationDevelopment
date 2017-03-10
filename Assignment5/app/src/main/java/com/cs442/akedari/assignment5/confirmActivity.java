package com.cs442.akedari.assignment5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class confirmActivity extends AppCompatActivity {

    private Button btnConfirmOrder;
    private Button btnCancelOrder;
    private TextView textView;

    private static int oldValue = 0 ;
    private static int newValue = 0 ;

    private int RESULT_CODE_CONFIRM=1;
    private int RESULT_CODE_CANCEL=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        btnConfirmOrder = (Button)findViewById(R.id.button3);
        btnCancelOrder =  (Button)findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        int count = intent.getIntExtra("count", 0);
        int checked= intent.getIntExtra("Selected",0);
        textView.setText("Total items selected are: " + checked);
        StringBuilder str= new StringBuilder();
        final StringBuilder ordered_names_Column = new StringBuilder();
        final String total_order_price_Column;
        final String order_time_stamp_Column = getDateTime();
        final String order_id_Column = getDateTime() ;
        final String temp;

        //System.out.println("\n Total elements in intents are = " + checked);
        str.append("New Order Items:\n");

        int i=0;
        while(i<checked)
        {
            str.append(intent.getStringExtra(String.valueOf(i)));
            str.append("\n");
            ordered_names_Column.append(intent.getStringExtra(String.valueOf(i)));
            ordered_names_Column.append(",");
            i++;
        }
        //temp = ordered_names_Column.toString();
        temp = (ordered_names_Column.toString()).substring(0, (ordered_names_Column.toString()).length()-1);
        //temp = (ordered_names_Column.toString()).replace((ordered_names_Column.toString()).substring((ordered_names_Column.toString()).length()-1), "");

        str.append("\n\nUpdated Bill Amount:\n $");
        int totalPrice = intent.getIntExtra("updatedBillAmount", 0);
        total_order_price_Column = Integer.toString(totalPrice);
        oldValue = newValue;
        newValue = totalPrice;

        str.append(String.valueOf(totalPrice));
        textView.setText(str);

       /* System.out.println(" SQL order_id_Column" + order_id_Column);
        System.out.println(" SQL order_time_stamp_Column" + order_time_stamp_Column);
        System.out.println(" SQL ordered_names_Column" + temp);
        System.out.println(" SQL total_order_price_Column" + total_order_price_Column);*/

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDB myDB = new MyDB(getApplicationContext());
                myDB.createRecord(order_id_Column,order_time_stamp_Column,temp,total_order_price_Column);

                Intent intent = getIntent();
                intent.putExtra("totalbill", String.valueOf(newValue));
                setResult(RESULT_CODE_CONFIRM, intent);
                finish();
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("totalbill", String.valueOf(oldValue));
                setResult(RESULT_CODE_CANCEL, intent);
                finish();
            }
        });


    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}

