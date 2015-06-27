package com.gimbal.android.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Bradley on 6/27/15.
 */
public class Home extends Activity {
    private ListView foodView;
    private ArrayList<String> foodList;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        foodView = (ListView) findViewById(R.id.foodView);
        foodList = new ArrayList<String>();
        foodList.add("None");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList);
        foodView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean newForm(View view) {
        Intent intent = new Intent(this, RegisterFood.class);
        startActivityForResult(intent, 1);

        return true;
    }

//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch(item.getItemId())
//        {
//            case R.id.addItemButton:
//                Intent intent = new Intent(this, RegisterFood.NewItem.class);
//                startActivityForResult(intent, 1);
//
////				foodList.add(""+(char)letter++);
////				adapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String message = data.getStringExtra("item");
        foodList.add(message);
        adapter.notifyDataSetChanged();
    }
}
