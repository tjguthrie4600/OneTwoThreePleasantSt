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
	
	String eventArray [] = currentDay.split("\\n");

	String theDay = eventArray[0];
	String bandNames = eventArray[1];

	TextView eventDate = (TextView) findViewById(R.id.eventInfo);
	eventDate.setText(theDay);

	TextView bandsPlaying = (TextView) findViewById(R.id.dayEventBands);
	bandsPlaying.setText(bandNames);

	// Container for the results
        ListView lv = (ListView) findViewById(R.id.dayList);
        ArrayList<String> arrayList = new ArrayList<String>();
	///

	for (int i = 0; i < eventArray.length; i++)
	arrayList.add(eventArray[i]);

	//Display the results
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
		
    }
}

