package com.github.niipi.ohii_harjoitustyo;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class WateringCalendar {

    protected static Calendar calendar = new GregorianCalendar(new Locale("fi", "FI"));
    private static SimpleDateFormat dfMonth = new SimpleDateFormat("MMMM");
    private static Date today = calendar.getTime();
    private static Text month = new Text(dfMonth.format(today));

    private static Rectangle rectangle() {
        Rectangle r = new Rectangle(100,100);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        return r;
    }



    private static StackPane stackdate() {
        StackPane s = new StackPane();
        //Text now = new Text(String.valueOf(calendar.getTime()));
        s.getChildren().add(rectangle());
        //s.getChildren().add(now);
        return s;
    }

    /**
     * Sets calendar to first week of current month, checks how many weekdays there are before the 1st of the current month.
     * Returns an integer value showing the difference. This integer is used in the method calView to draw the "empty days" at the beginning of the month.
     * @return int
     **/
    private static int matchDayOfWeekToFrameInCalendar() {
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

    private static boolean monthChanged() {
        boolean nextMonth = false;
        SimpleDateFormat sf = new SimpleDateFormat("M");
        int thismonth = Integer.parseInt(sf.format(today))-1;
        int compare = calendar.get(calendar.MONTH);
        if (compare != thismonth) {
            nextMonth = true;
        }
        return nextMonth;
    }

    public static VBox calView() {
        VBox calpage = new VBox();
        GridPane cal = new GridPane();
        int empties = matchDayOfWeekToFrameInCalendar();
        int dayNum = 1;
        for (int weeknum = 0; weeknum < 5; weeknum++) {
            for (int weekday = 0; weekday < 7; weekday++) {
                StackPane cell = stackdate();
                if (empties > 0) {
                    Rectangle empty = rectangle();
                    empty.setFill(Color.GRAY);
                    cell.getChildren().add(empty);
                    empties--;
                }
                else if (weeknum == 4 && calendar.get(calendar.DAY_OF_MONTH) == calendar.getActualMaximum(calendar.DAY_OF_MONTH)) {
                    dayNum = 1;
                    Rectangle empty = rectangle();
                    empty.setFill(Color.GRAY);
                    cell.getChildren().add(empty);
                }
                else {
                    Text day = new Text(String.valueOf(dayNum));
                    calendar.set(Calendar.DAY_OF_MONTH, dayNum);
                    cell.getChildren().add(day);
                    dayNum++;
                }
                cal.add(cell, weekday, weeknum);
            }
        }
        calpage.getChildren().addAll(month, cal);
        return calpage;
    }
}
