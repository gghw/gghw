package com.ff.gghw.apps;

import java.io.StringReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.junit.*;
import org.junit.Test;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        
        String output = runAppWith(input().enter("exit"));
        
        verify(time, times(1)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString, output);
    }
    
    @Test
    public void testEmptyCommand() {
        when(time.get()).thenReturn(new LocalDateTime(2001, 2, 3, 4, 5, 6));
        
        String output = runAppWith(input().enter("").enter("exit"));
        
        verify(time, times(1)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString + "> ", output);
    }
    
    @Test
    public void testUnrecognizedCommand() {
        when(time.get()).thenReturn(new LocalDateTime(2001, 2, 3, 4, 5, 6));
        
        String output = runAppWith(input().enter("bad").enter("exit"));
        
        verify(time, times(2)).get();
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                 + "Unrecognized command: bad\n"
                 + "\nTime: 2001-02-03 04:05:06\n" + menuString
            , output);
    }
    
    @Test
    public void testApply() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 18);
        Loan loan = new Loan(123, "client_id", 10000, 500, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(loan);
        
        String output = runAppWith(input()
            .enter("apply").enter("client_id").enter("10000").enter("15").enter("1.2.3.4")
            .enter("exit"));
        
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
            , output);
    }
    
    @Test
    public void testApplyTypos() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDate dueDate = new LocalDate(2001, 2, 18);
        Loan loan = new Loan(123, "client_id", 10000, 500, dueDate);
        Application application = new Application(456, loan, "client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(loan);
        
        String output = runAppWith(input()
            .enter("apply")
                .enter("").enter("client_id")
                .enter("").enter("abc").enter("4999").enter("50001").enter("10000")
                .enter("").enter("abc").enter("6").enter("31").enter("15")
                .enter("").enter("abc").enter("1234").enter("1.2.3.4")
            .enter("exit"));
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "ClientID(string): " + "ClientID(string): "
                   + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): " + "Sum(cents,5000-50000): "
                   + "Term(days,7-30): " + "Term(days,7-30): " + "Term(days,7-30): " + "Term(days,7-30): " + "Term(days,7-30): "
                   + "Interest will be: 500\n"
                   + "ClientIP(IPv4): " + "ClientIP(IPv4): " + "ClientIP(IPv4): " + "ClientIP(IPv4): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Created loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=500, dueDate=2001-02-18, application=456, extensions=()]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , output);
    }
    
    @Test
    public void testApplyFail() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.applyForLoan("client_id", 10000, 500, 15, "1.2.3.4", timestamp)).thenReturn(null);
        
        String output = runAppWith(input()
            .enter("apply").enter("client_id").enter("10000").enter("15").enter("1.2.3.4")
            .enter("exit"));
        
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
            , output);
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
        
        String output = runAppWith(input().enter("extend").enter("123").enter("exit"));
        
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
            , output);
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
        
        String output = runAppWith(input()
            .enter("extend").enter("").enter("abc").enter("0").enter("123")
            .enter("exit"));
        
        verify(time, times(3)).get();
        verify(appServices, times(1)).extendLoan(123, timestamp);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "LoanID(integer): " + "LoanID(integer): " + "LoanID(integer): " + "LoanID(integer): "
                   + "Timestamp will be: 2001-02-03 04:05:06\n"
                   + "\n"
                   + "Extended loan:\n"
                   + "Loan [id=123, client=client_id, sum=10000, interest=750, dueDate=2001-02-25, application=456, extensions=(789)]\n"
                   + "  * Application [id=456, loan=123, client=client_id, sum=10000, interest=500, termDays=15, ip=1.2.3.4, timestamp=2001-02-03 04:05:06]\n"
                   + "  + Extension [id=789, loan=123, extensionDays=7, addedInterest=250, timestamp=2001-02-03 04:05:06]\n"
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , output);
    }
    
    @Test
    public void testExtendFail() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        when(appServices.extendLoan(123, timestamp)).thenReturn(null);
        
        String output = runAppWith(input().enter("extend").enter("123").enter("exit"));
        
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
            , output);
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
        
        String output = runAppWith(input().enter("list").enter("client_id").enter("exit"));
        
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
            , output);
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
        
        String output = runAppWith(input().enter("list").enter("").enter("client_id").enter("exit"));
        
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
            , output);
    }
    
    @Test
    public void testSkip() {
        LocalDateTime timestamp = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        
        when(time.get()).thenReturn(timestamp);
        
        String output = runAppWith(input().enter("skip").enter("123").enter("exit"));
        
        verify(time, times(2)).get();
        verify(time, times(1)).skipHours(123);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "Hours(0-999): "
                   + "\n"
                   + "Time: 2001-02-03 04:05:06\n" + menuString
            , output);
    }
    
    @Test
    public void testSkipTypos() {
        LocalDateTime timestamp1 = new LocalDateTime(2001, 2, 3, 4, 5, 6);
        LocalDateTime timestamp2 = new LocalDateTime(2001, 6, 5, 4, 3, 2);
        
        when(time.get()).thenReturn(timestamp1).thenReturn(timestamp2);
        
        String output = runAppWith(input()
            .enter("skip").enter("").enter("abc").enter("-1").enter("1000").enter("123")
            .enter("exit"));
        
        verify(time, times(2)).get();
        verify(time, times(1)).skipHours(123);
        verifyNoMoreInteractions(appServices, time);
        
        assertEquals("Time: 2001-02-03 04:05:06\n" + menuString
                   + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): " + "Hours(0-999): "
                   + "\n"
                   + "Time: 2001-06-05 04:03:02\n" + menuString
            , output);
    }
    
    @Before
    public void setUp() {
        appServices = mock(AppServices.class);
        time = mock(Time.class);
    }
    
    private class Input {
        public Input enter(String command) { input += command + "\n"; return this; }
        public String string() { return input; }
        private String input = "";
    }
    
    private Input input() {
        return new Input();
    }
    
    private String runAppWith(Input input) {
        BufferedReader in = new BufferedReader(new StringReader(input.string()));
        
        ByteArrayOutputStream outBytes = new java.io.ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBytes);
        
        CommandLineApp cliApp = new CommandLineApp(appServices, time, in, out);
        try {
            cliApp.run();
        }
        catch ( IOException e ) {
            assertTrue(false);
        }
        
        return outBytes.toString();
    }
    
    private AppServices appServices;
    private Time time;
    
    private final String menuString = ""
        + "Available commands:\n"
        + "  - apply            Apply for loan\n"
        + "  - extend           Extend loan\n"
        + "  - list             List loan history\n"
        + "  - skip             Skip hours of 'current time' (for easier manual time related testing)\n"
        + "  - exit             Exit application\n"
        + "> ";
}

