package org.sbm.atm.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table
public class BGL_3197_3199 {
	
	@Id
	@Column
	String bglAccountNumber;
	
	@ManyToOne
	@JoinColumn(name="Branch_Code")
	private Branch branchCode;
	
	@OneToMany(mappedBy="bglAccountNumber")
	private List<BGL_3197_3199_TRANSACTION>  bgl_3197_3199_transaction = new ArrayList<BGL_3197_3199_TRANSACTION>();
	
	@OneToMany(mappedBy="bgl_3197_3199_ACNO")
	private List<ATM>  atm = new ArrayList<ATM>();
	
	
	
	
	
	public List<BGL_3197_3199_TRANSACTION> getBgl_3197_3199_transaction() {
		return bgl_3197_3199_transaction;
	}

	public void setBgl_3197_3199_transaction(
			List<BGL_3197_3199_TRANSACTION> bgl_3197_3199_transaction) {
		this.bgl_3197_3199_transaction = bgl_3197_3199_transaction;
	}

	public String getBglAccountNumber() {
		return bglAccountNumber;
	}

	public void setBglAccountNumber(String bglAccountNumber) {
		this.bglAccountNumber = bglAccountNumber;
	}

	

	public Branch getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Branch branchCode) {
		this.branchCode = branchCode;
	}
	
	


}
