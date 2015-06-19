package org.sbm.atm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;

public class BGL_3197_3199_DAO {
	
	  
	  
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 
	 public List<BGL_3197_3199> getBglAccountNumberByBranchCode(Branch branch)
	 {
		  Session session = sessionFactory.openSession();  
		  session.beginTransaction();  
		  Criteria criteria = session.createCriteria(BGL_3197_3199.class);
		  criteria.add(Restrictions.eq("branchCode", branch));
		  List<BGL_3197_3199> bgl_3197_3199_list = (List<BGL_3197_3199>)criteria.list();
		  
		  session.getTransaction().commit();
		  session.close();
		  
		  
		  return bgl_3197_3199_list ;
	 }
	  
	 
	
	  


}
