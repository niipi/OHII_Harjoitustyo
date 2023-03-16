package com.github.niipi.ohii_harjoitustyo;

import javafx.scene.text.Text;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Creates calendar functionality based on which the main program draws the watering schedule.
 * @author Niina Piiroinen
 **/
public class WateringCalendar {

    Calendar calendar = Calendar.getInstance(new Locale("fi", "FI"));
    private SimpleDateFormat dfMonth = new SimpleDateFormat("MMMM");
    Text month = new Text(dfMonth.format(calendar.getTime()));

    public WateringCalendar() {
        //empty constructor
    }

    /**
     * Checks how many weekdays there are before the 1st day of the current month.
     * Returns an integer value showing the difference. This integer is used in the method calView to draw the "empty days" at the beginning of the month.
     * @return int
     **/
     int howManyDaysOfWeekToFrameInCalendar() {
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        int weekdaysToFirstOfMonth = 0;
        if (weekOfMonth == 1) {
            if (dayOfWeek - 1 == dayOfMonth)
                weekdaysToFirstOfMonth = 0;
            else if (dayOfWeek - 1 > dayOfMonth)
                weekdaysToFirstOfMonth = (dayOfWeek - 1) - dayOfMonth;
        }
        return weekdaysToFirstOfMonth;
    }

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
}
