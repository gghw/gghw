package com.ff.gghw.daos;

import java.util.List;
import org.junit.Test;
import org.joda.time.LocalDateTime;
import static org.junit.Assert.*;

import com.ff.gghw.daos.DaosTestBase;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class ExtensionDaoTest extends DaosTestBase {
    @Test
    public void testInsertAndFindById() {
        Daos daos = newDaos();
        
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        Loan l = newLoan();
        Extension e = new Extension(0, l, 7, 500, timestamp);
        
        daos.loanDao.insert(l);
        daos.extensionDao.insert(e);
        assertEquals(1, e.getId());
        
        e = daos.extensionDao.findById(1);
        assertNotNull(e);
        assertEquals(1, e.getId());
        assertEquals(l, e.getLoan());
        assertEquals(7, e.getExtensionDays());
        assertEquals(500, e.getAddedInterest());
        assertEquals(timestamp, e.getTimestamp());
    }
    
    @Test
    public void testFindByIdFail() {
        Extension e = newDaos().extensionDao.findById(1);
        assertNull(e);
    }
}

