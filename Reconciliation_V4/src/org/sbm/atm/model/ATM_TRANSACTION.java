package org.sbm.atm.model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table
public class ATM_TRANSACTION {
	
	
	
	@Column
	private double amount;
	
	@EmbeddedId
	private AdminTxnCompositeKey adminKey;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public AdminTxnCompositeKey getAdminKey() {
		return adminKey;
	}

	public void setAdminKey(AdminTxnCompositeKey adminKey) {
		this.adminKey = adminKey;
	}
	
	
	


	

}
