package com.ff.gghw.apps;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.lang.Math;
import java.util.List;
import com.google.common.net.InetAddresses;
import org.joda.time.LocalDateTime;

import com.ff.gghw.services.AppServices;
import com.ff.gghw.rules.LoanRules;
import com.ff.gghw.etc.Time;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;
import com.ff.gghw.etc.Helpers;

public class CommandLineApp {
    public CommandLineApp(AppServices appServices, Time time, BufferedReader in, PrintStream out) {
        this.appServices = appServices;
        this.time = time;
        this.in = in;
        this.out = out;
    }

    public void run() throws java.io.IOException {
        for ( ; ; ) {
            out.println("Time: " + Time.format(time.get()));
            out.println("Available commands:");
            out.println("  - apply            Apply for loan");
            out.println("  - extend           Extend loan");
            out.println("  - list             List loan history");
            out.println("  - skip             Skip hours of 'current time' (for easier manual time related testing)");
            out.println("  - exit             Exit application");
            String cmd = "";
            while ( cmd.length() == 0 ) {
                out.print("> ");
                cmd = in.readLine();
            }

            if ( cmd.equals("apply") ) {
                applyForLoan();
            } else if ( cmd.equals("extend") ) {
                extendLoan();
            } else if ( cmd.equals("list") ) {
                listLoans();
            } else if ( cmd.equals("skip") ) {
                skipTime();
            } else if ( cmd.equals("exit") ) {
                break;
            } else {
                out.println("Unrecognized command: " + cmd);
            }
            
            out.println("");
        }
    }
    
    private void applyForLoan() throws java.io.IOException {
        String client = "";
        while ( client.length() == 0 ) {
            out.print("ClientID(string): ");
            client = in.readLine();
        }
        
        int sum = 0;
        while ( sum < LoanRules.MIN_LOAN || sum > LoanRules.MAX_LOAN ) {
            out.print("Sum(cents," + LoanRules.MIN_LOAN + "-" + LoanRules.MAX_LOAN + "): ");
            sum = Helpers.safeParseInt(in.readLine(), -1);
        }
        
        int termDays = 0;
        while ( termDays < LoanRules.MIN_TERM_DAYS || termDays > LoanRules.MAX_TERM_DAYS ) {
            out.print("Term(days," + LoanRules.MIN_TERM_DAYS + "-" + LoanRules.MAX_TERM_DAYS + "): ");
            termDays = Helpers.safeParseInt(in.readLine(), -1);
        }
        
        final int interest = Math.round(sum * termDays * LoanRules.INTEREST_PER_DAY);
        out.println("Interest will be: " + interest);

        String ip = "";
        while ( !InetAddresses.isInetAddress(ip) ) {
            out.print("ClientIP(IPv4): ");
            ip = in.readLine();
        }
        
        LocalDateTime timestamp = time.get();
        out.println("Timestamp will be: " + Time.format(timestamp));

        Loan l = appServices.applyForLoan(client, sum, interest, termDays, ip, timestamp);
        if ( l != null ) {
            out.println("\nCreated loan:");
            describeLoan(l);
        } else {
            out.println("\nLoan denied!");
        }
    }

    private void extendLoan() throws java.io.IOException {
        int loan = 0;
        while ( loan <= 0 ) {
            out.print("LoanID(integer): ");
            loan = Helpers.safeParseInt(in.readLine(), 0);
        }

        LocalDateTime timestamp = time.get();
        out.println("Timestamp will be: " + Time.format(timestamp));

        Loan l = appServices.extendLoan(loan, timestamp);
        if ( l != null ) {
            out.println("\nExtended loan:");
            describeLoan(l);
        } else {
            out.println("\nLoan extension failed!");
        }
    }
    
    private void listLoans() throws java.io.IOException {
        String client = "";
        while ( client.length() == 0 ) {
            out.print("ClientID(string): ");
            client = in.readLine();
        }
        List<Loan> list = appServices.listLoans(client);
        out.println("\nListing loans:");
        for ( Loan loan : list ) {
            describeLoan(loan);
        }
    }

    private void skipTime() throws java.io.IOException {
        int hours = -1;
        while ( hours < 0 || hours > 999 ) {
            out.print("Hours(0-999): ");
            hours = Helpers.safeParseInt(in.readLine(), -1);
        }
        time.skipHours(hours);
    }
    
    private void describeLoan(Loan loan) {
        Application application = appServices.getLoanApplication(loan.getId());
        List<Extension> extensions = appServices.listLoanExtensions(loan.getId());
        out.println("" + loan);
        out.println("  * " + application);
        for ( Extension extension : extensions ) {
            out.println("  + " + extension);
        }
    }

    private AppServices appServices;
    private Time time;
    private BufferedReader in;
    private PrintStream out;
}

