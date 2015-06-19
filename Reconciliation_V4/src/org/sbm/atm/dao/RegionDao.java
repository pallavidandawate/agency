package org.sbm.atm.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.sbm.atm.dto.RegionDto;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;
import org.sbm.atm.model.Branch;
import org.sbm.atm.model.Region;
import org.springframework.beans.factory.annotation.Autowired;

public class RegionDao {
	
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 public List<RegionDto> getRegionData(String regionName, Date cutOfDate)
	 {
		 List<RegionDto> regionDtoList = new ArrayList<RegionDto>();
		 Calendar cutOfCalendar = Calendar.getInstance();
		 cutOfCalendar.setTime(cutOfDate);
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 
		 /*Query query = session.getNamedQuery("AgencyBalance.byRegionCode");
		 List<Double> sum = query.list();
		 Iterator<Double> itr = sum.iterator();
		 if(itr.hasNext())
		 {
			 System.out.println(itr.next());
		 }*/
		 
		 Region region = (Region) session.get(Region.class, regionName);
		 List<Branch> branchList = region.getBranch();
		 
		 RegionDto regionDto=null;
		 
		 for(Branch branch : branchList)
		 {
			 regionDto = new RegionDto();
			 regionDto.setBranchCode(branch.getBranchCode());
			 regionDto.setBranchName(branch.getBranchName());
			 
			 //Getting all agency transaction
			 List<BGL_3197_3199> bgl_3197_3199_list = branch.getBgl_3197_3199();
			 if(bgl_3197_3199_list.size() == 0)
				 continue;
			 double bgl_3197_3199_balance = 0.0;
			 for(BGL_3197_3199 bgl_3197_3199 : bgl_3197_3199_list)
			 {
				 List<BGL_3197_3199_TRANSACTION> bgl_3197_3199_txn_List =  bgl_3197_3199.getBgl_3197_3199_transaction();
				 for(BGL_3197_3199_TRANSACTION bgl_3197_3199_txn : bgl_3197_3199_txn_List)
				 {
					Calendar txnDate = Calendar.getInstance();
					txnDate.setTime(bgl_3197_3199_txn.getTxnDate());
					 if(txnDate.before(cutOfCalendar))
					{
						 bgl_3197_3199_balance = bgl_3197_3199_balance + bgl_3197_3199_txn.getTxnKey().getTxnAmount();
					}
					
				 }
			 }
			 regionDto.setAgencyAccountBalance(bgl_3197_3199_balance);
			 regionDto.setBgl3198AccountBalance(-1 * bgl_3197_3199_balance );
			 //TO DO fetch 3198 data
			 regionDtoList.add(regionDto);
			 
		 }
		 
		  
		  session.getTransaction().commit();
		  session.close();
		  return regionDtoList;
	 }
	 public List<String> getRegionNames()
	 {
		 Session session = sessionFactory.openSession();
				 
		 session.beginTransaction();  
		 List<String> regionNames = new ArrayList<String>();
		 
		Criteria criteria = session.createCriteria(Region.class);
		criteria.setProjection(Projections.property("regionName"));
		
		 regionNames = (ArrayList<String>) criteria.list();
		 
		
		
		 
		 session.getTransaction().commit();
		 session.close();
		 
		 Collections.sort(regionNames.subList(0, regionNames.size()));
		
		 return regionNames;
		 
	 }
	 

}
