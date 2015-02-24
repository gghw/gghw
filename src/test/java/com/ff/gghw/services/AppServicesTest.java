package com.ff.gghw.services;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.ArrayList;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;
import java.lang.IllegalArgumentException;
import java.lang.reflect.*;

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

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanNullClientId() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan(null, 10000, 1000, 15, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanBadClientId() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("", 10000, 1000, 15, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanSumTooLow() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 4999, 1000, 15, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanSumTooHigh() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 50001, 1000, 15, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanBadInterest() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, -1, 15, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanTermDaysTooLow() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, 1000, 6, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanTermDaysTooHigh() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, 1000, 31, "1.2.3.4", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanNullIp() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, 1000, 15, null, timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanBadIp() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, 1000, 15, "1234", timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyForLoanNullTimestamp() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.applyForLoan("client_id", 10000, 1000, 15, "1.2.3.4", null);
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
    public void testExtendLoanFailBecauseNonExistingLoanId() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);

        LoanDao mockedLoanDao = mock(LoanDao.class);
        when(mockedLoanDao.findById(123)).thenReturn(null);

        AppServices appServices = new AppServices(null, null, mockedLoanDao);
        assertNull(appServices.extendLoan(123, timestamp));

        verify(mockedLoanDao, times(1)).findById(123);
        verifyNoMoreInteractions(mockedLoanDao);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testExtendLoanBadLoanId() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        AppServices appServices = new AppServices(null, null, null);
        appServices.extendLoan(0, timestamp);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testExtendLoanNullTimestamp() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.extendLoan(123, null);
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

    @Test(expected=IllegalArgumentException.class)
    public void testListLoansWithNullClientId() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.listLoans(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testListLoansWithBadClientId() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.listLoans("");
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

    @Test(expected=IllegalArgumentException.class)
    public void testGetLoanApplicationWithBadLoanId() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.getLoanApplication(0);
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

    @Test(expected=IllegalArgumentException.class)
    public void testListLoanExtensionsWithBadLoanId() {
        AppServices appServices = new AppServices(null, null, null);
        appServices.listLoanExtensions(0);
    }

    @Test
    public void testIllegalApplicationTimeAndSum() {
        try {
            Class[] argClasses = new Class[1];
            argClasses[0] = Application.class;
            AppServices appServices = new AppServices(null, null, null);
            Method method = AppServices.class.getDeclaredMethod("illegalApplicationTimeAndSum", argClasses);
            method.setAccessible(true);

            LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 0, 0, 0);
            Application application = new Application(0, 0, "client_id", 10000, 1000, 15, "1.2.3.4", timestamp);
            Object[] args = new Object[1];
            args[0] = application;
        
            assertFalse((boolean)method.invoke(appServices, args));
            application.setSum(50000);
            assertFalse((boolean)method.invoke(appServices, args));
            application.setTimestamp(new LocalDateTime(2001, 2, 3, 9, 0, 0));
            assertFalse((boolean)method.invoke(appServices, args));
            application.setTimestamp(new LocalDateTime(2001, 2, 3, 4, 5, 6));
            assertTrue((boolean)method.invoke(appServices, args));
        }
        catch ( Exception e ) {
            assertTrue(false);
        }
    }
}

