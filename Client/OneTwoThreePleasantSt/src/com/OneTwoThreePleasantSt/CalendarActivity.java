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
import android.app.ProgressDialog;
import android.util.Log;
import android.os.Handler;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Message;
import java.net.URI;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

public class CalendarActivity extends Activity implements Runnable
{
    GridView dayGrid;
    Button next;
    Button prev;
    CalendarClass calendar = new CalendarClass();
    final String currentMonth = calendar.getMonth();
    final String currentYear = calendar.getYear();
    final int currentFinalDay = calendar.getCurrentDay();
    final int currentStartDay = calendar.getStartDay();
    TextView tv;
    
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


    private String currentDay;
    private String serverResultDayComments;
    private String serverResultsDayBands;
    private String serverResultDays;
    private ProgressDialog progress;


    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	menu.add("Hilight Today");
	menu.add("Show Events");
	return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	if (item.getTitle().toString().equals("Hilight Today"))
	    showToday();
	else if (item.getTitle().toString().equals("Show Events"))
	    fillDays();

	return super.onOptionsItemSelected(item);
    }

    public void showToday()
    {
	if (currentMonth.equals(calendar.getMonth()) && currentYear.equals(calendar.getYear()))
	{
	    tv = (TextView) dayGrid.getChildAt(calendar.getStartDay() + currentFinalDay - 1);
	    tv.setTextColor(Color.YELLOW);
	}
	else
	{
	    Toast.makeText(getApplicationContext(),"Please navigate to current month", Toast.LENGTH_SHORT).show();
	}
    }

    public void fillDays()
    {
	URI uri = URI.create("http://98.236.199.243/lamp/pleasant/XML-RPC-Server.py");
        XMLRPCClient client = new XMLRPCClient(uri);
        try
        {
	    // Make The Call To The Server
	    serverResultDays = (String) client.call("PleasantMobile", "5", calendar.getLastDay());
        }
        catch (XMLRPCException e)
        {
	    // Log Errors
	    Log.w("XMLRPC Error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", "Error", e);
	}

	String resultArray[] = serverResultDays.split("\\r?\\n");
	
	
	for (int i = 0; i < resultArray.length; i++)
	{
	    if (!(resultArray[i].equals("")))
	    {
		tv = (TextView) dayGrid.getChildAt(calendar.getStartDay() + Integer.parseInt(resultArray[i]) - 1);
		tv.setTextColor(Color.RED);
	    }
	}
    }
    
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
		    
		    currentDay = calendar.getYear()+ "-" + calendar.getIntMonth() + "-" + ((TextView) v).getText().toString();
		    getConnected();
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

    public void getConnected(){
	// Check For Network Connection
	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	
	// The User Is Not Connected To A Network
	if (networkInfo == null)
	    {
		// Tell The User They Are Not Connected
		Context context = getApplicationContext();
		CharSequence text = "An Active Network Connection Is Required";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	    }
	// The User Is Connected To The Internet
	else
	    {
		// Display Messege
		progress = ProgressDialog.show(CalendarActivity.this, "Please Wait", "Retrieving Data", true, false);
		
		// Create New Thread
		Thread thread = new Thread(CalendarActivity.this);
		thread.start();
	    }	
    }
    // When new thread is run
    public void run()
    {
	// Create The XMLRPC Client
        URI uri = URI.create("http://98.236.199.243/lamp/pleasant/XML-RPC-Server.py");
        XMLRPCClient client = new XMLRPCClient(uri);
        try
        {
	    // Make The Call To The Server
	    serverResultDayComments = (String) client.call("PleasantMobile", "4", currentDay);
	    serverResultsDayBands = (String) client.call("PleasantMobile", "3", currentDay);
        }
        catch (XMLRPCException e)
        {
	    // Log Errors
	    Log.w("XMLRPC Error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", "Error", e);
	}
	
	// Create And Send Message To User Interface Thread
        handler.sendEmptyMessage(0);
    }
// Create The Handler To Communicate Back To The User Interface Thread
    private Handler handler = new Handler()
    {
	@Override
	public void handleMessage(Message msg)
	{
	    // Get Rid Of The Loading Message
	    progress.dismiss();
	    // Start The New Activity, Send It The Result
	    Intent nextScreen = new Intent(getApplicationContext(), DayActivity.class);
	    nextScreen.putExtra("dayComments",serverResultDayComments);
	    nextScreen.putExtra("dayBands", serverResultsDayBands);
	    startActivity(nextScreen);
	}
	};
}
