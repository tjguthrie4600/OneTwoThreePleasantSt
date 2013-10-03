package com.OneTwoThreePleasantSt;

import android.app.Activity;
import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarActivity extends Activity 
{
    GridView dayGrid;
    GridView weekGrid;

    // Days In The Month
    static final String[] days = new String[] 
	{ 
	    "1", "2", "3", "4", "5", "6", "7", 
	    "8", "9", "10", "11", "12", "13", "14", 
	    "15", "16", "17", "18", "19", "20", "21", 
	    "22", "23", "24", "25", "26", "27", "28", 
	    "29", "30", "31"
	};

    // Days In Week
    static final String[] weeks = new String[] 
     {
     	"S", "M", "T", "W", "R", "F", "S"
     };

    // Called When The Activity Is First Created
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

	// Set The Layout
        setContentView(R.layout.calendar_layout);

        // Set Up The Day Grid
	dayGrid = (GridView) findViewById(R.id.gridView1);
	ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, days);
	dayGrid.setAdapter(dayAdapter);

	// Set Up The Week Grid
	weekGrid = (GridView) findViewById(R.id.gridView2);
	ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, weeks);
	weekGrid.setAdapter(weekAdapter);

	// Listen On The Day Grid For User Clicks
	dayGrid.setOnItemClickListener(new OnItemClickListener() 
	    {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		{
		    // Display The Clicked Value
		    Toast.makeText(getApplicationContext(),((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		}
	    });
    }
}
