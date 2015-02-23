package com.ff.gghw.services;

import java.lang.Math;
import java.util.List;
import org.joda.time.LocalTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import com.google.common.net.InetAddresses;

import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;
import com.ff.gghw.models.Loan;
import com.ff.gghw.daos.ApplicationDao;
import com.ff.gghw.daos.ExtensionDao;
import com.ff.gghw.daos.LoanDao;
import com.ff.gghw.rules.LoanRules;
import com.ff.gghw.etc.Assert;

public class AppServices {
    public AppServices(ApplicationDao applicationDao, ExtensionDao extensionDao, LoanDao loanDao) {
        this.applicationDao = applicationDao;
        this.extensionDao = extensionDao;
        this.loanDao = loanDao;
    }

    public Loan applyForLoan(String client, int sum, int interest, int termDays, String ip, LocalDateTime timestamp) {
        Assert.arg(client != null && client.length() > 0);
        Assert.arg(sum >= LoanRules.MIN_LOAN && sum <= LoanRules.MAX_LOAN);
        Assert.arg(termDays >= LoanRules.MIN_TERM_DAYS && termDays <= LoanRules.MAX_TERM_DAYS);
        Assert.arg(interest >= 0);
        Assert.arg(ip != null && InetAddresses.isInetAddress(ip));
        Assert.arg(timestamp != null);

        Application application = new Application();
        application.setClient(client);
        application.setSum(sum);
        application.setInterest(interest);
        application.setTermDays(termDays);
        application.setIp(ip);
        application.setTimestamp(timestamp);
        
        if ( !validateApplication(application) ) {
            return null;
        }

        applicationDao.insert(application);
        
        Loan loan = new Loan();
        loan.setClient(client);
        loan.setSum(sum);
        loan.setInterest(interest);
        loan.setDueDate(timestamp.toLocalDate().plusDays(termDays));
        loanDao.insert(loan);
        
        application.setLoan(loan.getId());
        applicationDao.update(application);

        return loan;
    }
    
    public Loan extendLoan(int loan_id, LocalDateTime timestamp) {
        Assert.arg(loan_id > 0);
        Assert.arg(timestamp != null);

        Loan loan = loanDao.findById(loan_id);
        if ( loan == null ) return null;
        
        Extension extension = new Extension();
        extension.setLoan(loan_id);
        extension.setExtensionDays(LoanRules.EXTEND_DAYS);
        extension.setAddedInterest(Math.round(loan.getInterest()*LoanRules.EXTEND_INTEREST));
        extension.setTimestamp(timestamp);

        loan.setInterest(loan.getInterest() + extension.getAddedInterest());
        loan.setDueDate(loan.getDueDate().plusDays(extension.getExtensionDays()));
        extensionDao.insert(extension);
        loanDao.update(loan);

        return loan;
    }

    public List<Loan> listLoans(String client) {
        Assert.arg(client != null && client.length() > 0);
        return loanDao.findByClient(client);
    }

    public Application getLoanApplication(int loan_id) {
        Assert.arg(loan_id > 0);
        return applicationDao.findByLoan(loan_id);
    }

    public List<Extension> listLoanExtensions(int loan_id) {
        Assert.arg(loan_id > 0);
        return extensionDao.findByLoan(loan_id);
    }

    private boolean validateApplication(Application application) {
        if ( illegalApplicationTimeAndSum(application) ) return false;
        if ( tooManyPreviousApplicationsFromSameIp(application) ) return false;
        return true;
    }
    
    private boolean illegalApplicationTimeAndSum(Application application) {
        LocalTime timeOfDay = application.getTimestamp().toLocalTime();
        return application.getSum() == LoanRules.MAX_LOAN
            && timeOfDay.isAfter(LoanRules.DENY_MAX_LOAN_INTERVAL_START)
            && timeOfDay.isBefore(LoanRules.DENY_MAX_LOAN_INTERVAL_END);
    }

    private boolean tooManyPreviousApplicationsFromSameIp(Application application) {
        int prevCount = applicationDao.countSinceWithIp(
              application.getTimestamp().minusHours(LoanRules.PREVIOUS_APPLICTION_TIME_LIMIT_HOURS)
            , application.getIp());
        return prevCount >= LoanRules.PREVIOUS_APPLICTION_COUNT_LIMIT;
    }

	private ApplicationDao applicationDao;    
	private ExtensionDao extensionDao;
	private LoanDao loanDao;
}

