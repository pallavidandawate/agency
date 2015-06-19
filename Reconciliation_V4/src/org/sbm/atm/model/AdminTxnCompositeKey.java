package org.sbm.atm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class AdminTxnCompositeKey implements Serializable 
{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2210757619435302243L;
	
	@Id
	@Column
	String time;
	
	@Column
	@Temporal(TemporalType.DATE)
	Date date;
	
	@ManyToOne
	@JoinColumn(name="atmId")
	private ATM atm;
	
	
	public AdminTxnCompositeKey()
	{
		
	}
	
	public AdminTxnCompositeKey(String time, Date date, ATM atm)
	{
		this.time = time;
		this.date = date;
		this.atm=atm;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ATM getAtm() {
		return atm;
	}

	public void setAtm(ATM atm) {
		this.atm = atm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminTxnCompositeKey other = (AdminTxnCompositeKey) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	
	
	
}
