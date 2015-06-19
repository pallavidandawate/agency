package org.sbm.atm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sbm.atm.model.ATM;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3198_TRANSACTION;
import org.springframework.beans.factory.annotation.Autowired;

public class BGL_3198_TRANSACTION_DAO {
	
	@Autowired  
	 SessionFactory sessionFactory;  
	 
	 @Autowired
	 ATM_DAO atmDao;
	
	 public double getTotalBgl3198CreditByBgl_3197_3199_AndDate(BGL_3197_3199 bgl_3197_3199, Date date)
	 {
		 
		 
		 List<ATM> atmList = atmDao.getATMByBgl_3197_3199(bgl_3197_3199);
		 double grandTotalCreditInBgl3198 =0.0;
		 
		 for(ATM atm : atmList){
			 
			 grandTotalCreditInBgl3198 = grandTotalCreditInBgl3198 + getTotalCreditFromBbgl3198TxnList(getBgl3198TransactionListByAtmAndDate(atm, date));
		 }
		 return grandTotalCreditInBgl3198;
	
	 }
	 
	 public double getTotalBgl3198DebitByBgl_3197_3199_AndDate(BGL_3197_3199 bgl_3197_3199, Date date)
	 {
		 
		 
		 List<ATM> atmList = atmDao.getATMByBgl_3197_3199(bgl_3197_3199);
		 double grandTotalAdminDecrease =0.0;
		 
		 for(ATM atm : atmList){
			 
			 grandTotalAdminDecrease = grandTotalAdminDecrease + getTotalDebitFromBbgl3198TxnList(getBgl3198TransactionListByAtmAndDate(atm, date));
		 }
		 return grandTotalAdminDecrease;
	
	 }
	 
	 
	 public double getNetAmountBgl3198_ByBgl_3197_3199_AndDate(BGL_3197_3199 bgl_3197_3199, Date date)
	 {
		 
		 
		 List<ATM> atmList = atmDao.getATMByBgl_3197_3199(bgl_3197_3199);
		 double grandTotalAdminDecrease =0.0;
		 
		 for(ATM atm : atmList){
			 
			 grandTotalAdminDecrease = grandTotalAdminDecrease + getNetAmountFromBbgl3198TxnList(getBgl3198TransactionListByAtmAndDate(atm, date));
		 }
		 return grandTotalAdminDecrease;
	
	 }
	 
	 
	
	 
	 public List<BGL_3198_TRANSACTION> getBgl3198TransactionListByAtmAndDate(ATM atm, Date date)
	 {
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 Criteria criteria = session.createCriteria(BGL_3198_TRANSACTION.class);
		 criteria.add(Restrictions.eq("txnDate", date));
		 criteria.add(Restrictions.eqOrIsNull("bglAccountNumber", atm.getBgl_3198()));
		 List<BGL_3198_TRANSACTION> bgl3198TransactionList = (List<BGL_3198_TRANSACTION>)criteria.list();
	  
		 session.getTransaction().commit();
		 session.close();
		 return bgl3198TransactionList;
	 }
	 
	 public double getTotalCreditFromBbgl3198TxnList(List<BGL_3198_TRANSACTION> bgl_3198_transaction_list)
	 {
		 double totalBgl3198Increase = 0.0;
		 double amount = 0.0;
		 for(BGL_3198_TRANSACTION bgl_3198_txn : bgl_3198_transaction_list){
			 amount = bgl_3198_txn.getTxnKey().getTxnAmount();
			 if(amount>0){
				 totalBgl3198Increase = totalBgl3198Increase + amount;
			 }
		 }
		 return totalBgl3198Increase;
	 }
	 
	 public double getTotalDebitFromBbgl3198TxnList(List<BGL_3198_TRANSACTION> bgl_3198_transaction_list)
	 {
		 double totalBgl3198Decrease = 0.0;
		 double amount = 0.0;
		 for(BGL_3198_TRANSACTION bgl_3198_txn : bgl_3198_transaction_list){
			 amount = bgl_3198_txn.getTxnKey().getTxnAmount();
			 if(amount<0){
				 totalBgl3198Decrease = totalBgl3198Decrease + amount;
			 }
		 }
		 return totalBgl3198Decrease;
	 }
	 
	 
	 public double getNetAmountFromBbgl3198TxnList(List<BGL_3198_TRANSACTION> bgl_3198_transaction_list)
	 {
		 double netAmount = 0.0;
		 double amount = 0.0;
		 for(BGL_3198_TRANSACTION bgl_3198_txn : bgl_3198_transaction_list){
			 amount = bgl_3198_txn.getTxnKey().getTxnAmount();
			 netAmount = netAmount + amount;
			
		 }
		 return netAmount;
	 }
	 
	 public List<ATM> getBGL_3198_ListByBgl3197_3199(String bglAccountNumber)
	 {
		 List<ATM> bgl3198List = new ArrayList<ATM>();
		 
		
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction(); 
		 BGL_3197_3199 bgl3197_3199 = (BGL_3197_3199)session.get(BGL_3197_3199.class, bglAccountNumber);
		 Criteria criteria = session.createCriteria(ATM.class);
		 criteria.add(Restrictions.eqOrIsNull("bgl_3197_3199_ACNO", bgl3197_3199));
		 bgl3198List = (List<ATM>)criteria.list();
	  
		 session.getTransaction().commit();
		 session.close();
		 		 				 
		 return bgl3198List;
	 }
	 
	 

}
