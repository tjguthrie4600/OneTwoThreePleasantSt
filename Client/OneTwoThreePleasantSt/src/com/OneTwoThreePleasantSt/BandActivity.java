package com.OneTwoThreePleasantSt;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Message;
import android.widget.TextView;
import java.net.URI;
import android.text.util.Linkify;

// Activity To View Band Results
public class BandActivity extends Activity implements Runnable
{

    ListView lv;
    String selectedDate;
    String serverResult01;
    String serverResult02;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Save The State, Set The Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_layout);

	// Get The Result From The BandResults Activity
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
	String resultArray[] = result.split("\\r?\\n");

	String bandName = resultArray[0];
	String bandWebsite = resultArray[1];

	TextView textView = (TextView) findViewById(R.id.bandNameID);
        textView.setText(bandName);
	
	TextView textView2 = (TextView) findViewById(R.id.bandWeb);
        textView2.setText(bandWebsite);
	Linkify.addLinks(textView2, Linkify.ALL);

	// Container for the results
        lv = (ListView) findViewById(R.id.bandList);
        ArrayList<String> arrayList = new ArrayList<String>();

	for (int i = 2; i < resultArray.length; i++)
            arrayList.add(resultArray[i]);

	// Display the results
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);

	listen();
	
    }

    // Listen For Clicks                                                                                                                                                    
    public void listen()
    {
        lv.setOnItemClickListener(new OnItemClickListener()
        {
	    // When The Submit Button Is Pressed                                                                                                                        
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    {
		// Set The Date
		selectedDate = lv.getItemAtPosition(position).toString();
		
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
			progress = ProgressDialog.show(BandActivity.this, "Please Wait", "Retrieving Data", true, false);
			
			// Create New Thread                                                                                                                            
			Thread thread = new Thread(BandActivity.this);
			thread.start();
		    }
                }
	    });
	
    }
    // When New Thread Is Run                                                                                                                                               
    public void run()
    {
	// Create The XMLRPC Client                                                                                                                                         
	URI uri = URI.create("http://98.236.199.243/lamp/pleasant/XML-RPC-Server.py");
	XMLRPCClient client = new XMLRPCClient(uri);
	
	try
	{
	    // Make The Call To The Server                                                                                                                              
	    serverResult01 = (String) client.call("PleasantMobile", "3", selectedDate);
	    serverResult02 = (String) client.call("PleasantMobile", "4", selectedDate);
	}
	catch (XMLRPCException e)
	{
	    // Log Errors                                                                                                                                               
	    Log.w("XMLRPC Test", "Error", e);
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
	    nextScreen.putExtra("dayBands", serverResult01);
	    nextScreen.putExtra("dayComments", serverResult02);
	    startActivity(nextScreen);
	}
	};
}
    
    
