package com.OneTwoThreePleasantSt;

import android.app.Activity;
import java.util.ArrayList;
import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Intent;

public class DayActivity extends Activity 
{
   
    public void onCreate(Bundle savedInstanceState) 
    {
        
	super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

	Intent intent = getIntent();
        String currentDay = intent.getStringExtra("currentDay");

	TextView tv = (TextView) findViewById(R.id.dayInfo);
	tv.setText(currentDay);

	// Container for the results
        ListView lv = (ListView) findViewById(R.id.dayList);
        ArrayList<String> arrayList = new ArrayList<String>();
	///

	//for (int i = 0; i < currentDay.length; i++)
	arrayList.add(currentDay);

	//Display the results
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
		
    }
}

