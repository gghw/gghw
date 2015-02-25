package com.ff.gghw.models;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ff.gghw.etc.Helpers;
import com.ff.gghw.models.Application;
import com.ff.gghw.models.Extension;

@Entity
@Table(name="loans")
public class Loan {
    public Loan() {
        this.id = 0;
        this.client = null;
        this.sum = 0;
        this.interest = 0;
        this.dueDate = null;
    }
    
    public Loan(int id, String client, int sum, int interest, LocalDate dueDate) {
        this.id = id;
        this.client = client;
        this.sum = sum;
        this.interest = interest;
        this.dueDate = dueDate;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "client", nullable = false)
    private String client;
    
    @Column(name = "sum", nullable = false)
    private int sum;
    
    @Column(name = "interest", nullable = false)
    private int interest;
    
    @Column(name = "due_date", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dueDate;
    
    @OneToOne (mappedBy="loan", fetch = FetchType.EAGER)
    private Application application = null;
    
    @OneToMany(mappedBy="loan", fetch = FetchType.EAGER)
    private List<Extension> extensions = new ArrayList<Extension>();
    
    public int getId() { return id; }
    public Loan setId(int id) { this.id = id; return this; }
    
    public String getClient() { return client; }
    public Loan setClient(String client) { this.client = client; return this; }
    
    public int getSum() { return sum; }
    public Loan setSum(int sum) { this.sum = sum; return this; }
    
    public int getInterest() { return interest; }
    public Loan setInterest(int interest) { this.interest = interest; return this; }
    
    public LocalDate getDueDate() { return dueDate; }
    public Loan setDueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
    
    public Application getApplication() { return application; }
    public Loan setApplication(Application application) { this.application = application; return this; }
    
    public List<Extension> getExtensions() { return extensions; }
    public Loan setExtensions(List<Extension> extensions) { this.extensions = extensions; return this; }
    public Loan addExtension(Extension extension) {
        extension.setLoan(this);
        extensions.add(extension);
        return this;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 53)
            .append(id).append(client).append(sum).append(interest).append(dueDate)
            .append(application != null ? application.getId() : 0).append(extensionString())
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        
        if ( !(obj instanceof Loan) ) return false;
        Loan other = (Loan) obj;
        if ( id != other.id ) return false;
        if ( !Helpers.objectsEqual(client, other.client) ) return false;
        if ( sum != other.sum ) return false;
        if ( interest != other.interest ) return false;
        if ( !Helpers.objectsEqual(dueDate, other.dueDate) ) return false;
        if ( applicationId(application) != applicationId(other.application) ) return false;
        if ( extensions.size() != other.extensions.size() ) return false;
        ListIterator<Extension> miter = extensions.listIterator();
        ListIterator<Extension> oiter = other.extensions.listIterator();
        while ( miter.hasNext() ) {
            Extension me = miter.next();
            Extension oe = oiter.next();
            if ( me.getId() != oe.getId() ) return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Loan [id=" + id + ", client=" + client + ", sum=" + sum + ", interest=" + interest
            + ", dueDate=" + dueDate + ", application=" + applicationId(application)
            + ", extensions=" + extensionString() + "]";
    }
    
    private int applicationId(Application application) {
        if ( application != null ) {
            return application.getId();
        } else {
            return -1;
        }
    }
    
    private String extensionString() {
        String str = "(";
        boolean first = true;
        for ( Extension extension : extensions ) {
            str += (first ? "" : ",") + extension.getId();
            first = false;
        }
        str += ")";
        return str;
    }
}

