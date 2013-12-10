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
    private Button nextDay;
    private Button prevDay;
    
    // storage for all of the day information
    private String day;
    private String commentsForDay;
    private String[] bandsForDay;
    private String ticketLink;

    //storage for info that comes from another process
    private String comments;
    private String bandList;

    public void onCreate(Bundle savedInstanceState) 
    {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
	Intent intent = getIntent();
        comments = intent.getStringExtra("dayComments");
	bandList = intent.getStringExtra("dayBands");
	
	//parse it out now.
	parseDayInfo();
	
	/**
	prevDay = (Button) findViewById(R.id.leftArrow);
	nextDay = (Button) findViewById(R.id.rightArrow);
	// listen for previous day's button click
	prevDay.setOnClickListener( new View.OnClickListener()
	    {
		@Override
		public void onClick (View v)
		{
	**/	    
	//If comment or ticketLink == "NULL" then do not display
	TextView displayComments = (TextView) findViewById(R.id.dayDate);
	displayComments.setText(day);
	
	TextView displayBands = (TextView) findViewById(R.id.dayEventBands);
	displayBands.setText(commentsForDay);

	getSomeTickets = (Button) findViewById(R.id.dayTicket);

	// display list of bands
	// display flyer
	listen();
    }
    
    public void parseDayInfo()
    {
	String [] parsedComment = comments.split("\\n");
	day = parsedComment[0]; 
	commentsForDay = parsedComment[1]; 
	ticketLink = parsedComment[parsedComment.length - 1];

	String [] parsedBands = bandList.split("\\n");
	
	bandsForDay = new String [parsedBands.length - 1];
	for (int i = 1; i < parsedBands.length; i++){
	    parsedBands[i-1] = parsedBands[i];
	} 
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
