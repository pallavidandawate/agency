package org.sbm.atm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sbm.atm.model.ATM;
import org.sbm.atm.model.BGL_3197_3199;
import org.springframework.beans.factory.annotation.Autowired;

public class ATM_DAO {
	
	  
	  
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 
	 public List<ATM> getATMByBgl_3197_3199(BGL_3197_3199 bgl_3197_3198)
	 {
		  Session session = sessionFactory.openSession();  
		  session.beginTransaction();  
		  Criteria criteria = session.createCriteria(ATM.class);
		  criteria.add(Restrictions.eq("bgl_3197_3199_ACNO", bgl_3197_3198));
		  List<ATM> atmList = (List<ATM>)criteria.list();
		  
		  session.getTransaction().commit();
		  session.close();
		  
		  
		  return atmList ;
	 }
	 

	 

	  
	  
	 
	
	  


}
