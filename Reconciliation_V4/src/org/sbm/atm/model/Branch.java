package org.sbm.atm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table
public class Branch {
	
	@Id
	@Column
	private String branchCode;
	
	@Column
	private String branchName;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date cashOutSourcingStartDate;
	
	

	@ManyToOne
	@JoinColumn(name="regionName")
	private Region regionName;
	
	

	@OneToMany(mappedBy="branchCode")
	private List<ATM>  atm = new ArrayList<ATM>();
	
	
	@OneToMany(mappedBy="branchCode")
	private List<BGL_3198>  bgl_3198 = new ArrayList<BGL_3198>();
	
	
	@OneToMany(mappedBy="branchCode")
	private List<BGL_3197_3199>  bgl_3197_3199 = new ArrayList<BGL_3197_3199>();


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public Date getCashOutSourcingStartDate() {
		return cashOutSourcingStartDate;
	}


	public void setCashOutSourcingStartDate(Date cashOutSourcingStartDate) {
		this.cashOutSourcingStartDate = cashOutSourcingStartDate;
	}


	public Region getRegionName() {
		return regionName;
	}


	public void setRegionName(Region regionName) {
		this.regionName = regionName;
	}


	public List<ATM> getAtm() {
		return atm;
	}


	public void setAtm(List<ATM> atm) {
		this.atm = atm;
	}


	public List<BGL_3198> getBgl_3198() {
		return bgl_3198;
	}


	public void setBgl_3198(List<BGL_3198> bgl_3198) {
		this.bgl_3198 = bgl_3198;
	}


	public List<BGL_3197_3199> getBgl_3197_3199() {
		return bgl_3197_3199;
	}


	public void setBgl_3197_3199(List<BGL_3197_3199> bgl_3197_3199) {
		this.bgl_3197_3199 = bgl_3197_3199;
	}

	
	
	

	
	

}
