package com.ff.gghw.models;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import org.joda.time.LocalDateTime;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.models.Extension;

public class ExtensionDaoTest extends TestBase {
    @Test
    public void testInsertAndFindById() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        ExtensionDao dao = newCtx().getBean("extensionDao", ExtensionDao.class);
        Extension e = new Extension(0, 2, 7, 500, timestamp);
        dao.insert(e);
        assertEquals(1, e.getId());
        
        e = dao.findById(1);
        assertNotNull(e);
        assertEquals(1, e.getId());
        assertEquals(2, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }

    @Test
    public void testFindByIdFail() {
        ExtensionDao dao = newCtx().getBean("extensionDao", ExtensionDao.class);
        Extension e = dao.findById(1);
        assertNull(e);
    }

    @Test
    public void testFindByLoan() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        List<Extension> list;
        ExtensionDao dao = newCtx().getBean("extensionDao", ExtensionDao.class);
        Extension e = new Extension(0, 2, 7, 500, timestamp);
        dao.insert(e);
        
        e.setId(0);
        e.setLoan(3);
        dao.insert(e);

        e.setId(0);
        e.setLoan(3);
        dao.insert(e);

        list = dao.findByLoan(1);
        assertEquals(0, list.size());

        list = dao.findByLoan(2);
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());

        list = dao.findByLoan(3);
        assertEquals(2, list.size());
        assertEquals(2, list.get(0).getId());
        assertEquals(3, list.get(1).getId());
    }
}

