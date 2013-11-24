package com.OneTwoThreePleasantSt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

// Activity To View Band Results
public class BandResultsActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Save The State, Set The Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_results);

        // Get The Result From The Main Activity
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

	Context context = getApplicationContext();
	CharSequence text = result;
	int duration = Toast.LENGTH_LONG;
	Toast toast = Toast.makeText(context, text, duration);
	toast.show();

	
    }
}
