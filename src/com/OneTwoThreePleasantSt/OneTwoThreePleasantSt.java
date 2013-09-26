package com.OneTwoThreePleasantSt;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class OneTwoThreePleasantSt extends TabActivity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();

        // Tab for Home
        TabSpec homespec = tabHost.newTabSpec("Home");
        homespec.setIndicator("Home", getResources().getDrawable(R.drawable.icon_home_tab));
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homespec.setContent(homeIntent);
        
        // Tab for Calendar
        TabSpec calendarspec = tabHost.newTabSpec("Calendar");
        calendarspec.setIndicator("Calendar", getResources().getDrawable(R.drawable.icon_calendar_tab));
        Intent calendarIntent = new Intent(this, CalendarActivity.class);
        calendarspec.setContent(calendarIntent);
        
        // Tab for Directions
        TabSpec directionsspec = tabHost.newTabSpec("Directions");
        directionsspec.setIndicator("Directions", getResources().getDrawable(R.drawable.icon_directions_tab));
        Intent directionssIntent = new Intent(this, DirectionsActivity.class);
        directionsspec.setContent(directionssIntent);
        
        // Tab for Search
        TabSpec searchspec = tabHost.newTabSpec("Search");
        searchspec.setIndicator("Search", getResources().getDrawable(R.drawable.icon_search_tab));
        Intent searchsIntent = new Intent(this, SearchActivity.class);
        searchspec.setContent(searchsIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(homespec); 
        tabHost.addTab(calendarspec); 
        tabHost.addTab(directionsspec); 
        tabHost.addTab(searchspec); 
    }
}
