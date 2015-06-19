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
public class ATM {
	
	@Id
	@Column
	String atmId;
	
	@Column
	String vendorName;
	
	@ManyToOne
	@JoinColumn(name="Branch_Code")
	private Branch branchCode;
	
	@ManyToOne
	@JoinColumn(name="BGL_3197_3199_ACNO")
	private BGL_3197_3199 bgl_3197_3199_ACNO;
	
	@OneToOne
	@JoinColumn(name="BGL_3198_ACNO")
	private BGL_3198 bgl_3198;
	
	
	
	

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public BGL_3197_3199 getBgl_3197_3199_ACNO() {
		return bgl_3197_3199_ACNO;
	}

	public void setBgl_3197_3199_ACNO(BGL_3197_3199 bgl_3197_3199_ACNO) {
		this.bgl_3197_3199_ACNO = bgl_3197_3199_ACNO;
	}

	public BGL_3198 getBgl_3198() {
		return bgl_3198;
	}

	public void setBgl_3198(BGL_3198 bgl_3198) {
		this.bgl_3198 = bgl_3198;
	}

	@OneToMany(mappedBy="adminKey.atm")
	private List<ATM_TRANSACTION> atmTransaction = new ArrayList<ATM_TRANSACTION>();
	
	
	

	
	public List<ATM_TRANSACTION> getAtmTransaction() {
		return atmTransaction;
	}

	public void setAtmTransaction(List<ATM_TRANSACTION> atmTransaction) {
		this.atmTransaction = atmTransaction;
	}

	public String getAtmId() {
		return atmId;
	}

	public void setAtmId(String atmId) {
		this.atmId = atmId;
	}

	public Branch getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Branch branchCode) {
		this.branchCode = branchCode;
	}

	
	
	
	

}
