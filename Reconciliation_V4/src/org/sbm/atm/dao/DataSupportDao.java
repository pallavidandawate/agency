package org.sbm.atm.dao;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.SessionFactory;
import org.sbm.atm.model.ATM;
import org.sbm.atm.model.ATM_TRANSACTION;
import org.sbm.atm.model.BGL_3197_3199;
import org.sbm.atm.model.BGL_3197_3199_TRANSACTION;
import org.sbm.atm.model.BGL_3198_TRANSACTION;
import org.sbm.atm.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;



public class DataSupportDao {
	
	@Autowired  
	SessionFactory sessionFactory;
	
	@Autowired
	BRANCH_DAO branchDao;
	
	@Autowired
	BGL_3197_3199_DAO bgl_3197_3198_Dao;
	
	@Autowired
	BGL_3197_3199_TRANSACTION_DAO bgl_3197_3199_transaction_Dao;
	
	@Autowired
	ATM_TRANSACTION_DAO atmTransactionDao;
	
	@Autowired
	BGL_3198_TRANSACTION_DAO bgl_3198_transaction_dao;
	
	@Autowired
	RegionDao regionDao;
	
	
	
	public List<DataClass> getData(String branchCode)
	{
		Branch branch = new Branch();
		branch = branchDao.getBranchByBranchCode(branchCode);
		Date cashOutSourcingDate = branch.getCashOutSourcingStartDate();
		
		
		//System.out.println("Start Date" + cashOutSourcingDate.toString());
		
		
		Calendar startDate = Calendar.getInstance();
		Calendar todayDate = Calendar.getInstance();
		todayDate.setTime(new Date());
		startDate.setTime(cashOutSourcingDate);
		
		
		List<DataClass> dataClassList = new ArrayList<DataClass>();
		
		List<BGL_3197_3199> bgl_3197_3199_list = bgl_3197_3198_Dao.getBglAccountNumberByBranchCode(branch);
		
		//Creating HashMap for bglAccountNumber with commulative difference as value. Initializing with 0
		HashMap<String, Double> commulativeDifferenceMap = new HashMap<String, Double>();
		
		//Creating HashMap for cummalative 3198 difference
		HashMap<String, Double> commulative3198DifferenceMap = new HashMap<String, Double>();
		
		for(BGL_3197_3199 bgl_3197_3199 : bgl_3197_3199_list ){
			commulativeDifferenceMap.put(bgl_3197_3199.getBglAccountNumber(), 0.0);
			commulative3198DifferenceMap.put(bgl_3197_3199.getBglAccountNumber(), 0.0);
		}
		
	
		
		SimpleDateFormat dateFormat =  new SimpleDateFormat ("dd/MM/yyyy");
		
		//Initializing temporary variables
		double totalDebitInIntermediateAccount=0;
		double totalCreditInIntermediateAccount=0;
		double dayDefference = 0;
		double commulativeDifference = 0;
		
		double adminIncrease = 0.0;
		double adminDecrease = 0.0;
		
		double net3198 = 0.0;
		double days3198 = 0.0;
		double commulative3198 = 0.0;
		
		
		while(startDate.before(todayDate)){
			
			//processing intermediate bgl account
			for(BGL_3197_3199 bgl_3197_3199 : bgl_3197_3199_list )
			{
				totalDebitInIntermediateAccount=0;
				totalCreditInIntermediateAccount=0;
				dayDefference = 0;
				commulativeDifference = 0;
				
				adminIncrease = 0.0;
				adminDecrease = 0.0;
				
				net3198 = 0.0;
				days3198 = 0.0;
				commulative3198 = 0.0;
				
				String bglAccountNumber = bgl_3197_3199.getBglAccountNumber();
				
				
				Date currentDate = startDate.getTime();
				
								
				for(BGL_3197_3199_TRANSACTION tx : bgl_3197_3199_transaction_Dao.getBgl_3197_3199_transactionList(bgl_3197_3199,currentDate))
				{
					double txnAmount =  tx.getTxnKey().getTxnAmount();
					if(txnAmount<0)
					{
						totalDebitInIntermediateAccount += txnAmount;
					}
					else{
						
						totalCreditInIntermediateAccount += txnAmount;
					}
					dayDefference  = dayDefference + txnAmount;
										
					
				
				}
				commulativeDifference =  dayDefference + commulativeDifferenceMap.get(bglAccountNumber);
				commulativeDifferenceMap.put(bglAccountNumber, commulativeDifference);
				
				
				
				
				adminIncrease = atmTransactionDao.getTotalAdminIncreaseByBgl_3197_3199_AndDate(bgl_3197_3199, currentDate);
				adminDecrease = atmTransactionDao.getTotalAdminDecreaseByBgl_3197_3199_AndDate(bgl_3197_3199, currentDate);
				net3198 = bgl_3198_transaction_dao.getNetAmountBgl3198_ByBgl_3197_3199_AndDate(bgl_3197_3199, currentDate);
				
				
			//	days3198 = Math.abs(net3198) - dayDefference;
				
			//  days3198 = Math.abs(net3198) + Math.abs(dayDefference) - totalCreditInIntermediateAccount  ; //newly updated 1
				
			//	days3198 = net3198 - dayDefference + totalCreditInIntermediateAccount  ; //newly updated 2
				
				days3198 = dayDefference * (-1);										// changed on 17.06.2015
				
/*				if(dayDefference > 0.0){
					
					commulative3198 = days3198 + commulative3198DifferenceMap.get(bglAccountNumber + net3198)- dayDefference ;
					commulative3198DifferenceMap.put(bglAccountNumber, commulative3198);
					
				}
				else
				{
					//commulative3198 = days3198 + commulative3198DifferenceMap.get(bglAccountNumber) +net3198;
					
				}
*/				

			//  commulative3198 = days3198 + commulative3198DifferenceMap.get(bglAccountNumber); //newly updated
				
				commulative3198 = commulativeDifference * (-1);							//changed on 17.06.2015
				commulative3198DifferenceMap.put(bglAccountNumber, commulative3198 );
				 
				
				
				
				/*System.out.println(dateFormat.format(currentDate)
								   +"  "
								   + bglAccountNumber
						           +  "     " 
						           + totalDebitInIntermediateAccount
						           + "           "
						           + totalCreditInIntermediateAccount
						           + "           "
						           +dayDefference
						           +"            "
						           + commulativeDifferenceMap.get(bglAccountNumber)
						           +"         "
						           +adminIncrease
						           +"          "
						           +adminDecrease
						           +"       "
						           +net3198
						           
						           );*/
				
				DataClass dataModel = new DataClass();
				dataModel.setTxnDate(dateFormat.format(currentDate));
				dataModel.setBglAccountNumber(bglAccountNumber);
				dataModel.setTotalDebitInIntermediateAccount(totalDebitInIntermediateAccount);
				dataModel.setTotalCreditInIntermediateAccount(totalCreditInIntermediateAccount);
				dataModel.setDayDefference(dayDefference);
				dataModel.setCommulativeDifference(commulativeDifferenceMap.get(bglAccountNumber));
				dataModel.setTotalAdminIncrease(adminIncrease);
				dataModel.setTotalAdminDecrease(adminDecrease);
				dataModel.setNet3198(net3198);;
				dataModel.setDays3198(days3198);
				dataModel.setCommulative3198(commulative3198DifferenceMap.get(bglAccountNumber));
				
				dataClassList.add(dataModel);
				
				
				
			
			}
			
			//increasing the date by 1 days
			startDate.add(Calendar.DAY_OF_YEAR, 1);
			
			
			
			
		}
		
		return dataClassList;
		
	}
	
	public List<DataClassForBglNumbers> getBgl_3197_3199_ListByBranchCode(String branchCode)
	{
		List<DataClassForBglNumbers> bglAccountNumbers = new ArrayList<DataClassForBglNumbers>();
		Branch branch = new Branch();
		branch = branchDao.getBranchByBranchCode(branchCode);
		List<BGL_3197_3199> bgl_3197_3199_list = bgl_3197_3198_Dao.getBglAccountNumberByBranchCode(branch);
		for(BGL_3197_3199 bgl_3197_3199 : bgl_3197_3199_list ){
			
				DataClassForBglNumbers dataClassForBglNumbers = new DataClassForBglNumbers();
				dataClassForBglNumbers.setBglAccountNumber(bgl_3197_3199.getBglAccountNumber());
				dataClassForBglNumbers.setSelected(true);
				bglAccountNumbers.add(dataClassForBglNumbers);
				
		}
		return bglAccountNumbers;
	}
	
	
	public List<ATM> getBgl3198List(String bglAccountNumber)
	{
		List<ATM> lists = bgl_3198_transaction_dao.getBGL_3198_ListByBgl3197_3199(bglAccountNumber);
		return lists;
		
	}
	
	public List<BGL_3198_TRANSACTION> get3198TxnList(ATM atm, Date date)
	{
		return bgl_3198_transaction_dao.getBgl3198TransactionListByAtmAndDate(atm, date);
	}
	
	public List<ATM_TRANSACTION> getAtmTxnList(ATM atm, Date date)
	{
		return atmTransactionDao.getAtmTransactionListByAtmIdAndDate(atm, date);
	}
	
}
