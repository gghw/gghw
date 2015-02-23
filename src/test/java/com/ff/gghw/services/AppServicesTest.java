package com.ff.gghw.services;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.ArrayList;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;

import com.ff.gghw.services.AppServices;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;

public class AppServicesTest {
    @Test
    public void testApplyForLoan() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 18);

        Loan loan = new Loan(0, "client_id", 10000, 1000, dueDate);
        Application application = new Application(0, 0, "client_id", 10000, 1000, 15, "1.2.3.4", timestamp);

        ApplicationDao mockedApplicationDao = mock(ApplicationDao.class);
        LoanDao mockedLoanDao = mock(LoanDao.class);
        when(mockedApplicationDao.countSinceWithIp(timestamp.minusHours(24), "1.2.3.4")).thenReturn(2);

        AppServices appServices = new AppServices(mockedApplicationDao, null, mockedLoanDao);
        assertEquals(loan, appServices.applyForLoan("client_id", 10000, 1000, 15, "1.2.3.4", timestamp));

        verify(mockedApplicationDao, times(1)).countSinceWithIp(timestamp.minusHours(24), "1.2.3.4");
        verify(mockedApplicationDao, times(1)).insert(application);
        verify(mockedLoanDao, times(1)).insert(loan);
        verify(mockedApplicationDao, times(1)).update(application);
        verifyNoMoreInteractions(mockedApplicationDao, mockedLoanDao);
    }

    @Test
    public void testApplyForLoanFailBecauseIllegalTimeAndSum() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        assertNull(appServices.applyForLoan("client_id", 50000, 1000, 15, "1.2.3.4", timestamp));
    }

    @Test
    public void testApplyForLoanFailBecauseTooManyPreviousApplications() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);

        ApplicationDao mockedApplicationDao = mock(ApplicationDao.class);
        when(mockedApplicationDao.countSinceWithIp(timestamp.minusHours(24), "1.2.3.4")).thenReturn(3);

        AppServices appServices = new AppServices(mockedApplicationDao, null, null);
        assertNull(appServices.applyForLoan("client_id", 10000, 1000, 15, "1.2.3.4", timestamp));

        verify(mockedApplicationDao, times(1)).countSinceWithIp(timestamp.minusHours(24), "1.2.3.4");
        verifyNoMoreInteractions(mockedApplicationDao);
    }

    @Test
    public void testExtendLoan() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate oldDueDate = new LocalDate(2001, 2, 3);
        LocalDate newDueDate = new LocalDate(2001, 2, 10);

        Loan oldLoan = new Loan(123, "client_id", 10000, 1000, oldDueDate);
        Loan newLoan = new Loan(123, "client_id", 10000, 1500, newDueDate);
        Extension extension = new Extension(0, 123, 7, 500, timestamp);

        ExtensionDao mockedExtensionDao = mock(ExtensionDao.class);
        LoanDao mockedLoanDao = mock(LoanDao.class);
        when(mockedLoanDao.findById(123)).thenReturn(oldLoan);

        AppServices appServices = new AppServices(null, mockedExtensionDao, mockedLoanDao);
        assertEquals(newLoan, appServices.extendLoan(123, timestamp));

        verify(mockedLoanDao, times(1)).findById(123);
        verify(mockedExtensionDao, times(1)).insert(extension);
        verify(mockedLoanDao, times(1)).update(newLoan);
        verifyNoMoreInteractions(mockedExtensionDao, mockedLoanDao);
    }

    @Test
    public void testExtendLoanBadLoanId() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);

        LoanDao mockedLoanDao = mock(LoanDao.class);
        when(mockedLoanDao.findById(123)).thenReturn(null);

        AppServices appServices = new AppServices(null, null, mockedLoanDao);
        assertNull(appServices.extendLoan(123, timestamp));

        verify(mockedLoanDao, times(1)).findById(123);
        verifyNoMoreInteractions(mockedLoanDao);
    }


    @Test
    public void testListLoans() {
        List<Loan> loanList = new ArrayList();

        LoanDao mockedLoanDao = mock(LoanDao.class);
        when(mockedLoanDao.findByClient("client_id")).thenReturn(loanList);

        AppServices appServices = new AppServices(null, null, mockedLoanDao);
        assertSame(loanList, appServices.listLoans("client_id"));

        verify(mockedLoanDao, times(1)).findByClient("client_id");
        verifyNoMoreInteractions(mockedLoanDao);
    }
    
    @Test
    public void testGetLoanApplication() {
        Application application = new Application();

        ApplicationDao mockedApplicationDao = mock(ApplicationDao.class);
        when(mockedApplicationDao.findByLoan(123)).thenReturn(application);

        AppServices appServices = new AppServices(mockedApplicationDao, null, null);
        assertSame(application, appServices.getLoanApplication(123));

        verify(mockedApplicationDao, times(1)).findByLoan(123);
        verifyNoMoreInteractions(mockedApplicationDao);
    }
    
    @Test
    public void testListLoanExtensions() {
        List<Extension> extensionList = new ArrayList();

        ExtensionDao mockedExtensionDao = mock(ExtensionDao.class);
        when(mockedExtensionDao.findByLoan(123)).thenReturn(extensionList);

        AppServices appServices = new AppServices(null, mockedExtensionDao, null);
        assertSame(extensionList, appServices.listLoanExtensions(123));

        verify(mockedExtensionDao, times(1)).findByLoan(123);
        verifyNoMoreInteractions(mockedExtensionDao);
    }
}

