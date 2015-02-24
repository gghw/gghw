package com.ff.gghw.etc;

import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDateTime;

import com.ff.gghw.etc.Time;

public class TimeTest {
    @Test
    public void testGet() {
        Time time = new Time();
        LocalDateTime t = time.get();
        LocalDateTime t1 = new LocalDateTime().minusMinutes(1);
        LocalDateTime t2 = new LocalDateTime().plusMinutes(1);
        assertTrue(t.isAfter(t1));
        assertTrue(t.isBefore(t2));
    }
    
    @Test
    public void testSkipHours() {
        Time time = new Time();
        time.skipHours(1);
        {
            LocalDateTime t = time.get();
            LocalDateTime t1 = new LocalDateTime().plusMinutes(60-1);
            LocalDateTime t2 = new LocalDateTime().plusMinutes(60+1);
            assertTrue(t.isAfter(t1));
            assertTrue(t.isBefore(t2));
        }
        time.skipHours(9);
        {
            LocalDateTime t = time.get();
            LocalDateTime t1 = new LocalDateTime().plusMinutes(600-1);
            LocalDateTime t2 = new LocalDateTime().plusMinutes(600+1);
            assertTrue(t.isAfter(t1));
            assertTrue(t.isBefore(t2));
        }
    }
    
    @Test
    public void testFormat() {
        assertEquals("2001-02-03 04:05:06", Time.format(new LocalDateTime(2001, 2, 3, 4, 5, 6)));
    }
}

