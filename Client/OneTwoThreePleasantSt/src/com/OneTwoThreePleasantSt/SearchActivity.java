package com.OneTwoThreePleasantSt;

import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import java.net.URI;
import android.widget.EditText;
import android.view.View;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Button;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.net.NetworkInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SearchActivity extends Activity implements Runnable 
{
    // UI Objects
    private EditText band;
    private Button commitButton;
    private RadioGroup radGrp;
    private RadioButton radBut;
    private ProgressDialog progress;

    // Storage For The Result                                                                                                                                              
    String serverResult = "";

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

	// Set Reference For Feilds
	band = (EditText) findViewById(R.id.bandNameValue);
	
        commitButton = (Button) findViewById(R.id.searchButton);

	listen();
    }


    // Listen For Clicks
    public void listen()
    {
        commitButton.setOnClickListener(new View.OnClickListener()
            {
                // When The Search Button Is Pressed
                public void onClick(View v)
                {
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
                            progress = ProgressDialog.show(SearchActivity.this, "Please Wait", "Retrieving Data", true, false);

                            // Create New Thread
                            Thread thread = new Thread(SearchActivity.this);
                            thread.start();
                        }
                }
            });
    }
    

    // When new thread is run
    public void run()
    {
	// Store the username and password on submit
	String bandString = band.getText().toString();

	// Get the value of the Radio Button
	radGrp = (RadioGroup) findViewById(R.id.radiogroup);
	int radioId = radGrp.getCheckedRadioButtonId();
	radBut = (RadioButton) findViewById(radioId);
	CharSequence radioString = radBut.getText();

	if (radioString.equals("Before Today"))
	    radioString = "0";
	else
	    radioString = "1";
	
	// Create The XMLRPC Client
        URI uri = URI.create("http://98.236.199.243/lamp/pleasant/XML-RPC-Server.py");
        XMLRPCClient client = new XMLRPCClient(uri);
        try
        {
	    // Make The Call To The Server
	    serverResult = (String) client.call("PleasantMobile", radioString, bandString);
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
	    Intent nextScreen = new Intent(getApplicationContext(), BandResultsActivity.class);
	    nextScreen.putExtra("result", serverResult);
	    startActivity(nextScreen);
	}
    };

}
