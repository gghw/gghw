package com.ff.gghw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ff.gghw.etc.Time;
import com.ff.gghw.etc.Helpers;

@Entity
@Table(name="applications")
public class Application {
    public Application() {
        this.id = 0;
        this.loan = null;
        this.client = null;
        this.sum = 0;
        this.interest = 0;
        this.termDays = 0;
        this.ip = null;
        this.timestamp = null;
    }
    
    public Application(int id, Loan loan, String client, int sum, int interest
            , int termDays, String ip, LocalDateTime timestamp) {
        this.id = id;
        this.loan = loan;
        this.client = client;
        this.sum = sum;
        this.interest = interest;
        this.termDays = termDays;
        this.ip = ip;
        this.timestamp = timestamp;
        if ( this.loan != null ) {
            loan.setApplication(this);
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    @JoinColumn(name = "loan", nullable = false)
    private Loan loan;
    
    @Column(name = "client", nullable = false)
    private String client;
    
    @Column(name = "sum", nullable = false)
    private int sum;
    
    @Column(name = "interest", nullable = false)
    private int interest;
    
    @Column(name = "term_days", nullable = false)
    private int termDays;
    
    @Column(name = "ip", nullable = false)
    private String ip;
    
    @Column(name = "timestamp", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime timestamp;
    
    public int getId() { return id; }
    public Application setId(int id) { this.id = id; return this; }
    
    public Loan getLoan() { return loan; }
    public Application setLoan(Loan loan) { this.loan = loan; return this; }
    
    public String getClient() { return client; }
    public Application setClient(String client) { this.client = client; return this; }
    
    public int getSum() { return sum; }
    public Application setSum(int sum) { this.sum = sum; return this; }
    
    public int getInterest() { return interest; }
    public Application setInterest(int interest) { this.interest = interest; return this; }
    
    public int getTermDays() { return termDays; }
    public Application setTermDays(int termDays) { this.termDays = termDays; return this; }
    
    public String getIp() { return ip; }
    public Application setIp(String ip) { this.ip = ip; return this; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public Application setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(id).append(loanId(loan)).append(client).append(sum)
            .append(interest).append(termDays).append(ip).append(timestamp)
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( !(obj instanceof Application) ) return false;
        Application other = (Application) obj;
        if ( id != other.id ) return false;
        if ( loanId(loan) != loanId(other.loan) ) return false;
        if ( !Helpers.objectsEqual(client, other.client) ) return false;
        if ( sum != other.sum ) return false;
        if ( interest != other.interest ) return false;
        if ( termDays != other.termDays ) return false;
        if ( !Helpers.objectsEqual(ip, other.ip) ) return false;
        if ( !Helpers.objectsEqual(timestamp, other.timestamp) ) return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Application [id=" + id + ", loan=" + loanId(loan) + ", client=" + client
            + ", sum=" + sum + ", interest=" + interest + ", termDays=" + termDays
            + ", ip=" + ip + ", timestamp=" + Time.format(timestamp) + "]";
    }
    
    private static int loanId(Loan loan) {
        if ( loan != null ) {
            return loan.getId();
        } else {
            return -1;
        }
    }
}

