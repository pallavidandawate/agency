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
import org.sbm.atm.dto.ZoneDto;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;
import org.sbm.atm.model.Branch;
import org.sbm.atm.model.Region;
import org.sbm.atm.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;

public class ZoneDao {
	
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 public List<ZoneDto> getZoneData(String zoneName, Date cutOfDate)
	 {
		 List<ZoneDto> zoneDtoList = new ArrayList<ZoneDto>();
		 Calendar cutOfCalendar = Calendar.getInstance();
		 cutOfCalendar.setTime(cutOfDate);
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 
			 
		 Zone zone = (Zone) session.get(Zone.class, zoneName);
		 
		 List<Region> regionList = zone.getRegion();
		 ZoneDto zoneDto=null;
		 for(Region region : regionList)
		 {
			 
			 
			 List<Branch> branchList = region.getBranch();
			 for(Branch branch : branchList)
			 {
				 
				 zoneDto = new ZoneDto();
				 zoneDto.setRegionName(region.getRegionName());
				 zoneDto.setBranchCode(branch.getBranchCode());
				 zoneDto.setBranchName(branch.getBranchName());
				 
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
				 zoneDto.setAgencyAccountBalance(bgl_3197_3199_balance);
				 zoneDto.setBgl3198AccountBalance(-1 * bgl_3197_3199_balance );
				 //TO DO fetch 3198 data
				 zoneDtoList.add(zoneDto);
				
				 
			 }
			 
			
			 
		 }
		 
		
		 
		  
		  session.getTransaction().commit();
		  session.close();
		  return zoneDtoList;
	 }
	 public List<String> getZoneNames()
	 {
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 List<String> zoneNames = new ArrayList<String>();
		 
		Criteria criteria = session.createCriteria(Zone.class);
		criteria.setProjection(Projections.property("zoneName"));
	
		zoneNames = (ArrayList<String>) criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		Collections.sort(zoneNames.subList(0, zoneNames.size()));
		
		 return zoneNames;
		 
	 }
	 

}
