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
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;


public class CalendarActivity extends Activity 
{
    GridView dayGrid;

    // Get The First And Last Days In A Month
    Calendar cal = Calendar.getInstance();
    int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    int year = cal.get(cal.YEAR);  
    int month = cal.get(cal.MONTH);
    int firstDay = getFirstDay(month,year);
    String  monthString = String.format(Locale.US,"%tB",cal);

    

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


	// Set The Array Of Days
	for (int i = 0; i<firstDay; i++)
	    days[i] = "";
	
	for (int j = firstDay; j<=endDay+1; j++)
	    days[j] = Integer.toString(j-1);

	// Set The Layout
        setContentView(R.layout.calendar_layout);

	TextView textView = (TextView) findViewById(R.id.monthView);
	
	textView.setText(monthString);

        // Set Up The Day Grid
	dayGrid = (GridView) findViewById(R.id.gridView1);
	ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, days);
	dayGrid.setAdapter(dayAdapter);

	// Listen On The Day Grid For User Clicks
	dayGrid.setOnItemClickListener(new OnItemClickListener() 
	    {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		{
		    // Display The Clicked Value
		    Toast.makeText(getApplicationContext(),((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		    //Toast.makeText(getApplicationContext(), String.format(Locale.US,"%tB",cal), Toast.LENGTH_SHORT).show();
		    //Toast.makeText(getApplicationContext(), Integer.toString(endDay), Toast.LENGTH_SHORT).show();
		}
	    });
    }

    // Gets the first day of the month
    private int getFirstDay(int month, int year)
    {	
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
	case Calendar.SUNDAY:
	    return 0;
	case Calendar.MONDAY:
	    return 1;
	case Calendar.TUESDAY:
	    return 2;
	case Calendar.WEDNESDAY:
	    return 3;
	case Calendar.THURSDAY:
	    return 4;
	case Calendar.FRIDAY:
	    return 5;
	case Calendar.SATURDAY:
	    return 6;
        }
        return 0;
    }
}
