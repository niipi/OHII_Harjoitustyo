package com.github.niipi.ohii_harjoitustyo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Creates calendar functionality based on which the main program draws the watering schedule.
 * @author Niina Piiroinen
 **/
public class CalendarView {

    private java.util.Calendar calendar = Calendar.getInstance(new Locale("fi", "FI"));

    public CalendarView() {
        calendar.clear();
        calendar.set(Calendar.YEAR, Calendar.MONTH, 1, 0, 0);
    }

    /**
     * Returns current day of month as an integer.
     * @return int
     **/
    int whatDayNumberIsIt() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns current date as a String. Used for showing watering days from WateringScheduler.
     * @return String
     **/
    String whatDayIsIt() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        return day+"."+month+".";
    }

    /**
     * Returns current month as a String. Used for drawing a calendar page in Main.
     * @return String
     **/
    String whatMonthIsIt() {
        SimpleDateFormat dfMonth = new SimpleDateFormat("MMMM");
        String month = dfMonth.format(calendar.getTime());
        return month;
    }

    /**
     * Bump calendar to next day.
     **/
    void addOneDay() {
        calendar.add(calendar.DAY_OF_MONTH, 1);
    }

    /**
     * Checks how many weekdays there are before the 1st day of the current month.
     * Returns an integer value showing the difference. This integer is used in the method calView to draw the "empty days" at the beginning of the month.
     * @return int
     **/
     int howManyDaysOfWeekToFrameInCalendar() {
         Calendar temp = Calendar.getInstance();
         temp.clear();
         temp.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0,0);
         int dayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
         int dayOfMonth = temp.get(Calendar.DAY_OF_MONTH);
         int weekOfMonth = temp.get(Calendar.WEEK_OF_MONTH);
         int weekdaysToFirstOfMonth = 0;
         if (weekOfMonth == 1) {
            if (dayOfWeek - 1 == dayOfMonth)
                weekdaysToFirstOfMonth = 0;
            else if (dayOfWeek - 1 > dayOfMonth)
                weekdaysToFirstOfMonth = (dayOfWeek) - dayOfMonth;
        }
        return weekdaysToFirstOfMonth;
    }

    /**
     * Checks how many weeks current month consists of. Used for drawing the calendar in Main.
     * @return int
     **/
    int howManyWeeksInCurrentMonth() {
        return calendar.getActualMaximum(calendar.WEEK_OF_MONTH);
    }

    /**
     * Compares current day to the last day of current month. Returns true if date is the same.
     * @return boolean
     **/
    boolean isItTheLastDayOfThisMonth() {
         return calendar.get(calendar.DAY_OF_MONTH) == calendar.getActualMaximum(calendar.DAY_OF_MONTH);
    }

    /**
     * Compares two Strings of dates and returns and the difference of days as integer.
     * @params String dateA, String dateB
     * @return int
     **/
    int howManyDaysBetweenDates(String dateA, String dateB) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.");
            Date a = df.parse(dateA);
            Date b = df.parse(dateB);
            long millisecondsBetween = b.getTime() - a.getTime(); // Calculate difference of two dates in milliseconds
            long daysBetween = millisecondsBetween / (1000 * 60 * 60 * 24); // Calculate difference in days from milliseconds
            return (int) daysBetween;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
