package com.OneTwoThreePleasantSt;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;
import java.lang.StringBuilder;
import java.lang.Integer;

public class CalendarClass
{
    private Calendar cal;
    private int year;
    private int month;
    private int day;

    public CalendarClass()
    {
	cal = Calendar.getInstance();
	year = cal.get(cal.YEAR);
	month = cal.get(Calendar.MONTH);
	day = cal.get(cal.DAY_OF_MONTH);
    }
    
    public CalendarClass(String setDate)
    {
	// sets the current calendar date to the specified date
	// setDate needs to be in the format 'YYYY-MM-DD'
	cal = Calendar.getInstance();
	String splitDate [] = setDate.split("-");
	year = Integer.parseInt(splitDate[0]);
	month = (Integer.parseInt(splitDate[1]) - 1);
	day = Integer.parseInt(splitDate[2]);
	cal.set(year,month,day);
    }
    
    public int getIntMonth(){
	return month;
    }
    
    public String getMonth()
    {
	return String.format(Locale.US,"%tB",cal);
    }
    
    public String getYear()
    {
	return Integer.toString(year);
    }
    
    public int getStartDay()
    {
	return getFirstDay(month,year);
    }
    
    public int getEndDay()
    {
	return cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 1;
    }
    
    public void nextMonth()
    {
	cal.add(Calendar.MONTH, +1);
	year = cal.get(cal.YEAR);
	month = month = cal.get(Calendar.MONTH);
    }
    
    public void prevMonth()
    {
	cal.add(Calendar.MONTH, -1);
	year = cal.get(cal.YEAR);
	month = cal.get(Calendar.MONTH);
    }
    
    
    
    private int getFirstDay(int month, int year)
    {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            return 0;
        case Calendar.MONDAY:
            return 1;
        case Calendar.TUESDAY:
            return 2;
        case Calendar.WEDNESDAY:
            return 3;
        case Calendar.THURSDAY:
            return 4;
        case Calendar.FRIDAY:
            return 5;
        case Calendar.SATURDAY:
            return 6;
        }
        return 0;
    }
    
    public int getCurrentDay(){
	return day;
    }
    
    public String getNextDay(){
	// get the next day, and returns a database acceptable query formatted date for it 'YYYY-MM-DD'
	cal.add(Calendar.DATE, 1);
	return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
    }
    
    public String getPrevDay(){
	// gets the previous day, and returns a database acceptable query formatted date for it 'YYYY-MM-DD'
	cal.add(Calendar.DATE, -1);
	return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
    }
    
    public Calendar getCalendar(){
	return cal;
    }
    
    public String[] createWeek(){
	Calendar tempcal = Calendar.getInstance();
        String[] daweek = new String [7];               //eventually this will be another object                                                             
 
        String wString = "";

        for (int i=0; i < 7; i++){
	    wString = tempcal.get(Calendar.YEAR) + "-" + (tempcal.get(Calendar.MONTH)+1) + "-" + tempcal.get(Calendar.DAY_OF_MONTH);
	    tempcal.add(Calendar.DAY_OF_MONTH, 1);
	    daweek [i] = wString;
        }
	
        return daweek;
    }
    
    public String nameOfWeek(int dayOfWeek){
	String name="";
	switch (dayOfWeek) {
	case Calendar.SUNDAY:
	    name = "Sun"; break;
	case Calendar.MONDAY:
	    name = "Mon"; break;
	case Calendar.TUESDAY:
	    name = "Tue"; break;
	case Calendar.WEDNESDAY:
	    name = "Wed"; break;
	case Calendar.THURSDAY:
	    name = "Thur"; break;
	case Calendar.FRIDAY:
	    name = "Fri"; break;
	case Calendar.SATURDAY:
	    name = "Sat"; break;
	}
	return name;
    }
    
    public String weekToString (String [] weekString){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < weekString.length; i++ ){
	    sb.append(weekString[i]+"\n");
	}
        return sb.toString();
    }
    
}