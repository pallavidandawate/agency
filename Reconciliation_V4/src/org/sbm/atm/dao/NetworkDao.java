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
import org.sbm.atm.dto.BankDto;
import org.sbm.atm.dto.NetworkDto;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;
import org.sbm.atm.model.Branch;
import org.sbm.atm.model.Network;
import org.sbm.atm.model.Region;
import org.sbm.atm.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;

public class NetworkDao {
	
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 public List<NetworkDto> getNetworkData(String networkName, Date cutOfDate)
	 {
		 List<NetworkDto> networkDtoList = new ArrayList<NetworkDto>();
		 Calendar cutOfCalendar = Calendar.getInstance();
		 cutOfCalendar.setTime(cutOfDate);
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 
			 
		Network network = (Network) session.get(Network.class, networkName);
		
		List<Zone> zoneList = network.getZone();
		NetworkDto networkDto = null;
		
		for(Zone zone : zoneList)
		{
			 List<Region> regionList = zone.getRegion();
			
			 for(Region region : regionList)
			 {
				 
				 
				 List<Branch> branchList = region.getBranch();
				 for(Branch branch : branchList)
				 {
					 
					 networkDto = new NetworkDto();
					 networkDto.setNetworkName(networkName);
					 networkDto.setRegionName(region.getRegionName());
					 networkDto.setBranchCode(branch.getBranchCode());
					 networkDto.setBranchName(branch.getBranchName());
					 
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
					 networkDto.setAgencyAccountBalance(bgl_3197_3199_balance);
					 networkDto.setBgl3198AccountBalance(-1 * bgl_3197_3199_balance );
					 //TO DO fetch 3198 data
					 networkDtoList.add(networkDto);
					 
				 }
				 
			 }
			
		}
		 
		session.getTransaction().commit();
		session.close();
		return networkDtoList;
	 }
	 public List<String> getNetworkNames()
	 {
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 List<String> networkNames = new ArrayList<String>();
		 
		Criteria criteria = session.createCriteria(Network.class);
		criteria.setProjection(Projections.property("networkName"));
		
		networkNames = (ArrayList<String>) criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		Collections.sort(networkNames.subList(0, networkNames.size()));
		 return networkNames;
		 
	 }
	 
	 public List<BankDto> getBankData(Date cutOfDate)
	 {
		 List<BankDto> bankDtoList = new ArrayList<BankDto>();
		 Calendar cutOfCalendar = Calendar.getInstance();
		 cutOfCalendar.setTime(cutOfDate);
		 
		 Session session = sessionFactory.openSession();  
		 session.beginTransaction();  
		 
			 
		List<Network> networkList = (List<Network>)session.createQuery("from Network").list();
		BankDto bankDto = null;
		
		
		for(Network network : networkList)
		{
			List<Zone> zoneList = network.getZone();
			
			
			for(Zone zone : zoneList)
			{
				 List<Region> regionList = zone.getRegion();
				
				 for(Region region : regionList)
				 {
					 
					 
					 List<Branch> branchList = region.getBranch();
					 for(Branch branch : branchList)
					 {
						 
						 
						 
						 //Getting all agency transaction
						 List<BGL_3197_3199> bgl_3197_3199_list = branch.getBgl_3197_3199();
						 if(bgl_3197_3199_list.size() == 0)
							 continue;
						 else
						 {
							 bankDto = new BankDto();
							 bankDto.setNetworkName(network.getNetworkName());
							 bankDto.setZoneName(zone.getZoneName());
							 bankDto.setRegionName(region.getRegionName());
							 bankDto.setBranchCode(branch.getBranchCode());
							 bankDto.setBranchName(branch.getBranchName());
						 }
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
						 bankDto.setAgencyAccountBalance(bgl_3197_3199_balance);
						 bankDto.setBgl3198AccountBalance(-1 * bgl_3197_3199_balance );
						 //TO DO fetch 3198 data
						 bankDtoList.add(bankDto);
						 
					 }
					 
				 }
				
			}
			
		}
		
	
		 
		session.getTransaction().commit();
		session.close();
		return bankDtoList;
	 }
	 

}
