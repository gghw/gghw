package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDateTime;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.models.Application;

public class ApplicationTest extends TestBase {
    @Test
    public void testGetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Application a = new Application(1, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);

        assertEquals(1, a.getId());
        assertEquals(2, a.getLoan());
        assertEquals("client_id", a.getClient());
        assertEquals(10000, a.getSum());
        assertEquals(1000, a.getInterest());
        assertEquals(30, a.getTermDays());
        assertEquals("1.2.3.4", a.getIp());
        assertEquals(new LocalDateTime(2001, 2, 3, 4, 5, 6), a.getTimestamp());
    }

    @Test
    public void testSetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Application a = new Application();
        
        a.setId(1);
        a.setLoan(2);
        a.setClient("client_id");
        a.setSum(10000);
        a.setInterest(1000);
        a.setTermDays(30);
        a.setIp("1.2.3.4");
        a.setTimestamp(timestamp);

        assertEquals(1, a.getId());
        assertEquals(2, a.getLoan());
        assertEquals("client_id", a.getClient());
        assertEquals(10000, a.getSum());
        assertEquals(1000, a.getInterest());
        assertEquals(30, a.getTermDays());
        assertEquals("1.2.3.4", a.getIp());
        assertEquals(timestamp, a.getTimestamp());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp3 = new LocalDateTime(2001, 2, 3, 4, 5, 7);
        Application a1 = new Application(1, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp1);
        Application a2 = new Application(1, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp2);
        Application a3 = new Application(1, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp3);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
        assertNotEquals(a1.hashCode(), a3.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Application a = new Application(1, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);
        assertEquals(
              "Application [id=1, loan=2, client=client_id, sum=10000, interest=1000, termDays=30, ip=1.2.3.4"
              + ", timestamp=2001-02-03 04:05:06]"
            , "" + a);
    }
}

