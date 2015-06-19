package org.sbm.atm.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sbm.atm.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;

public class BRANCH_DAO {
	
	@Autowired  
	 SessionFactory sessionFactory;  
	
	public Branch getBranchByBranchCode(String branchCode)
	{
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 Branch branch = (Branch) session.get(Branch.class, branchCode);
		 session.getTransaction().commit();
		 session.close();
		return branch;
		
	}

}
