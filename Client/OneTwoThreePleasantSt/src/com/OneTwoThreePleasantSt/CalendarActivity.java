package com.OneTwoThreePleasantSt;

import com.OneTwoThreePleasantSt.CalendarClass;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;


public class CalendarActivity extends Activity 
{
    GridView dayGrid;
    Button next;
    Button prev;
    CalendarClass calendar = new CalendarClass(); 

    // Days In The Month
    static String[] days = new String[]
    {
	"", "", "", "", "", "", "",
	"", "", "", "", "", "", "",
	"", "", "", "", "", "", "",
	"", "", "", "", "", "", "",
	"", "", "", "", "", "", "",
	"", "", "", "", "", "", "",
	""
    };
    
    // Called When The Activity Is First Created
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

	// Set The Layout
        setContentView(R.layout.calendar_layout);

	// Set Up The Display
	setViews();

	// Listen On The Day Grid For User Clicks
	dayGrid.setOnItemClickListener(new OnItemClickListener() 
	    {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		{
			    // Display The Clicked Value
		    Toast.makeText(getApplicationContext(),((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		    //Toast.makeText(getApplicationContext(), Integer.toString(endDay), Toast.LENGTH_SHORT).show();
		}
	  });

	// Listen For Button Clicks
	next.setOnClickListener( new View.OnClickListener()
	    {
		@Override
		public void onClick(View v) 
		{
		    calendar.nextMonth();
		    setViews();
		}
	    });

	// Listen For Button Clicks
	prev.setOnClickListener( new View.OnClickListener()
	    {
		@Override
		public void onClick(View v) 
		{
		    calendar.prevMonth();
		    setViews();
		}
	    });

	
    }

    private void setViews()
    {
	// Set The Array Of Days
	for (int i = 0; i<calendar.getStartDay(); i++)
	    days[i] = "";
	
	for (int j = calendar.getStartDay(); j<=calendar.getEndDay()+calendar.getStartDay(); j++)
	    days[j] = Integer.toString(j-calendar.getStartDay()+1);

	for (int k = calendar.getEndDay()+calendar.getStartDay()+1; k<days.length; k++)
	    days[k] = "";

	// Set The Month
	TextView textView = (TextView) findViewById(R.id.monthView);
	textView.setText(calendar.getMonth() + "  ");

	// Set The Year
	TextView textView2 = (TextView) findViewById(R.id.yearView);
	textView2.setText(calendar.getYear());

        // Set Up The Day Grid
	dayGrid = (GridView) findViewById(R.id.gridView1);
	ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, days);
	dayGrid.setAdapter(dayAdapter);

	// Set up the buttons
	prev = (Button) findViewById(R.id.leftArrow);
	next = (Button) findViewById(R.id.rightArrow);
    }
}
