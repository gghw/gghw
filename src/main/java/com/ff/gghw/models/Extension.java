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
@Table(name="extensions")
public class Extension {
    public Extension() {
        this.id = 0;
        this.loan = 0;
        this.extensionDays = 0;
        this.addedInterest = 0;
        this.timestamp = null;
    }

    public Extension(int id, int loan, int extensionDays, int addedInterest, LocalDateTime timestamp) {
        this.id = id;
        this.loan = loan;
        this.extensionDays = extensionDays;
        this.addedInterest = addedInterest;
        this.timestamp = timestamp;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "loan", nullable = false)
	private int loan;

	@Column(name = "extension_days", nullable = false)
	private int extensionDays;

	@Column(name = "added_interest", nullable = false)
	private int addedInterest;

	@Column(name = "timestamp", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime timestamp;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getLoan() { return loan; }
	public void setLoan(int loan) { this.loan = loan; }

	public int getExtensionDays() { return extensionDays; }
	public void setExtensionDays(int extensionDays) { this.extensionDays = extensionDays; }

	public int getAddedInterest() { return addedInterest; }
	public void setAddedInterest(int addedInterest) { this.addedInterest = addedInterest; }

	public LocalDateTime getTimestamp() { return timestamp; }
	public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

	@Override
	public int hashCode() {
        return new HashCodeBuilder(13, 43)
            .append(id).append(loan).append(extensionDays).append(addedInterest).append(timestamp)
            .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( !(obj instanceof Extension) ) return false;
		Extension other = (Extension) obj;
		if ( id != other.id ) return false;
		if ( loan != other.loan ) return false;
		if ( extensionDays != other.extensionDays ) return false;
		if ( addedInterest != other.addedInterest ) return false;
        if ( !Helpers.objectsEqual(timestamp, other.timestamp) ) return false;
		return true;
	}
    
    @Override
	public String toString() {
        return "Extension [id=" + id + ", loan=" + loan + ", extensionDays=" + extensionDays
            + ", addedInterest=" + addedInterest + ", timestamp=" + Time.format(timestamp) + "]";
    }
}

