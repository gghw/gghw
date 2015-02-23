package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import org.joda.time.LocalDateTime;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.models.Application;

public class ApplicationDaoTest extends TestBase {
    @Test
    public void testInsertAndFindById() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        ApplicationDao dao = newCtx().getBean("applicationDao", ApplicationDao.class);
        Application a = new Application(0, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);

        dao.insert(a);
        assertEquals(1, a.getId());
        
        a = dao.findById(1);
        assertNotNull(a);
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
    public void testUpdate() {
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 2, 3, 4, 5, 7);
        ApplicationDao dao = newCtx().getBean("applicationDao", ApplicationDao.class);
        Application a = new Application(0, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp1);
        dao.insert(a);

        a.setLoan(3);
        a.setClient("id_client");
        a.setSum(10001);
        a.setInterest(1001);
        a.setTermDays(31);
        a.setIp("2.3.4.5");
        a.setTimestamp(timestamp2);
        dao.update(a);
        assertEquals(1, a.getId());
        
        a = dao.findById(1);
        assertNotNull(a);
        assertEquals(1, a.getId());
        assertEquals(3, a.getLoan());
        assertEquals("id_client", a.getClient());
        assertEquals(10001, a.getSum());
        assertEquals(1001, a.getInterest());
        assertEquals(31, a.getTermDays());
        assertEquals("2.3.4.5", a.getIp());
        assertEquals(timestamp2, a.getTimestamp());
    }

    @Test
    public void testFindByIdFail() {
        ApplicationDao dao = newCtx().getBean("applicationDao", ApplicationDao.class);
        Application a = dao.findById(1);
        assertNull(a);
    }

    @Test
    public void testFindByLoan() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        ApplicationDao dao = newCtx().getBean("applicationDao", ApplicationDao.class);
        Application a = new Application(0, 2, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);

        dao.insert(a);
        assertEquals(1, a.getId());
        
        a = dao.findByLoan(1);
        assertNull(a);

        a = dao.findByLoan(2);
        assertNotNull(a);
        assertEquals(1, a.getId());
    }

    @Test
    public void testCountSinceWithIp() {
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 0, 0);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 2, 3, 5, 0, 0);
        LocalDateTime timestamp3 = new LocalDateTime(2001, 2, 3, 6, 0, 0);
        LocalDateTime timestamp4 = new LocalDateTime(2001, 2, 3, 7, 0, 0);
        ApplicationDao dao = newCtx().getBean("applicationDao", ApplicationDao.class);
        Application a = new Application(0, 2, "client_id", 10000, 1000, 30, "1.2.3.4", null);

        a.setTimestamp(timestamp1);
        dao.insert(a);

        a.setId(0);
        a.setTimestamp(timestamp2);
        dao.insert(a);

        a.setId(0);
        a.setTimestamp(timestamp3);
        dao.insert(a);

        a.setId(0);
        a.setIp("2.3.4.5");
        a.setTimestamp(timestamp4);
        dao.insert(a);
        
        assertEquals(3, dao.countSinceWithIp(timestamp1, "1.2.3.4"));
        assertEquals(2, dao.countSinceWithIp(timestamp2, "1.2.3.4"));
        assertEquals(1, dao.countSinceWithIp(timestamp3, "1.2.3.4"));
        assertEquals(0, dao.countSinceWithIp(timestamp4, "1.2.3.4"));
    }
}

