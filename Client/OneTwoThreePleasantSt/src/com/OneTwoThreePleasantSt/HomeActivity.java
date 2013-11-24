package com.OneTwoThreePleasantSt;

import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

public class HomeActivity extends ListActivity 
{
    //final ListView weekListView = (ListView) findViewById(R.id.list);
    static private String[] weekListing =new String[] { "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday" };

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
	
	//Set The Layout
	setContentView(R.layout.home_layout);

	setListAdapter (new ArrayAdapter <String> (this, android.R.layout.simple_list_item_1, weekListing));

	ListView weeklyListing = getListView();

	/**weekListView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		    // When clicked, show a toast with the TextView text
		    Toast.makeText(getApplicationContext(),
				   ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
		}
		});*/

	    }  
}

