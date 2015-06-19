package org.sbm.atm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table
public class BGL_3198 {
	
	@Id
	@Column
	private String bglAccountNumber;
	
	@ManyToOne
	@JoinColumn(name="Branch_Code")
	private Branch branchCode;
	
	@OneToMany(mappedBy="bglAccountNumber")
	private List<BGL_3198_TRANSACTION> bgl_3198_transaction = new ArrayList<BGL_3198_TRANSACTION>();
	
	
	@OneToOne(mappedBy="bgl_3198")
	private ATM atm;

	public ATM getAtm() {
		return atm;
	}

	public void setAtm(ATM atm) {
		this.atm = atm;
	}

	public String getBglAccountNumber() {
		return bglAccountNumber;
	}

	public void setBglAccountNumber(String bglAccountNumber) {
		this.bglAccountNumber = bglAccountNumber;
	}

	




	public List<BGL_3198_TRANSACTION> getBgl_3198_transaction() {
		return bgl_3198_transaction;
	}

	public void setBgl_3198_transaction(
			List<BGL_3198_TRANSACTION> bgl_3198_transaction) {
		this.bgl_3198_transaction = bgl_3198_transaction;
	}

	public Branch getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Branch branchCode) {
		this.branchCode = branchCode;
	}
	
	
	
	

}
