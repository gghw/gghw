package com.ff.gghw.models;

import org.junit.Test;
import org.joda.time.LocalDateTime;
import static org.junit.Assert.*;

import com.ff.gghw.models.ModelsTestBase;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class ExtensionTest extends ModelsTestBase {
    @Test
    public void testGetters() {
        Loan l = newLoan();
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension(1, l, 7, 500, timestamp);
        
        assertEquals(1, e.getId());
        assertSame(l, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }
    
    @Test
    public void testSetters() {
        Loan l = newLoan();
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Extension e = new Extension();
        
        assertSame(e, e.setId(1));
        assertSame(e, e.setLoan(l));
        assertSame(e, e.setExtensionDays(7));
        assertSame(e, e.setAddedInterest(500));
        assertSame(e, e.setTimestamp(timestamp));
        
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
            Extension e2 = newExtension(newLoan()).setId(2);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan().setId(2));
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
            Extension e2 = newExtension(newLoan()).setExtensionDays(8);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan()).setAddedInterest(1001);
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
        {
            Extension e1 = newExtension(newLoan());
            Extension e2 = newExtension(newLoan()).setTimestamp(new LocalDateTime(2001, 2, 3, 4, 5, 7));
            assertNotEquals(e1, e2);
            assertNotEquals(e1.hashCode(), e2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        Extension e = newExtension(newLoan().setId(2));
        assertEquals(
              "Extension [id=1, loan=2, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
        
        e.setLoan(null);
        assertEquals(
              "Extension [id=1, loan=-1, extensionDays=7, addedInterest=500, timestamp=2001-02-03 04:05:06]"
            , "" + e);
    }
}

