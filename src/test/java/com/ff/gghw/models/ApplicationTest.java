package com.ff.gghw.models;

import org.junit.Test;
import org.joda.time.LocalDateTime;
import static org.junit.Assert.*;

import com.ff.gghw.models.ModelsTestBase;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Loan;

public class ApplicationTest extends ModelsTestBase {
    @Test
    public void testGetters() {
        Loan l = newLoan();
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Application a = new Application(1, l, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);
        
        assertEquals(1, a.getId());
        assertSame(l, a.getLoan());
        assertEquals("client_id", a.getClient());
        assertEquals(10000, a.getSum());
        assertEquals(1000, a.getInterest());
        assertEquals(30, a.getTermDays());
        assertEquals("1.2.3.4", a.getIp());
        assertSame(timestamp, a.getTimestamp());
    }
    
    @Test
    public void testSetters() {
        Loan l = newLoan();
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Application a = new Application();
        
        assertSame(a, a.setId(1));
        assertSame(a, a.setLoan(l));
        assertSame(a, a.setClient("client_id"));
        assertSame(a, a.setSum(10000));
        assertSame(a, a.setInterest(1000));
        assertSame(a, a.setTermDays(30));
        assertSame(a, a.setIp("1.2.3.4"));
        assertSame(a, a.setTimestamp(timestamp));
        
        assertEquals(1, a.getId());
        assertSame(l, a.getLoan());
        assertEquals("client_id", a.getClient());
        assertEquals(10000, a.getSum());
        assertEquals(1000, a.getInterest());
        assertEquals(30, a.getTermDays());
        assertEquals("1.2.3.4", a.getIp());
        assertEquals(timestamp, a.getTimestamp());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        {
            Application a1 = newApplication(newLoan());
            assertEquals(a1, a1);
            assertEquals(a1.hashCode(), a1.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            assertNotEquals(a1, null);
        }
        {
            Application a1 = newApplication(newLoan());
            assertNotEquals(a1, new Object());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan());
            assertEquals(a1, a2);
            assertEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(null);
            Application a2 = newApplication(null);
            assertEquals(a1, a2);
            assertEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setId(2);
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan().setId(2));
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(null);
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(null);
            Application a2 = newApplication(newLoan());
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setClient("id_client");
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setSum(10001);
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setInterest(1001);
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setTermDays(31);
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setIp("1.2.3.5");
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
        {
            Application a1 = newApplication(newLoan());
            Application a2 = newApplication(newLoan()).setTimestamp(new LocalDateTime(2001, 2, 3, 4, 5, 7));
            assertNotEquals(a1, a2);
            assertNotEquals(a1.hashCode(), a2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        Application a = newApplication(newLoan().setId(2));
        assertEquals(
              "Application [id=1, loan=2, client=client_id, sum=10000, interest=1000, termDays=30, ip=1.2.3.4"
              + ", timestamp=2001-02-03 04:05:06]"
            , "" + a);
        
        a.setLoan(null);
        assertEquals(
              "Application [id=1, loan=-1, client=client_id, sum=10000, interest=1000, termDays=30, ip=1.2.3.4"
              + ", timestamp=2001-02-03 04:05:06]"
            , "" + a);
    }
}

