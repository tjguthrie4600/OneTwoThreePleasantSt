package com.OneTwoThreePleasantSt;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;

public class CalendarClass
{
    private Calendar cal;
    private int year;
    private int month;

    public CalendarClass()
    {
	cal = Calendar.getInstance();
	year = cal.get(cal.YEAR);
	month = month = cal.get(Calendar.MONTH);
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
	month = month = cal.get(Calendar.MONTH);
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
    
}
