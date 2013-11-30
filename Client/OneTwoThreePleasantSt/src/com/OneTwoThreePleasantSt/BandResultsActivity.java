package com.OneTwoThreePleasantSt;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.AdapterView;
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
import java.net.URI;

// Activity To View Band Results
public class BandResultsActivity extends Activity implements Runnable
{
    // Storage for the results
    private String serverResult;
    private String name;

    // UI objects
    private ListView lv;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Save The State, Set The Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_results);

	// Container for the results
	lv = (ListView) findViewById(R.id.bandListView);
	ArrayList<String> arrayList = new ArrayList<String>();

	// Get The Result From The Main Activity
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
	String resultArray[] = result.split("\\r?\\n");
	
	for (int i = 0; i < resultArray.length; i++)
	    arrayList.add(resultArray[i]);

	// Display the results
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
	lv.setAdapter(arrayAdapter); 

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
		name = (String) lv.getItemAtPosition(position);

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
		    progress = ProgressDialog.show(BandResultsActivity.this, "Please Wait", "Retrieving Data", true, false);
		    
		    // Create New Thread                                                                                                                            
		    Thread thread = new Thread(BandResultsActivity.this);
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
	    serverResult = (String) client.call("PleasantMobile", "2", name);
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
		Intent nextScreen = new Intent(getApplicationContext(), BandActivity.class);
		nextScreen.putExtra("result", serverResult);
		startActivity(nextScreen);
	    }
	};
}
