package com.ff.gghw.daos;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;

import com.ff.gghw.TestBase;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public abstract class DaosTestBase extends TestBase {
    protected Application newApplication(Loan loan) {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        return new Application(0, loan, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);
    }
    
    protected Extension newExtension(Loan loan) {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        return new Extension(0, loan, 7, 500, timestamp);
    }
    
    protected Loan newLoan() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        return new Loan(0, "client_id", 10000, 1000, dueDate);
    }
    
    protected Daos newDaos() {
        ApplicationContext ctx = newCtx();
        return new Daos(
              ctx.getBean("applicationDao", ApplicationDao.class)
            , ctx.getBean("extensionDao", ExtensionDao.class)
            , ctx.getBean("loanDao", LoanDao.class));
    }
    
    protected class Daos {
        public Daos(ApplicationDao applicationDao, ExtensionDao extensionDao, LoanDao loanDao) {
            this.applicationDao = applicationDao;
            this.extensionDao = extensionDao;
            this.loanDao = loanDao;
        }
        public ApplicationDao applicationDao;
        public ExtensionDao extensionDao;
        public LoanDao loanDao;
    }
}

