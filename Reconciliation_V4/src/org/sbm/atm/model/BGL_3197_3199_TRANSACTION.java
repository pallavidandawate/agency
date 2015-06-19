package org.sbm.atm.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedNativeQuery(name = "AgencyBalance.byRegionCode", query = "select  SUM(AMOUNT)  from BGL_3197_3199_TRANSACTION where BGLACCOUNTNUMBER in (select UNIQUE BGL_3197_3199_ACNO from ATM where BRANCH_CODE in (select BRANCHCODE from Branch where REGIONCODE = '40729'))")
public class BGL_3197_3199_TRANSACTION {
	
	
	@ManyToOne
	@JoinColumn(name="BGLACCOUNTNUMBER")
	private BGL_3197_3199 bglAccountNumber;





	public BGL_3197_3199 getBglAccountNumber() {
		return bglAccountNumber;
	}


	public void setBglAccountNumber(BGL_3197_3199 bglAccountNumber) {
		this.bglAccountNumber = bglAccountNumber;
	}
	
	@EmbeddedId
	private TransactionCompositeKey txnKey;


	public TransactionCompositeKey getTxnKey() {
		return txnKey;
	}


	public void setTxnKey(TransactionCompositeKey txnKey) {
		this.txnKey = txnKey;
	}

	@Column
	@Temporal(TemporalType.DATE)
	private Date txnDate;
	
	
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
