package com.OneTwoThreePleasantSt;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.location.LocationManager;
import android.content.Context;
import android.location.Location;
import android.location.Geocoder;
import java.util.List;
import android.location.LocationListener;
import android.location.Address;
import java.util.Locale;
import java.io.IOException;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DirectionsActivity extends Activity
{
    EditText start;
    String saddr = "";
    final String daddr = "123 Pleasant Street, 123 Pleasant St, Morgantown, West Virginia 26505-5417";
    LocationManager manager;
    Location location;
    double startLatitude;
    double startLongitude;
    List<Address> addresses;
    Geocoder geocoder;

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions_layout);

	start = (EditText) findViewById(R.id.startLocationVaule);
	Button button = (Button) findViewById(R.id.directionsButton);
	Button button2 = (Button) findViewById(R.id.startButton);
	
	geocoder = new Geocoder(this, Locale.getDefault());;
	manager = (LocationManager) getSystemService(LOCATION_SERVICE);

	button2.setOnClickListener(new OnClickListener()
	{
	    public void onClick(View v)
            {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
		    showGPSDisabledAlertToUser();
		}
		else
		{

		    // Gets the starting latitude and longitude
		    location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);	
		    
		    if (location != null)
		    {
			startLatitude = location.getLatitude();
			startLongitude = location.getLongitude();
			
			// Get the starting address from the latitude and longitude
			try
			{
			    addresses = geocoder.getFromLocation(startLatitude, startLongitude, 1);    
			    saddr = addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getAddressLine(1) + " " + addresses.get(0).getAddressLine(2);
			    start.setText(saddr);
		        }
			catch(IOException ex)
		        {
			    System.out.println (ex.toString());
		        }
		    }
		    else
		    {
			Context context = getApplicationContext();
			CharSequence text = "No GPS Signal";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
		    }

		}
            }
	});
    
	button.setOnClickListener(new OnClickListener()
	{
	    public void onClick(View v)
	    {
		saddr = start.getText().toString();
		final Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr));
		startActivity(i);
	    }
	});
    }


    private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
	    .setCancelable(false)
	    .setPositiveButton("Open Settings To Enable GPS",
			       new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
				       Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				       startActivity(callGPSSettingIntent);
				   }
			       });
        alertDialogBuilder.setNegativeButton("Cancel",
					     new DialogInterface.OnClickListener(){
						 public void onClick(DialogInterface dialog, int id){
						     dialog.cancel();
						 }
					     });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
