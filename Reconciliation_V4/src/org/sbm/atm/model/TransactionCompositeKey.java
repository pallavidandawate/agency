package org.sbm.atm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TransactionCompositeKey implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5242837467162807866L;
	String journalNumber;
	double txnAmount;
	
	public TransactionCompositeKey()
	{
		
	}
	
	public TransactionCompositeKey(String journalNumber, double txnAmount)
	{
		this.journalNumber = journalNumber;
		this.txnAmount = txnAmount;
	}

	public String getJournalNumber() {
		return journalNumber;
	}

	public void setJournalNumber(String journalNumber) {
		this.journalNumber = journalNumber;
	}

	public double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((journalNumber == null) ? 0 : journalNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(txnAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		TransactionCompositeKey other = (TransactionCompositeKey) obj;
		if (journalNumber == null) {
			if (other.journalNumber != null)
				return false;
		} else if (!journalNumber.equals(other.journalNumber))
			return false;
		if (Double.doubleToLongBits(txnAmount) != Double
				.doubleToLongBits(other.txnAmount))
			return false;
		return true;
	}
	
	
}
