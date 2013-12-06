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


public class HomeActivity extends Activity /// implements Runnable
{
    private ListView lv;
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
	
	lv = (ListView) findViewById(R.id.homeList);
	CalendarClass cal = new CalendarClass();
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

	//	ListView weeklyListing = lv.getListView();
	listen();
    }

    // Listen for clicks on the list view
    public void listen()
    {
	lv.setOnItemClickListener(new OnItemClickListener() 
	    {
		// When an item in the list is selected 
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
		    // Store the band name
		    String name = (String) lv.getItemAtPosition(position);
		    Intent nextScreen = new Intent(getApplicationContext(), DayActivity.class);
		    nextScreen.putExtra("currentDay", name);
		    startActivity(nextScreen);
		    
		}
	    });
    }

    //public void run()
    //{

    //}
}