package com.ff.gghw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ff.gghw.etc.Helpers;

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

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getClient() { return client; }
	public void setClient(String client) { this.client = client; }

	public int getSum() { return sum; }
	public void setSum(int sum) { this.sum = sum; }

	public int getInterest() { return interest; }
	public void setInterest(int interest) { this.interest = interest; }

	public LocalDate getDueDate() { return dueDate; }
	public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

	@Override
	public int hashCode() {
        return new HashCodeBuilder(19, 53)
            .append(id).append(client).append(sum).append(interest).append(dueDate)
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
		return true;
	}
    
    @Override
	public String toString() {
        return "Loan [id=" + id + ", client=" + client + ", sum=" + sum
            + ", interest=" + interest + ", dueDate=" + dueDate + "]";
    }
}

