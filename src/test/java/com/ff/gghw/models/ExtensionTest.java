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
        {
            Extension e1 = newExtension();
            assertEquals(e1, e1);
            assertEquals(e1.hashCode(), e1.hashCode());
        }
        {
            Extension e1 = newExtension();
            assertNotEquals(e1, null);
        }
        {
            Extension e1 = newExtension();
            assertNotEquals(e1, new Object());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            assertEquals(e1, e2);
            assertEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            e2.setId(2);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            e2.setLoan(3);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            e2.setExtensionDays(8);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            e2.setAddedInterest(1001);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension();
            Extension e2 = newExtension();
            e2.setTimestamp(new LocalDateTime(2001, 2, 3, 4, 5, 7));
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension(1, 2, 7, 500, timestamp);
        assertEquals(
              "Extension [id=1, loan=2, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
    }
    
    private Extension newExtension() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        return new Extension(1, 2, 7, 500, timestamp);
    }
}

