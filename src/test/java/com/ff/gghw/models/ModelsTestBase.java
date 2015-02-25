package com.ff.gghw.models;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;

import com.ff.gghw.TestBase;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public abstract class ModelsTestBase extends TestBase {
    protected Application newApplication(Loan loan) {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        return new Application(1, loan, "client_id", 10000, 1000, 30, "1.2.3.4", timestamp);
    }
    
    protected Extension newExtension(Loan loan) {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        return new Extension(1, loan, 7, 500, timestamp);
    }
    
    protected Loan newLoan() {
        LocalDate dueDate = new LocalDate(2001, 2, 3);
        return new Loan(1, "client_id", 10000, 1000, dueDate);
    }
}

