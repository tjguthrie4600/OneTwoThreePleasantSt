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
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
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
import android.view.ViewGroup;
import android.net.Uri;

public class DayActivity extends Activity implements Runnable
{
    private Button getSomeTickets;
    private Button nextDay;
    private Button prevDay;
  
    // storage for all of the day information
    private String day;
    private String nextDate = "NULL";
    private String prevDate = "NULL";
    private String selectedBand = "NULL";
    private String commentsForDay;
    private String[] bandsForDay;
    private String ticketLink;

    //storage for what goes to the next process
    
    private String nextDayCommentsResult;
    private String nextDayBandsResult;
    private String prevDayCommentsResult;
    private String prevDayBandsResult;
    private String bandResult;

    //storage for info that comes from another process
    private String comments;
    private String bandList;

    //    private ListView lv;
    private ListView listOfBandsView;
    private String serverResult;
    private ProgressDialog progress;
    private CalendarClass cal;

    public void onCreate(Bundle savedInstanceState) 
    {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

      	Intent intent = getIntent();
        comments = intent.getStringExtra("dayComments");
	bandList = intent.getStringExtra("dayBands");
	
	//parse it out now.
	parseDayInfo();
	
	prevDay = (Button) findViewById(R.id.leftArrow);
	nextDay = (Button) findViewById(R.id.rightArrow);
	    
	//If comment or ticketLink == "NULL" then do not display
	TextView displayDate = (TextView) findViewById(R.id.dayDate);
	displayDate.setText(day);
		
	TextView displayComments = (TextView) findViewById(R.id.dayComments);
	displayComments.setText(commentsForDay);

	listOfBandsView = (ListView) findViewById(R.id.dayBandList);
	ArrayList<String> bandArrayList = new ArrayList<String>();
	
	
	for(int i = 0; i < bandsForDay.length; i++){
	    if ((! (bandsForDay[i].equals("NULL"))) || (! (bandsForDay[i].equals("")))){
		bandArrayList.add(bandsForDay[i]);
	    }
	}

	// Display the results
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, bandArrayList);
	listOfBandsView.setAdapter(arrayAdapter); 


	getSomeTickets = (Button) findViewById(R.id.dayTicketButton);
	if (ticketLink.equals("NULL")){
	    getSomeTickets.setVisibility(View.INVISIBLE);
	}

	// display list of bands
	// display flyer
	listen();
    }
    
    public void parseDayInfo()
    {
	String [] parsedComment = comments.split("\\n");
	day = parsedComment[0]; 
	commentsForDay = parsedComment[1]; 
	if (commentsForDay.equals("NULL")){
	    commentsForDay = "";
	}
	ticketLink = parsedComment[parsedComment.length - 1];
	
	String [] parsedBands = bandList.split("\\n");
	
	bandsForDay = new String [parsedBands.length - 1];
	for (int i = 1; i < parsedBands.length; i++){
	    bandsForDay[i-1] = parsedBands[i];
	} 
    }


    public void listen()
    {
	getSomeTickets.setOnClickListener(new View.OnClickListener()
	    {
		public void onClick (View v)
		{
		    /**Context ticketContext = getApplicationContext();
		    CharSequence tempText = "going to take you to a web link";
		    int durationOfMessage = Toast.LENGTH_LONG;
		    Toast toast = Toast.makeText(ticketContext, tempText, durationOfMessage);
		    toast.show();*/
		    Intent webviewIntent = new Intent("android.intent.action.VIEW", Uri.parse(ticketLink));
		    startActivity(webviewIntent);
		}
	    }
	    );

	listOfBandsView.setOnItemClickListener(new OnItemClickListener() 
	    {
		// When an item in the list is selected 
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{		    
		    selectedBand = (String) listOfBandsView.getItemAtPosition(position);
		    talkToServer();
		}});

	// Listen For Right Button Clicks
	nextDay.setOnClickListener( new View.OnClickListener()
	    {
		@Override
		public void onClick(View v) 
		{
		    cal = new CalendarClass(day);
		    nextDate = cal.getNextDay();
		    talkToServer();
		}
	    });

	// Listen For Left Button Clicks
	prevDay.setOnClickListener( new View.OnClickListener()
	    {
		@Override
		public void onClick(View v) 
		{
		    cal = new CalendarClass(day);
		    prevDate = cal.getPrevDay();
		    talkToServer();
		}
	    });

    }
    

    public void talkToServer(){
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
		progress = ProgressDialog.show(DayActivity.this, "Please Wait", "Retrieving Data", true, false);
		
		// Create New Thread                                                                                                                            
		Thread thread = new Thread(DayActivity.this);
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
	    if (! (selectedBand.equals("NULL"))){
		    bandResult = (String) client.call("PleasantMobile", "2", selectedBand);
	    }
	    else if (! (nextDate.equals("NULL"))){
		nextDayCommentsResult = (String) client.call("PleasantMobile", "4", nextDate);
		nextDayBandsResult = (String) client.call("PleasantMobile", "3", nextDate);
	    }
	    else if (! (prevDate.equals("NULL"))){
		prevDayCommentsResult = (String) client.call("PleasantMobile", "4", prevDate);
		prevDayBandsResult = (String) client.call("PleasantMobile", "3", prevDate);
	    }

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
		Intent nextScreen = new Intent (getApplicationContext(), BandActivity.class); //some random initialization for the compiler
		if (! (selectedBand.equals("NULL"))){
		    //send to band intent place
		    // uses bandResult
		    nextScreen = new Intent(getApplicationContext(), BandActivity.class);
		    nextScreen.putExtra("result", bandResult);
		}
		else if (! (nextDate.equals("NULL"))){
		    //send it to a new day view intent
		    // uses nextDayCommentsResult, and nextDayBandsResult
		    nextScreen = new Intent(getApplicationContext(), DayActivity.class);
		    nextScreen.putExtra("dayComments", nextDayCommentsResult);
		    nextScreen.putExtra("dayBands", nextDayBandsResult);
		    
		}
		else if (! (prevDate.equals("NULL"))){
		    // send it to a new day view intent
		    // uses prevDayCommentsResult, and prevDayBandResult
		    nextScreen = new Intent(getApplicationContext(), DayActivity.class);
		    nextScreen.putExtra("dayComments",prevDayCommentsResult);
		    nextScreen.putExtra("dayBands", prevDayCommentsResult);
		    
		}
		startActivity(nextScreen);
	    }
	};
}
