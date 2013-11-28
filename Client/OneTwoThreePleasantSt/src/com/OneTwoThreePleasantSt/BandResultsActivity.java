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

// Activity To View Band Results
public class BandResultsActivity extends Activity
{

    private ListView lv;

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
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
		    String name = (String) lv.getItemAtPosition(position);    
		}
	});
    }   
}
