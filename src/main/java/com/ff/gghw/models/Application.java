package com.ff.gghw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
        this.loan = 0;
        this.client = null;
        this.sum = 0;
        this.interest = 0;
        this.termDays = 0;
        this.ip = null;
        this.timestamp = null;
    }

    public Application(int id, int loan, String client, int sum, int interest
            , int termDays, String ip, LocalDateTime timestamp) {
        this.id = id;
        this.loan = loan;
        this.client = client;
        this.sum = sum;
        this.interest = interest;
        this.termDays = termDays;
        this.ip = ip;
        this.timestamp = timestamp;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "loan", nullable = false)
	private int loan;

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
	public void setId(int id) { this.id = id; }

	public int getLoan() { return loan; }
	public void setLoan(int loan) { this.loan = loan; }

	public String getClient() { return client; }
	public void setClient(String client) { this.client = client; }

	public int getSum() { return sum; }
	public void setSum(int sum) { this.sum = sum; }

	public int getInterest() { return interest; }
	public void setInterest(int interest) { this.interest = interest; }

	public int getTermDays() { return termDays; }
	public void setTermDays(int termDays) { this.termDays = termDays; }

	public String getIp() { return ip; }
	public void setIp(String ip) { this.ip = ip; }

	public LocalDateTime getTimestamp() { return timestamp; }
	public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

	@Override
	public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(id).append(loan).append(client).append(sum)
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
		if ( loan != other.loan ) return false;
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
        return "Application [id=" + id + ", loan=" + loan + ", client=" + client + ", sum=" + sum
            + ", interest=" + interest + ", termDays=" + termDays + ", ip=" + ip
            + ", timestamp=" + Time.format(timestamp) + "]";
    }
}

