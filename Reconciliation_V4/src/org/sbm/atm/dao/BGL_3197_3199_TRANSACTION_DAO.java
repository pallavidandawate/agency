package org.sbm.atm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;

public class BGL_3197_3199_TRANSACTION_DAO {

	 @Autowired  
	 SessionFactory sessionFactory; 
	 
	 public List<BGL_3197_3199_TRANSACTION> getBgl_3197_3199_transactionList(BGL_3197_3199 bgl_3197_3199) 
	 {
		 Session session = sessionFactory.openSession();
		 session.beginTransaction();
		 Criteria criteria = session.createCriteria(BGL_3197_3199_TRANSACTION.class);
		 criteria.add(Restrictions.eq("bglAccountNumber", bgl_3197_3199));
		 List<BGL_3197_3199_TRANSACTION> bgl_3197_3199_transactionList = (List<BGL_3197_3199_TRANSACTION>)criteria.list();
		 session.getTransaction().commit();
		 session.close();
		  
		  
		  return bgl_3197_3199_transactionList ;
		 
		
			 
	 }
	 
	 public List<BGL_3197_3199_TRANSACTION> getBgl_3197_3199_transactionList(BGL_3197_3199 bgl_3197_3199, Date date) 
	 {
		 Session session = sessionFactory.openSession();
		 session.beginTransaction();
		 Criteria criteria = session.createCriteria(BGL_3197_3199_TRANSACTION.class);
		 criteria.add(Restrictions.eq("bglAccountNumber", bgl_3197_3199));
		 criteria.add(Restrictions.eq("txnDate", date));
		 List<BGL_3197_3199_TRANSACTION> bgl_3197_3199_transactionList = (List<BGL_3197_3199_TRANSACTION>)criteria.list();
		 session.getTransaction().commit();
		 session.close();
		  
		  
		  return bgl_3197_3199_transactionList ;
		 
		
			 
	 }
}
