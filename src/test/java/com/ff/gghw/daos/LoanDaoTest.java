package com.ff.gghw.daos;

import java.util.List;
import org.junit.Test;
import org.joda.time.LocalDate;
import static org.junit.Assert.*;

import com.ff.gghw.daos.DaosTestBase;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class LoanDaoTest extends DaosTestBase {
    @Test
    public void testInsertAndFindById() {
        Daos daos = newDaos();
        
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        Loan l = new Loan(0, "client_id", 10000, 1000, dueDate);
        Application a = newApplication(l);
        Extension e = newExtension(l);
        
        daos.loanDao.insert(l);
        daos.applicationDao.insert(a);
        daos.extensionDao.insert(e);
        assertEquals(1, l.getId());
        
        l = daos.loanDao.findById(1);
        assertNotNull(l);
        assertEquals(1, l.getId());
        assertEquals("client_id", l.getClient());
        assertEquals(10000, l.getSum());
        assertEquals(1000, l.getInterest());
        assertEquals(dueDate, l.getDueDate());
        assertEquals(a, l.getApplication());
        assertEquals(1, l.getExtensions().size());
        assertEquals(e, l.getExtensions().get(0));
    }
    
    @Test
    public void testUpdate() {
        Daos daos = newDaos();
        
        LocalDate dueDate1 = new LocalDate(2001, 2, 3);
        LocalDate dueDate2 = new LocalDate(2001, 2, 4);
        Loan l = new Loan(0, "client_id", 10000, 1000, dueDate1);
        Application a = newApplication(l);
        Extension e1 = newExtension(l);
        
        daos.loanDao.insert(l);
        daos.applicationDao.insert(a);
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
        assertEquals(a, l.getApplication());
        assertEquals(2, l.getExtensions().size());
        assertEquals(e1, l.getExtensions().get(0));
        assertEquals(e2, l.getExtensions().get(1));
    }

    @Test
    public void testFindByIdFail() {
        Loan l = newDaos().loanDao.findById(1);
        assertNull(l);
    }
    
    @Test
    public void testFindByClient() {
        Daos daos = newDaos();
        List<Loan> list;
        
        daos.loanDao.insert(newLoan().setClient("client_id_1"));
        daos.loanDao.insert(newLoan().setClient("client_id_2"));
        daos.loanDao.insert(newLoan().setClient("client_id_2"));
        
        list = daos.loanDao.findByClient("client_id");
        assertEquals(0, list.size());
        
        list = daos.loanDao.findByClient("client_id_1");
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
        
        list = daos.loanDao.findByClient("client_id_2");
        assertEquals(2, list.size());
        assertEquals(2, list.get(0).getId());
        assertEquals(3, list.get(1).getId());
    }
}

