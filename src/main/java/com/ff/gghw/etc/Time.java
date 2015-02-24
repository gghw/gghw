package com.ff.gghw.etc;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// Helper class that returns current time + some number of hours, so that the console user can
// manually test the time related requirements by adjusting what the app thinks is "current time".
public class Time {
    public Time() {
        addHours = 0;
    }
    
    public LocalDateTime get() {
        LocalDateTime ldt = new LocalDateTime();
        return ldt.plusHours(addHours);
    }
    
    public void skipHours(int addHours) {
        this.addHours += addHours;
    }
    
    public static String format(LocalDateTime time) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return format.print(time);
    }
    
    private int addHours;
}

