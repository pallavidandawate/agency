package org.sbm.atm.dto;

public class BankDto {
	
	String networkName;
	String zoneName;
	String regionName;
	String branchCode;
	String branchName;
	double agencyAccountBalance;
	double bgl3198AccountBalance;
	
	
	
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
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
	public double getAgencyAccountBalance() {
		return agencyAccountBalance;
	}
	public void setAgencyAccountBalance(double agencyAccountBalance) {
		this.agencyAccountBalance = agencyAccountBalance;
	}
	public double getBgl3198AccountBalance() {
		return bgl3198AccountBalance;
	}
	public void setBgl3198AccountBalance(double bgl3198AccountBalance) {
		this.bgl3198AccountBalance = bgl3198AccountBalance;
	}
	
	

}
