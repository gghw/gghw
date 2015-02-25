package com.ff.gghw.daos;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;
import org.joda.time.LocalDate;

import com.ff.gghw.daos.DaosTestBase;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class LoanDaoTest extends DaosTestBase {
    @Test
    public void testInsertAndFindById() {
        Daos daos = newDaos();
        
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        Loan l = new Loan(0, "client_id", 10000, 1000, dueDate);
        Extension e = newExtension(l);
        
        daos.loanDao.insert(l);
        daos.extensionDao.insert(e);
        assertEquals(1, l.getId());
        
        l = daos.loanDao.findById(1);
        assertNotNull(l);
        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
        assertEquals(1, l.getExtensions().size());
        assertEquals(e, l.getExtensions().get(0));
    }
    
    @Test
    public void testUpdate() {
        Daos daos = newDaos();
        
        LocalDate dueDate1 = new LocalDate(2001, 2, 3);
        LocalDate dueDate2 = new LocalDate(2001, 2, 4);
        Loan l = new Loan(0, "client_id", 10000, 1000, dueDate1);
        Extension e1 = newExtension(l);
        
        daos.loanDao.insert(l);
        daos.extensionDao.insert(e1);
        
        l.setClient("id_client");
        l.setSum(10001);
        l.setInterest(1001);
        l.setDueDate(dueDate2);
        Extension e2 = newExtension(l);
        daos.extensionDao.insert(e2);
        daos.loanDao.update(l);
        assertEquals(1, l.getId());
        
        l = daos.loanDao.findById(1);
        assertNotNull(l);
        assertEquals(1, l.getId());
        assertEquals("id_client", l.getClient());
        assertEquals(10001, l.getSum());
        assertEquals(1001, l.getInterest());
        assertEquals(dueDate2, l.getDueDate());
        assertEquals(2, l.getExtensions().size());
        assertEquals(e1, l.getExtensions().get(0));
        assertEquals(e2, l.getExtensions().get(1));
    }

    @Test
    public void testFindByIdFail() {
        LoanDao dao = newCtx().getBean("loanDao", LoanDao.class);
        Loan l = dao.findById(1);
        assertNull(l);
    }
    
    @Test
    public void testFindByClient() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        LoanDao dao = newCtx().getBean("loanDao", LoanDao.class);
        List<Loan> list;
        Loan l;
        
        l = new Loan(0, "client_id_1", 10000, 1000, dueDate);
        dao.insert(l);
        
        l.setId(0);
        l.setClient("client_id_2");
        dao.insert(l);
        
        l.setId(0);
        l.setClient("client_id_2");
        dao.insert(l);
        
        list = dao.findByClient("client_id");
        assertEquals(0, list.size());
        
        list = dao.findByClient("client_id_1");
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
        
        list = dao.findByClient("client_id_2");
        assertEquals(2, list.size());
        assertEquals(2, list.get(0).getId());
        assertEquals(3, list.get(1).getId());
    }
}

