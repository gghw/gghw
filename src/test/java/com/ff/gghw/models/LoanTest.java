package com.ff.gghw.models;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.joda.time.LocalDate;
import static org.junit.Assert.*;

import com.ff.gghw.models.ModelsTestBase;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class LoanTest extends ModelsTestBase {
    @Test
    public void testGetters() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        Loan l = new Loan(1, "client_id", 10000, 1000, dueDate);
        
        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
        assertNull(l.getApplication());
        assertEquals(new ArrayList<Extension>(), l.getExtensions());
    }
    
    @Test
    public void testSetters() {
        Application a = newApplication(null);
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        List<Extension> list = new ArrayList<Extension>();
        Loan l = new Loan();
        
        assertSame(l, l.setId(1));
        assertSame(l, l.setClient("client_id"));
        assertSame(l, l.setSum(10000));
        assertSame(l, l.setInterest(1000));
        assertSame(l, l.setDueDate(dueDate));
        assertSame(l, l.setApplication(a));
        assertSame(l, l.setExtensions(list));
        
        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
        assertSame(a, l.getApplication());
        assertSame(list, l.getExtensions());
    }
    
    @Test
    public void testAddExtension() {
        Loan l = new Loan();
        List<Extension> list = new ArrayList<Extension>();
        
        assertEquals(list, l.getExtensions());
        
        list.add(newExtension(null));
        assertSame(l, l.addExtension(list.get(0)));
        assertEquals(list, l.getExtensions());
        
        list.add(newExtension(null));
        assertSame(l, l.addExtension(list.get(1)));
        assertEquals(list, l.getExtensions());
    }
    
    @Test
    public void testEqualsAndHashCode() {
        {
            Loan l1 = newLoan();
            assertEquals(l1, l1);
            assertEquals(l1.hashCode(), l1.hashCode());
        }
        {
            Loan l1 = newLoan();
            assertNotEquals(l1, null);
        }
        {
            Loan l1 = newLoan();
            assertNotEquals(l1, new Object());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            assertEquals(l1, l2);
            assertEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newApplication(l1); newApplication(l2);
            assertEquals(l1, l2);
            assertEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newExtension(l1); newExtension(l2);
            assertEquals(l1, l2);
            assertEquals(l1.hashCode(), l2.hashCode());
            newExtension(l1).setId(2); newExtension(l2).setId(2);
            assertEquals(l1, l2);
            assertEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan().setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan().setClient("id_client");
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan().setSum(10001);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan().setInterest(1001);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan().setDueDate(new LocalDate(2001, 2, 4));
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newApplication(l1);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newApplication(l2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newApplication(l1); newApplication(l2).setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newExtension(l1);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newExtension(l2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newExtension(l1); newExtension(l2).setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        Loan l = newLoan();
        assertEquals(
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03"
              + ", application=-1, extensions=()]"
            , "" + l);
        
        newExtension(l);
        assertEquals(
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03"
              + ", application=-1, extensions=(1)]"
            , "" + l);
        
        newExtension(l).setId(2);
        assertEquals(
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03"
              + ", application=-1, extensions=(1,2)]"
            , "" + l);
        
        newApplication(l).setId(3);
        assertEquals(
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03"
              + ", application=3, extensions=(1,2)]"
            , "" + l);
    }
}

