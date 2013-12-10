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
public class BandActivity extends Activity
{
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
        ListView lv = (ListView) findViewById(R.id.bandList);
        ArrayList<String> arrayList = new ArrayList<String>();

	for (int i = 2; i < resultArray.length; i++)
            arrayList.add(resultArray[i]);

	// Display the results
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
	
     }

}
