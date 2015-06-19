package org.sbm.atm.dao;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sbm.atm.model.ATM;
import org.sbm.atm.model.ATM_TRANSACTION;
import org.sbm.atm.model.AdminTxnCompositeKey;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;
import org.sbm.atm.model.BGL_3198;
import org.sbm.atm.model.BGL_3198_TRANSACTION;
import org.sbm.atm.model.Branch;
import org.sbm.atm.model.Network;
import org.sbm.atm.model.Region;
import org.sbm.atm.model.TransactionCompositeKey;
import org.sbm.atm.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;

public class FileProcessorDao {
	
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	private Session session;
	 
	
	 
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = "#";
	 
	 public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	


	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	
	public void initialize(){
		setSession(sessionFactory.openSession());
			
		 
	 }

	//trimming all white spaces in string array
	public String[] removeWhiteSpace(String [] stringArray)
	{
		
		String[] column = new String[stringArray.length];
		for (int i = 0; i < stringArray.length; i++)
		{
			column[i] = stringArray[i].trim();
		}
		
		return column;
		
	}
	 

	public void processBGL_3197_3199_transaction(String filePath)
	{
		initialize();
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			
			Branch branch;
			BGL_3197_3199 bgl_3197_3199;
			BGL_3197_3199_TRANSACTION bgl_3197_3199_transaction;
			Date txnDate = null;
			
			
			while ((line = br.readLine()) != null)
			{
				
				
				
				
				session.beginTransaction();
				
				
				// get string array by splitting the line and remove all white spaces
				String[] column = removeWhiteSpace(line.split(cvsSplitBy));
				
			
		
				String branchCode = column[6];
				String bgl_3197_3199_acno = column[1];
				String journalNumber = column[3];
				double amount = Double.valueOf(column[5]);
				TransactionCompositeKey txnKey = new TransactionCompositeKey(journalNumber,amount);
				
				
				//fetching TXN DATE				
				try
				{
					txnDate = new SimpleDateFormat("dd/MM/yyyy").parse(column[2]);
				}
				catch (ParseException e)
				{
					
					e.printStackTrace();
				}
				
				//Saving or Updating Branch Object
				branch = (Branch) session.get(Branch.class, branchCode);
				if(branch == null)
				{
					branch = new Branch();
					branch.setBranchCode(branchCode);
					branch.setCashOutSourcingStartDate(txnDate);
				}
				else
				{
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					
					c1.setTime(txnDate);
					Date dd = branch.getCashOutSourcingStartDate();
					if(dd == null)
					{
						branch.setCashOutSourcingStartDate(txnDate);
					}
					else
					{
						c2.setTime(dd);
						if(c1.before(c2))
						{
							branch.setCashOutSourcingStartDate(txnDate);
							//System.out.println("Lower date found");
						}
					}
					
				
					
				}
				
				
				//Saving or Updating BGL_3197_3199 Object
				bgl_3197_3199 = (BGL_3197_3199) session.get(BGL_3197_3199.class,bgl_3197_3199_acno);
				if(bgl_3197_3199 == null){
					bgl_3197_3199 = new BGL_3197_3199();
					bgl_3197_3199.setBranchCode(branch);
					bgl_3197_3199.setBglAccountNumber(bgl_3197_3199_acno);
							
				}
				else{
					
					bgl_3197_3199.setBranchCode(branch);
					bgl_3197_3199.setBglAccountNumber(bgl_3197_3199_acno);
					
				}
				
				
				//Saving or Updating BGL_3197_3199_TRANSACTION Object
				bgl_3197_3199_transaction = (BGL_3197_3199_TRANSACTION) session.get(BGL_3197_3199_TRANSACTION.class,txnKey);
				if(bgl_3197_3199_transaction == null){
					bgl_3197_3199_transaction = new BGL_3197_3199_TRANSACTION();
					bgl_3197_3199_transaction.setTxnDate(txnDate);
					bgl_3197_3199_transaction.setTxnKey(txnKey);
					bgl_3197_3199_transaction.setBglAccountNumber(bgl_3197_3199);					
							
				}
				else{
					
					bgl_3197_3199_transaction.setTxnDate(txnDate);
					bgl_3197_3199_transaction.setTxnKey(txnKey);
					bgl_3197_3199_transaction.setBglAccountNumber(bgl_3197_3199);								
					
				}
				
				
				//reverse mapping
				bgl_3197_3199.getBgl_3197_3199_transaction().add(bgl_3197_3199_transaction);
				branch.getBgl_3197_3199().add(bgl_3197_3199);
				
				session.saveOrUpdate(branch);
				session.saveOrUpdate(bgl_3197_3199);
				session.saveOrUpdate(bgl_3197_3199_transaction);
				session.flush();
				session.clear();
				session.getTransaction().commit();
				
				
			}
			session.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		}
	
	public void processATMRelationship(String filePath)
	{
		initialize();
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			Branch branch;
			BGL_3198 bgl_3198;
			BGL_3197_3199 bgl_3197_3199;
			ATM atm;
			
			String bgl_3197_3199_accno;
			String branchCode;
			String bgl_3198_accno;
			String craVendor;
			String atmId;
			
			while ((line = br.readLine()) != null)
			{
				
				session.beginTransaction();
							
				// get string array by splitting the line and remove all white spaces
				String[] column = removeWhiteSpace(line.split(cvsSplitBy));
				
			
		
				bgl_3197_3199_accno = column[0];
				craVendor = column[1];
				branchCode = column[2];
				atmId = column[3];
				bgl_3198_accno = column[4];
				
				//System.out.println(bgl_3197_3199_accno + " " + craVendor+ "  " + branchCode + "  "+ atmId + "   " + bgl_3198_accno + "  " );
				
							
				
				branch = (Branch) session.get(Branch.class, branchCode);
				if(branch == null)
				{
					branch = new Branch();
					branch.setBranchCode(branchCode);
					
				}
				
				bgl_3198 = (BGL_3198) session.get(BGL_3198.class, bgl_3198_accno);
				if(bgl_3198 == null){
					bgl_3198 = new BGL_3198();
					bgl_3198.setBglAccountNumber(bgl_3198_accno);
					bgl_3198.setBranchCode(branch);
					
				}
				
				bgl_3197_3199 = (BGL_3197_3199) session.get(BGL_3197_3199.class, bgl_3197_3199_accno);
				if(bgl_3197_3199 == null){
					//System.out.println("bgl 3197 3198 record not found");
					bgl_3197_3199 = new BGL_3197_3199();
					bgl_3197_3199.setBglAccountNumber(bgl_3197_3199_accno);
					bgl_3197_3199.setBranchCode(branch);
				}
				
				atm = (ATM) session.get(ATM.class, atmId);
				if(atm == null){
					atm = new ATM();
					atm.setAtmId(atmId);
					atm.setBranchCode(branch);
					atm.setBgl_3197_3199_ACNO(bgl_3197_3199);
					atm.setVendorName(craVendor);
					atm.setBgl_3198(bgl_3198);								
				}
				else
				{
					atm.setAtmId(atmId);
					atm.setBranchCode(branch);
					atm.setBgl_3197_3199_ACNO(bgl_3197_3199);
					atm.setVendorName(craVendor);
					atm.setBgl_3198(bgl_3198);	
				}
		
			
				
				session.saveOrUpdate(branch);
				session.saveOrUpdate(bgl_3197_3199);
				session.saveOrUpdate(bgl_3198);
				session.saveOrUpdate(atm);
				session.flush();
				session.clear();
				session.getTransaction().commit();
				
				
			}
			session.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		}
	
	public void processRecordTypeAdminTxn(String filePath)
	{
		initialize();
        try 
        {
        	br = new BufferedReader(new FileReader(filePath));
        	
        	Branch branch;
			ATM atm;
			ATM_TRANSACTION atmTransaction;
			
			Date txnDate=null;
			double amount;
			String time;
			String atmId;
			String branchCode;
			
			while ((line = br.readLine()) != null)
			{
				
				
				
				
				session.beginTransaction();
				
				// use # as separator
				String[] column = removeWhiteSpace(line.split(cvsSplitBy));
				
				
				
				
				try	{ txnDate = new SimpleDateFormat("dd/MM/yyyy").parse(column[0]);}
				catch (ParseException e){e.printStackTrace();}
				atmId = column[3];
				time = column[6];
				amount = Double.valueOf(column[21]);
				branchCode = column[22];
				
				
				//System.out.println(txnDate + "  " + atmId + "  " + journalNumber + "  " + amount + "  " + branchCode);
				
				branch = (Branch) session.get(Branch.class, branchCode);
				if(branch == null){
					
					branch = new Branch();
					branch.setBranchCode(branchCode);
				}
				
				atm = (ATM) session.get(ATM.class, atmId);
				if(atm == null){
					
					atm = new ATM();
					atm.setAtmId(atmId);
					
				}
				AdminTxnCompositeKey adminKey = new AdminTxnCompositeKey(time, txnDate, atm);
				
				atmTransaction = (ATM_TRANSACTION) session.get(ATM_TRANSACTION.class, adminKey);
				if(atmTransaction == null){
					atmTransaction = new ATM_TRANSACTION();
					atmTransaction.getAdminKey().setAtm(atm);
					atmTransaction.setAmount(amount);
					atmTransaction.getAdminKey().setTime(time);
					atmTransaction.getAdminKey().setDate(txnDate);
					
				}
				
			
				
				branch.getAtm().add(atm);
				atm.getAtmTransaction().add(atmTransaction);
				
				
				session.saveOrUpdate(branch);
				session.saveOrUpdate(atm);
				session.saveOrUpdate(atmTransaction);
				session.flush();
				session.clear();
				session.getTransaction().commit();
			
				
			}
			session.close();
     } 
     catch (FileNotFoundException e)
     {
		e.printStackTrace();
     }
	catch (IOException e)
    {
		e.printStackTrace();
	}
	finally
	{
		if (br != null)
		{
			try
			{
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
		

	
	public void processRecordType3198(String filePath)
	{
		initialize();
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			
			Branch branch;
			BGL_3198 bgl_3198;
			BGL_3198_TRANSACTION bgl_3198_transaction;
			
			Date txnDate = null;
			String journalNumber;
			String branchCode;
			String bglAccountNumber;
			double amount;
			
			
			
			while ((line = br.readLine()) != null)
			{
				
				session.beginTransaction();
				
				
				// get string array by splitting the line and remove all white spaces
				String[] column = removeWhiteSpace(line.split(cvsSplitBy));
				
				try	{ txnDate = new SimpleDateFormat("dd/MM/yyyy").parse(column[2]);} catch(Exception e){e.printStackTrace();}
				branchCode = column[6];
				bglAccountNumber = column[1];
				journalNumber = column[3];
				amount = Double.valueOf(column[5]);
				TransactionCompositeKey txnKey = new TransactionCompositeKey(journalNumber,amount);
				
				branch = (Branch) session.get(Branch.class, branchCode);
				if(branch == null)
				{
					branch = new Branch();
					branch.setBranchCode(branchCode);
					
				}
				
				bgl_3198 = (BGL_3198) session.get(BGL_3198.class, bglAccountNumber);
				if(bgl_3198 == null){
					
					bgl_3198 = new BGL_3198();
					bgl_3198.setBranchCode(branch);
					bgl_3198.setBglAccountNumber(bglAccountNumber);
				
				}
				
				bgl_3198_transaction = (BGL_3198_TRANSACTION) session.get(BGL_3198_TRANSACTION.class, txnKey);
				if(bgl_3198_transaction == null){
					bgl_3198_transaction = new BGL_3198_TRANSACTION();
					bgl_3198_transaction.setTxnKey(txnKey);
					bgl_3198_transaction.setTxnDate(txnDate);
					bgl_3198_transaction.setBglAccountNumber(bgl_3198);
				}
				
				bgl_3198.getBgl_3198_transaction().add(bgl_3198_transaction);
				branch.getBgl_3198().add(bgl_3198);
				
			
				
				session.saveOrUpdate(branch);
				session.saveOrUpdate(bgl_3198);
				session.saveOrUpdate(bgl_3198_transaction);
				session.flush();
				session.clear();
				session.getTransaction().commit();
				
				
			}
			session.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		}
	
	public void processBranchRegionZoneNetwork(String filePath)
	{

		initialize();
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			
			Branch branch;
			Region region;
			Zone zone;
			Network network;
			
			String branchCode;
			String branchName;
			String regionName;
			String zoneName;
			String networkName;
		
			
			
			
			while ((line = br.readLine()) != null)
			{
				
				session.beginTransaction();
				
				
				// get string array by splitting the line and remove all white spaces
				String[] column = removeWhiteSpace(line.split(cvsSplitBy));
				
				
				branchCode = column[0];
				branchName = column[1];
				regionName = column[3];
				zoneName = column[6];
				networkName =column[9];
				
				
				branch = (Branch) session.get(Branch.class, branchCode);
				if(branch == null)
				{
					branch = new Branch();
					branch.setBranchCode(branchCode);
					branch.setBranchName(branchName);
				
				}
				else branch.setBranchName(branchName);
				
				region = (Region) session.get(Region.class, regionName);
				if(region == null)
				{
					region = new Region();
					region.setRegionName(regionName);
				
				}
				
				zone = (Zone) session.get(Zone.class, zoneName);
				if(zone == null)
				{
					zone = new Zone();
					zone.setZoneName(zoneName);
				
				}
				
				
				network = (Network) session.get(Network.class, networkName);
				if(network == null)
				{
					network = new Network();
					network.setNetworkName(networkName);
				}
				
				branch.setRegionName(region);
				region.setZoneName(zone);
				zone.setNetworkName(network);
				
				
				
			
				
				session.saveOrUpdate(branch);
				session.saveOrUpdate(region);
				session.saveOrUpdate(zone);
				session.saveOrUpdate(network);
				session.flush();
				session.clear();
				session.getTransaction().commit();
				
				
			}
			session.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		
	}

}	
		


		



