package com.OneTwoThreePleasantSt;

import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import java.util.ArrayList;
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

public class HomeActivity extends Activity implements Runnable
{
    private ListView lv;
    String serverResultDayComments = "";
    String serverResultsDayBands = "";
    private ProgressDialog progress;
    private CalendarClass cal = new CalendarClass();

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
	
	lv = (ListView) findViewById(R.id.homeList);
	String [] week = cal.createWeek();
	
	//do something like ifPicture() for a day then it will set the picture with the flyer.
	// if not then it won't display it
	// some[] week = cal.createWeek();
	// for each day, check to see if it has a picture then display it

	// set onClickListeners like in CalendarActivity
	ArrayList<String> arrayList = new ArrayList<String>();

	for (int i = 0; i < week.length; i++)
            arrayList.add(week[i]);

	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
	lv.setAdapter(arrayAdapter);

	listen();
    }
    
    private String currentDay;
    
    // Listen for clicks on the list view
    public void listen()
    {
	lv.setOnItemClickListener(new OnItemClickListener() 
	    {
		// When an item in the list is selected 
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{		    
		    currentDay = (String) lv.getItemAtPosition(position);
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
                            progress = ProgressDialog.show(HomeActivity.this, "Please Wait", "Retrieving Data", true, false);
			    
                            // Create New Thread
                            Thread thread = new Thread(HomeActivity.this);
                            thread.start();
                        }
		    
		}
	    });
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


