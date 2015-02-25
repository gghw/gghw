package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDateTime;

import com.ff.gghw.models.ModelsTestBase;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class ExtensionTest extends ModelsTestBase {
    @Test
    public void testGetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Loan l = newLoan();
        Extension e = new Extension(1, l, 7, 500, timestamp);
        
        assertEquals(1, e.getId());
        assertSame(l, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }
    
    @Test
    public void testSetters() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Loan l = newLoan();
        Extension e = new Extension();
        
        e.setId(1);
        e.setLoan(l);
        e.setExtensionDays(7);
        e.setAddedInterest(500);
        e.setTimestamp(timestamp);
        
        assertEquals(1, e.getId());
        assertSame(l, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        {
            Extension e1 = newExtension(newLoan());
            assertEquals(e1, e1);
            assertEquals(e1.hashCode(), e1.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            assertNotEquals(e1, null);
        }
        {
            Extension e1 = newExtension(newLoan());
            assertNotEquals(e1, new Object());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            assertEquals(e1, e2);
            assertEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(null);
            Extension e2 = newExtension(null);
            assertEquals(e1, e2);
            assertEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            e2.setId(2);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            e2.getLoan().setId(2);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(null);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(null);
            Extension e2 = newExtension(newLoan());
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            e2.setExtensionDays(8);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            e2.setAddedInterest(1001);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan());
            e2.setTimestamp(new LocalDateTime(2001, 2, 3, 4, 5, 7));
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        Extension e = newExtension(newLoan());
        
        e.getLoan().setId(2);
        assertEquals(
              "Extension [id=1, loan=2, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
        
        e.setLoan(null);
        assertEquals(
              "Extension [id=1, loan=0, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
    }
}

