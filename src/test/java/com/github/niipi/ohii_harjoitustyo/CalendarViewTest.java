package com.github.niipi.ohii_harjoitustyo;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
class CalendarViewTest {

    @Test
    void howManyDaysOfWeekToFrameInCalendar() {
        Calendar calendar = Calendar.getInstance(new Locale("fi", "FI"));
        assertEquals(2, new CalendarView().howManyDaysOfWeekToFrameInCalendar());
    }
}