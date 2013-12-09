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

public class DayActivity extends Activity 
{
    private Button getSomeTickets;
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

	getSomeTickets = (Button) findViewById(R.id.dayTicket);

	for (int i = 0; i < eventArray.length; i++)
	    arrayList.add(eventArray[i]);
	
	//Display the results
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);

	//Button ticketsButton = (Button) findViewById(R.id.dayTicketsButton);
	//ticketsButton.setOnClickListener(new OnClickListener()
	//    {
		//this will take you to a url link for getting them in a little bit
	//	public void onClick(View v)
	//	{
		    //saddr = start.getText().toString();
		    ///final Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr));
		    //startActivity(i);
	//	    Toast toast = Toast.makeText(getApplicationContext(), "going to take you to a web link", Toast.LENGTH_LONG);
	//	}
	//    });

	// display list of bands
	
	// display flyer
	listen();
    }
    
    public void listen()
    {
	getSomeTickets.setOnClickListener(new View.OnClickListener()
	    {
		public void onClick (View v)
		{
		    //this will take you to a url link for getting them in a little bit
			//saddr = start.getText().toString();
			///final Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr));
			//startActivity(i);
		    Context ticketContext = getApplicationContext();
		    CharSequence tempText = "going to take you to a web link";
		    int durationOfMessage = Toast.LENGTH_LONG;
		    Toast toast = Toast.makeText(ticketContext, tempText, durationOfMessage);
		    toast.show();
		}
	    }
	    );
    }
    
}

