package com.cs442.akedari.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnPlayOrder;
    private Button btnReset;
    private Button btnOrderHistory;
    private ListView lstItems;

    Intent intent;
    int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnPlayOrder = (Button)findViewById(R.id.button);
        btnReset = (Button)findViewById(R.id.button2);
        btnOrderHistory = (Button)findViewById(R.id.button3);
        lstItems = (ListView)findViewById(R.id.listView);
        final ArrayList<String> menus= new ArrayList<String>();
        final ArrayAdapter<String> menusAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,menus);
        lstItems.setAdapter(menusAdapter);
        lstItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        menus.add("Zunka Bhakar     Price:1");
        menus.add("Pizza     Price:2");
        menus.add("Kebab     Price:3");
        menus.add("Handir     Price:4");
        menus.add("Noodle     Price:5");
        menus.add("Burger     Price:1");
        menus.add("Sushi     Price:2");
        menus.add("coffee     Price:3");

        btnPlayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int countSelecteditems = 0;
                int parsedMenuPrice = 0;
                int checkedItemCount = 0;
                int currentItemBill = 0;
                StringBuilder builder = new StringBuilder();

                intent = new Intent(MainActivity.this, confirmActivity.class);

                intent.setType("text/plain");
                /*intent.putExtra("value 1", "This value1 is for activity 1");
                intent.putExtra("value 2", "This value2 is for activity 2");*/
                //intent.putExtra("count",2);

                SparseBooleanArray checkedItems = lstItems.getCheckedItemPositions();
                int numberofselecteditem = checkedItems.size();
                intent.putExtra("count", numberofselecteditem);

                if (null != checkedItems && numberofselecteditem>0) {
                    int i = 0;
                    while (i < checkedItems.size()) {
                        if (checkedItems.valueAt(i)) {
                            String item = lstItems.getAdapter().getItem(checkedItems.keyAt(i)).toString();
                            String[] parsedStr = item.split("     Price:", 2);
                            intent.putExtra(String.valueOf(checkedItemCount), parsedStr[0]);
                            String temp = item;
                            String numberOnly = temp.replaceAll("[^0-9]", "");
                            currentItemBill += Integer.parseInt(numberOnly);
                            checkedItemCount = checkedItemCount + 1;
                        }
                        i++;
                    }


                    intent.putExtra("Selected", checkedItemCount);
                    boolean action = intent.getExtras().getBoolean("updatedBillAmount");
                    if (action == true) {
                        intent.putExtra("billAmount", intent.getIntExtra("updatedBillAmount", 0));
                        intent.putExtra("updatedBillAmount", currentItemBill);
                    } else {
                        intent.putExtra("updatedBillAmount", currentItemBill);
                        intent.putExtra("billAmount", 0);
                    }

                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(MainActivity.this,orderHistoryActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lstItems.setItemChecked(0,false);
                lstItems.setItemChecked(1,false);
                lstItems.setItemChecked(2,false);
                lstItems.setItemChecked(3,false);

                lstItems.setItemChecked(4,false);
                lstItems.setItemChecked(5,false);
                lstItems.setItemChecked(6,false);
                lstItems.setItemChecked(7,false);

                TextView textView = (TextView)findViewById(R.id.textView);
                textView.setText("$0");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView total_bill = (TextView)findViewById(R.id.textView);
        total_bill.setText("$"+data.getStringExtra("totalbill"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
