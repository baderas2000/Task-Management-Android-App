package com.example.se2_team06;

import static org.junit.Assert.assertEquals;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.Time;

import org.junit.Test;

public class DateAndTimeTesting {

    @Test
    public void testStringDate(){
        String expected = "12.2.2023";
        Date date = new Date(12,1,2023);
        String output = date.toString();

        assertEquals(expected, output);
    }

    @Test
    public void testStringTime(){
        String expected = "14:30";
        Time date = new Time(14,30);
        String output = date.toString();

        assertEquals(expected, output);
    }
}
