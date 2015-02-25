package com.ff.gghw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ff.gghw.models.Loan;
import com.ff.gghw.etc.Time;
import com.ff.gghw.etc.Helpers;

@Entity
@Table(name="extensions")
public class Extension {
    public Extension() {
        this.id = 0;
        this.loan = null;
        this.extensionDays = 0;
        this.addedInterest = 0;
        this.timestamp = null;
    }
    
    public Extension(int id, Loan loan, int extensionDays, int addedInterest, LocalDateTime timestamp) {
        this.id = id;
        this.loan = loan;
        this.extensionDays = extensionDays;
        this.addedInterest = addedInterest;
        this.timestamp = timestamp;
        if ( loan != null ) {
            this.loan.addExtension(this);
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan", nullable = false)
    private Loan loan;
    
    @Column(name = "extension_days", nullable = false)
    private int extensionDays;
    
    @Column(name = "added_interest", nullable = false)
    private int addedInterest;
    
    @Column(name = "timestamp", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime timestamp;
    
    public int getId() { return id; }
    public Extension setId(int id) { this.id = id; return this; }
    
    public Loan getLoan() { return loan; }
    public Extension setLoan(Loan loan) { this.loan = loan; return this; }
    
    public int getExtensionDays() { return extensionDays; }
    public Extension setExtensionDays(int extensionDays) { this.extensionDays = extensionDays; return this; }
    
    public int getAddedInterest() { return addedInterest; }
    public Extension setAddedInterest(int addedInterest) { this.addedInterest = addedInterest; return this; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public Extension setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 43)
            .append(id).append(loanId(loan)).append(extensionDays)
            .append(addedInterest).append(timestamp)
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( !(obj instanceof Extension) ) return false;
        Extension other = (Extension) obj;
        if ( id != other.id ) return false;
        if ( loanId(loan) != loanId(other.loan) ) return false;
        if ( extensionDays != other.extensionDays ) return false;
        if ( addedInterest != other.addedInterest ) return false;
        if ( !Helpers.objectsEqual(timestamp, other.timestamp) ) return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Extension [id=" + id + ", loan=" + loanId(loan) + ", extensionDays=" + extensionDays
            + ", addedInterest=" + addedInterest + ", timestamp=" + Time.format(timestamp) + "]";
    }
    
    private static int loanId(Loan loan) {
        if ( loan != null ) {
            return loan.getId();
        } else {
            return -1;
        }
    }
}

