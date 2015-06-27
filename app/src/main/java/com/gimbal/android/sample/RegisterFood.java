package com.gimbal.android.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Bradley on 6/27/15.
 */
public class RegisterFood extends Activity {
    private EditText location, foodType, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_food);

        foodType = (EditText) findViewById(R.id.newFood);
        location = (EditText) findViewById(R.id.newLoc);
        quantity = (EditText) findViewById(R.id.quantity);
    }

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.new_item, menu);
//            return true;
//        }

    public void submit(View view)
    {
        Intent i = getIntent();
        i.putExtra("foodType", foodType.getText().toString());
        i.putExtra("location", location.getText().toString());
        i.putExtra("quantity", quantity.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
}