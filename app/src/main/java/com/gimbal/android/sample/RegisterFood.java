package com.gimbal.android.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bradley on 6/27/15.
 */
public class RegisterFood extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText foodType, quantity;
    private Spinner location;
    private String chosen_location = "";

    private ListView placeView;
    private ArrayList<String> placeList;
    private ArrayAdapter adapter;

    private static final Map<String, String> loc_map;
    static
    {
        loc_map = new HashMap<String, String>();
        loc_map.put("AO-121D", "2517FBCC56254497847E90789B79E561");
        loc_map.put("AO-131E", "FE89DE892DD544C5A93D9A5115C3F881");
        loc_map.put("AO-143C", "458AADD8415D47D08E17B4EA74505F95");
        loc_map.put("AO-202C", "D256B10FF17B410E9141D4A543279890");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_food);

        foodType = (EditText) findViewById(R.id.newFood);
        location = (Spinner) findViewById(R.id.newLoc);
        quantity = (EditText) findViewById(R.id.quantity);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.locs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter);
        location.setOnItemSelectedListener(this);

//        placeView = (ListView) findViewById(R.id.placeView);
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ((MyApp)getApplicationContext()).getBeacons());
//        placeView.setAdapter(adapter);
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
        GimbalServer.set_server_pair("Food", foodType.getText().toString(), loc_map.get(chosen_location));
        GimbalServer.set_server_pair("Loc", chosen_location, loc_map.get(chosen_location));
        GimbalServer.set_server_pair("Quantity", quantity.getText().toString(), loc_map.get(chosen_location));
        setResult(RESULT_OK, i);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        chosen_location = (String) parent.getItemAtPosition(pos);
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
