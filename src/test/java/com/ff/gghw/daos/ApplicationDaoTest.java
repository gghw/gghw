package com.ff.gghw.daos;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.ff.gghw.daos.DaosTestBase;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Loan;

public class ApplicationDaoTest extends DaosTestBase {
    @Test
    public void testInsertAndFindById() {
        Daos daos = newDaos();
        
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Loan l = newLoan();
        Application a = new Application(0, l, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);
        
        daos.loanDao.insert(l);
        daos.applicationDao.insert(a);
        assertEquals(1, a.getId());
        
        a = daos.applicationDao.findById(1);
        assertNotNull(a);
        assertEquals(1, a.getId());
        assertEquals(l, a.getLoan());
        assertEquals("client_id", a.getClient());
        assertEquals(10000, a.getSum());
        assertEquals(1000, a.getInterest());
        assertEquals(30, a.getTermDays());
        assertEquals("1.2.3.4", a.getIp());
        assertEquals(timestamp, a.getTimestamp());
    }
    
    @Test
    public void testFindByIdFail() {
        Daos daos = newDaos();
        Application a = daos.applicationDao.findById(1);
        assertNull(a);
    }
    
    @Test
    public void testCountSinceWithIp() {
        Daos daos = newDaos();
        
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 0, 0);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 2, 3, 5, 0, 0);
        LocalDateTime timestamp3 = new LocalDateTime(2001, 2, 3, 6, 0, 0);
        LocalDateTime timestamp4 = new LocalDateTime(2001, 2, 3, 7, 0, 0);
        Application a1 = new Application(0, newLoan(), "client_id", 10000, 1000, 30, "1.2.3.4", timestamp1);
        Application a2 = new Application(0, newLoan(), "client_id", 10000, 1000, 30, "1.2.3.4", timestamp2);
        Application a3 = new Application(0, newLoan(), "client_id", 10000, 1000, 30, "1.2.3.4", timestamp3);
        Application a4 = new Application(0, newLoan(), "client_id", 10000, 1000, 30, "1.2.3.5", timestamp4);
        
        daos.loanDao.insert(a1.getLoan());
        daos.applicationDao.insert(a1);
        daos.loanDao.insert(a2.getLoan());
        daos.applicationDao.insert(a2);
        daos.loanDao.insert(a3.getLoan());
        daos.applicationDao.insert(a3);
        daos.loanDao.insert(a4.getLoan());
        daos.applicationDao.insert(a4);
        
        assertEquals(3, daos.applicationDao.countSinceWithIp(timestamp1, "1.2.3.4"));
        assertEquals(2, daos.applicationDao.countSinceWithIp(timestamp2, "1.2.3.4"));
        assertEquals(1, daos.applicationDao.countSinceWithIp(timestamp3, "1.2.3.4"));
        assertEquals(0, daos.applicationDao.countSinceWithIp(timestamp4, "1.2.3.4"));
    }
}

