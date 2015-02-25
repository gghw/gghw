package com.ff.gghw.models;

import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDate;

import com.ff.gghw.models.ModelsTestBase;
import com.ff.gghw.daos.LoanDao;
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
        assertEquals(new ArrayList<Extension>(), l.getExtensions());
    }
    
    @Test
    public void testSetters() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        List<Extension> list = new ArrayList<Extension>();
        Loan l = new Loan();
        
        l.setId(1);
        l.setClient("client_id");
        l.setSum(10000);
        l.setInterest(1000);
        l.setDueDate(dueDate);
        l.setExtensions(list);
        
        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
        assertSame(list, l.getExtensions());
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
            Loan l2 = newLoan();
            l2.setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            l2.setClient("id_client");
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            l2.setSum(10001);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            l2.setInterest(1001);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            l2.setDueDate(new LocalDate(2001, 2, 4));
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newApplication(l1);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
            newApplication(l2).setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
        {
            Loan l1 = newLoan();
            Loan l2 = newLoan();
            newExtension(l1);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
            newExtension(l2).setId(2);
            assertNotEquals(l1, l2);
            assertNotEquals(l1.hashCode(), l2.hashCode());
        }
    }
    
    @Test
    public void testToString() {
        Loan l = new Loan();
        
        l.setId(1);
        l.setClient("client_id");
        l.setSum(10000);
        l.setInterest(1000);
        l.setDueDate(new LocalDate(2001, 2, 3));
        
        assertEquals(
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03, application=0, extensions=()]"
            , "" + l);
        
        newExtension(l);
        assertEquals(
            "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03, application=0, extensions=(1)]"
          , "" + l);
        
        newExtension(l).setId(2);
        assertEquals(
            "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03, application=0, extensions=(1,2)]"
          , "" + l);
        
        newApplication(l).setId(3);
        assertEquals(
                "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03, application=3, extensions=(1,2)]"
              , "" + l);
    }
}

