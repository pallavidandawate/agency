package org.sbm.atm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sbm.atm.model.ATM;
import org.sbm.atm.model.ATM_TRANSACTION;
import org.sbm.atm.model.BGL_3197_3199;
import org.springframework.beans.factory.annotation.Autowired;

public class ATM_TRANSACTION_DAO {
	
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 @Autowired
	 ATM_DAO atmDao;
	
	 public double getTotalAdminIncreaseByBgl_3197_3199_AndDate(BGL_3197_3199 bgl_3197_3199, Date date)
	 {
		 
		 
		 List<ATM> atmList = atmDao.getATMByBgl_3197_3199(bgl_3197_3199);
		 double grandTotalAdminIncrease =0.0;
		 
		 for(ATM atm : atmList){
			 
			 grandTotalAdminIncrease = grandTotalAdminIncrease + getTotalAdminIncreaseFromAtmTxnList(getAtmTransactionListByAtmIdAndDate(atm, date));
		 }
		 return grandTotalAdminIncrease;
	
	 }
	 
	 public double getTotalAdminDecreaseByBgl_3197_3199_AndDate(BGL_3197_3199 bgl_3197_3199, Date date)
	 {
		 
		 
		 List<ATM> atmList = atmDao.getATMByBgl_3197_3199(bgl_3197_3199);
		 double grandTotalAdminDecrease =0.0;
		 
		 for(ATM atm : atmList){
			 
			 grandTotalAdminDecrease = grandTotalAdminDecrease + getTotalAdminDecreaseFromAtmTxnList(getAtmTransactionListByAtmIdAndDate(atm, date));
		 }
		 return grandTotalAdminDecrease;
	
	 }
	 
	 
	
	 
	 public List<ATM_TRANSACTION> getAtmTransactionListByAtmIdAndDate(ATM atm, Date date)
	 {
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 Criteria criteria = session.createCriteria(ATM_TRANSACTION.class);
		 criteria.add(Restrictions.eq("txnDate", date));
		 criteria.add(Restrictions.eqOrIsNull("atmId", atm));
		 List<ATM_TRANSACTION> atmTransactionList = (List<ATM_TRANSACTION>)criteria.list();
	  
		 session.getTransaction().commit();
		 session.close();
		 return atmTransactionList;
	 }
	 
	 public double getTotalAdminIncreaseFromAtmTxnList(List<ATM_TRANSACTION> atmTxnLsit)
	 {
		 double totalAdminIncrease = 0.0;
		 double amount = 0.0;
		 for(ATM_TRANSACTION atmTxn : atmTxnLsit){
			 amount = atmTxn.getAmount();
			 if(amount>0){
				 totalAdminIncrease = totalAdminIncrease + amount;
			 }
		 }
		 return totalAdminIncrease;
	 }
	 
	 public double getTotalAdminDecreaseFromAtmTxnList(List<ATM_TRANSACTION> atmTxnLsit)
	 {
		 double totalAdminDecreasae = 0.0;
		 double amount = 0.0;
		 for(ATM_TRANSACTION atmTxn : atmTxnLsit){
			 amount = atmTxn.getAmount();
			 if(amount<0){
				 totalAdminDecreasae = totalAdminDecreasae + amount;
			 }
		 }
		 return totalAdminDecreasae;
	 }

}
