package com.ff.gghw.apps;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.ff.gghw.apps.CommandLineApp;
import com.ff.gghw.services.AppServices;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;
import com.ff.gghw.etc.Time;

public class CommandLineAppTest {
    @Test
    public void testExit() {
        when(time.get()).thenReturn(new LocalDateTime(2001, 2, 3, 4, 5, 6));
        makeInStream("exit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(1)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString, outBytes.toString());
    }
    
    @Test
    public void testEmptyCommand() {
        when(time.get()).thenReturn(new LocalDateTime(2001, 2, 3, 4, 5, 6));
        makeInStream("\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(1)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString + "> ", outBytes.toString());
    }
    
    @Test
    public void testUnrecognizedCommand() {
        when(time.get()).thenReturn(new LocalDateTime(2001, 2, 3, 4, 5, 6));
        makeInStream("bad\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(2)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                 + "Unrecognized command: bad\n"
                 + "\nTime: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testApply() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 18);
        Loan loan = new Loan(123, "client_id", 10000, 500, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(loan);
        makeInStream("apply\nclient_id\n10000\n15\n1.2.3.4\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): "
                   + "Sum(cents,5000-50000): "
                   + "Term(days,7-30): "
                   + "Interest will be: 500\n"
                   + "ClientIP(IPv4): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Created loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=500, dueDate=2001-02-18, application=456, extensions=()]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testApplyTypos() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 18);
        Loan loan = new Loan(123, "client_id", 10000, 500, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(loan);
        makeInStream("apply\n\nclient_id\n\n4999\n50001\n10000\n\n6\n31\n15\n\n1234\n1.2.3.4\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): " + "ClientID(string): "
                   + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): "
                   + "Term(days,7-30): " + "Term(days,7-30): " + "Term(days,7-30): " + "Term(days,7-30): "
                   + "Interest will be: 500\n"
                   + "ClientIP(IPv4): " + "ClientIP(IPv4): " + "ClientIP(IPv4): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Created loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=500, dueDate=2001-02-18, application=456, extensions=()]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testApplyFail() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(null);
        makeInStream("apply\nclient_id\n10000\n15\n1.2.3.4\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): "
                   + "Sum(cents,5000-50000): "
                   + "Term(days,7-30): "
                   + "Interest will be: 500\n"
                   + "ClientIP(IPv4): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Loan denied!\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testExtend() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 25);
        Loan loan = new Loan(123, "client_id", 10000, 750, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        Extension extension = new Extension(789, loan, 7, 250, timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(loan);
        makeInStream("extend\n123\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).extendLoan(123, timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "LoanID(integer): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Extended loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testExtendTypos() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 25);
        Loan loan = new Loan(123, "client_id", 10000, 750, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        Extension extension = new Extension(789, loan, 7, 250, timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(loan);
        makeInStream("extend\n\n0\n123\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).extendLoan(123, timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "LoanID(integer): " + "LoanID(integer): " + "LoanID(integer): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Extended loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testExtendFail() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(null);
        makeInStream("extend\n123\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).extendLoan(123, timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "LoanID(integer): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Loan extension failed!\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }

    @Test
    public void testList() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 25);
        Loan loan = new Loan(123, "client_id", 10000, 750, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 250, 8, "1.2.3.4", timestamp);
        Extension extension1 = new Extension(789, null, 7, 250, timestamp);
        Extension extension2 = new Extension(987, null, 7, 250, timestamp);
        List<Loan> loanList = new ArrayList(); loanList.add(loan); loanList.add(loan);
        loan.addExtension(extension1); loan.addExtension(extension2);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.listLoans("client_id")).thenReturn(loanList);
        makeInStream("list\nclient_id\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(2)).get();
        verify(appServices, times(1)).listLoans("client_id");
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): "
                   + "\n"
                   + "Listing loans:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789,987)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=250, termDays=8, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=987, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789,987)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=250, termDays=8, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=987, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testListTypos() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 25);
        Loan loan = new Loan(123, "client_id", 10000, 750, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 250, 8, "1.2.3.4", timestamp);
        Extension extension1 = new Extension(789, null, 7, 250, timestamp);
        Extension extension2 = new Extension(987, null, 7, 250, timestamp);
        List<Loan> loanList = new ArrayList(); loanList.add(loan); loanList.add(loan);
        loan.addExtension(extension1); loan.addExtension(extension2);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.listLoans("client_id")).thenReturn(loanList);
        makeInStream("list\n\nclient_id\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(2)).get();
        verify(appServices, times(1)).listLoans("client_id");
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): " + "ClientID(string): "
                   + "\n"
                   + "Listing loans:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789,987)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=250, termDays=8, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=987, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789,987)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=250, termDays=8, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=987, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testSkip() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(null);
        makeInStream("skip\n123\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(2)).get();
        verify(time, times(1)).skipHours(123);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "Hours(0-999): "
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Test
    public void testSkipTypos() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(null);
        makeInStream("skip\n\nabc\n-1\n1000\n123\nexit\n");
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try { cliApp.run(); } catch ( IOException e ) { assertTrue(false); }
        
        verify(time, times(2)).get();
        verify(time, times(1)).skipHours(123);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): "
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , outBytes.toString());
    }
    
    @Before
    public void setUp() {
        appServices = mock(AppServices.class);
        time = mock(Time.class);
        in = null;
        outBytes = new java.io.ByteArrayOutputStream();
        out = new PrintStream(outBytes);
    }
    
    private void makeInStream(String string) {
        in = new BufferedReader(new StringReader(string));
    }
    
    private AppServices appServices;
    private Time time;
    private BufferedReader in;
    private ByteArrayOutputStream outBytes;
    private PrintStream out;
    
    private final String menuString = ""
        + "Available commands:\n"
        + "  - apply            Apply for loan\n"
        + "  - extend           Extend loan\n"
        + "  - list             List loan history\n"
        + "  - skip             Skip hours of 'current time' (for easier manual time related testing)\n"
        + "  - exit             Exit application\n"
        + "> ";
}

