package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import org.joda.time.LocalDate;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Loan;

public class LoanTest extends TestBase {
    @Test
    public void testGetters() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        Loan l = new Loan(1, "client_id", 10000, 1000, dueDate);

        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
    }

    @Test
    public void testSetters() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        Loan l = new Loan();
        
        l.setId(1);
        l.setClient("client_id");
        l.setSum(10000);
        l.setInterest(1000);
        l.setDueDate(dueDate);

        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDate dueDate1 = new LocalDate(2001, 2, 3);
        LocalDate dueDate2 = new LocalDate(2001, 2, 3);
        LocalDate dueDate3 = new LocalDate(2001, 2, 4);
        Loan l1 = new Loan(1, "client_id", 10000, 1000, dueDate1);
        Loan l2 = new Loan(1, "client_id", 10000, 1000, dueDate2);
        Loan l3 = new Loan(1, "client_id", 10000, 1000, dueDate3);

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
        assertNotEquals(l1, l3);
        assertNotEquals(l1.hashCode(), l3.hashCode());
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
              "Loan [id=1, client=client_id, sum=10000, interest=1000, dueDate=2001-02-03]"
            , "" + l);
    }
}

