package org.sbm.atm.dao;


public class DataClass {
	
	String  txnDate;
	String bglAccountNumber;
	double totalDebitInIntermediateAccount;
	double totalCreditInIntermediateAccount;
	double totalAdminIncrease;
	double totalAdminDecrease;
	double net3198;
	double dayDefference; // totalDebitInIntermediateAccount + totalCreditInIntermediateAccount
	double commulativeDifference; // commulativeDifference += dayDefference
	double days3198;
	double commulative3198;

	public double getDays3198() {
		return days3198;
	}
	public void setDays3198(double days3198) {
		this.days3198 = days3198;
	}
	public double getCommulative3198() {
		return commulative3198;
	}
	public void setCommulative3198(double commulative3198) {
		this.commulative3198 = commulative3198;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getBglAccountNumber() {
		return bglAccountNumber;
	}
	public void setBglAccountNumber(String bglAccountNumber) {
		this.bglAccountNumber = bglAccountNumber;
	}
	public double getTotalDebitInIntermediateAccount() {
		return totalDebitInIntermediateAccount;
	}
	public void setTotalDebitInIntermediateAccount(
			double totalDebitInIntermediateAccount) {
		this.totalDebitInIntermediateAccount = totalDebitInIntermediateAccount;
	}
	public double getTotalCreditInIntermediateAccount() {
		return totalCreditInIntermediateAccount;
	}
	public void setTotalCreditInIntermediateAccount(
			double totalCreditInIntermediateAccount) {
		this.totalCreditInIntermediateAccount = totalCreditInIntermediateAccount;
	}
	public double getTotalAdminIncrease() {
		return totalAdminIncrease;
	}
	public void setTotalAdminIncrease(double totalAdminIncrease) {
		this.totalAdminIncrease = totalAdminIncrease;
	}
	public double getTotalAdminDecrease() {
		return totalAdminDecrease;
	}
	public void setTotalAdminDecrease(double totalAdminDecrease) {
		this.totalAdminDecrease = totalAdminDecrease;
	}
	
	
	public double getNet3198() {
		return net3198;
	}
	public void setNet3198(double net3198) {
		this.net3198 = net3198;
	}
	public double getDayDefference() {
		return dayDefference;
	}
	public void setDayDefference(double dayDefference) {
		this.dayDefference = dayDefference;
	}
	public double getCommulativeDifference() {
		return commulativeDifference;
	}
	public void setCommulativeDifference(double commulativeDifference) {
		this.commulativeDifference = commulativeDifference;
	}

	
	
	
	

}
