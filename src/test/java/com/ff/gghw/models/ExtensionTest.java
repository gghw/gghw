package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDateTime;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.models.Extension;

public class ExtensionTest extends TestBase {
    @Test
    public void testGetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension(1, 2, 7, 500, timestamp);

        assertEquals(1, e.getId());
        assertEquals(2, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }

    @Test
    public void testSetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension();
        
        e.setId(1);
        e.setLoan(2);
        e.setExtensionDays(7);
        e.setAddedInterest(500);
        e.setTimestamp(timestamp);

        assertEquals(1, e.getId());
        assertEquals(2, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp3 = new LocalDateTime(2001, 2, 3, 4, 5, 7);
        Extension e1 = new Extension(1, 2, 7, 500, timestamp1);
        Extension e2 = new Extension(1, 2, 7, 500, timestamp2);
        Extension e3 = new Extension(1, 2, 7, 500, timestamp3);
        
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1, e3);
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension(1, 2, 7, 500, timestamp);
        assertEquals(
              "Extension [id=1, loan=2, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
    }
}

