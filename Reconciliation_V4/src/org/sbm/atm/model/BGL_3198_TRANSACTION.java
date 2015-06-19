package org.sbm.atm.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class BGL_3198_TRANSACTION {
	
	
	
	@ManyToOne
	@JoinColumn(name="BGLACCOUNTNUMBER")
	private BGL_3198 bglAccountNumber;
	

	
	
	public BGL_3198 getBglAccountNumber() {
		return bglAccountNumber;
	}


	public void setBglAccountNumber(BGL_3198 bglAccountNumber) {
		this.bglAccountNumber = bglAccountNumber;
	}





	@Column
	@Temporal(TemporalType.DATE)
	private Date txnDate;
	
	
	@EmbeddedId
	private TransactionCompositeKey txnKey;
	
	
	public TransactionCompositeKey getTxnKey() {
		return txnKey;
	}


	public void setTxnKey(TransactionCompositeKey txnKey) {
		this.txnKey = txnKey;
	}





	@Column
	private String txmTime;
	
	 
	
	@Column
	 private String atmId;
	 
	


	
	public Date getTxnDate() {
		return txnDate;
	}


	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}


	public String getTxmTime() {
		return txmTime;
	}


	public void setTxmTime(String txmTime) {
		this.txmTime = txmTime;
	}


	public String getAtmId() {
		return atmId;
	}


	public void setAtmId(String atmId) {
		this.atmId = atmId;
	}




		

}
