package org.sbm.atm.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sbm.atm.model.ATM;
import org.sbm.atm.model.ATM_TRANSACTION;
import org.sbm.atm.model.BGL_3198_TRANSACTION;
import org.sbm.atm.model.FilePath;
import org.sbm.atm.services.FilePathDataService;
import org.sbm.atm.comparators.SortBgl3198Comparator;
import org.sbm.atm.constant.Constant;
import org.sbm.atm.dao.BRANCH_DAO;
import org.sbm.atm.dao.DataClass;
import org.sbm.atm.dao.DataClassForBglNumbers;
import org.sbm.atm.dao.DataSupportDao;
import org.sbm.atm.dao.NetworkDao;
import org.sbm.atm.dao.RegionDao;
import org.sbm.atm.dao.ZoneDao;
import org.sbm.atm.dto.BankDto;
import org.sbm.atm.dto.NetworkDto;
import org.sbm.atm.dto.RegionDto;
import org.sbm.atm.dto.ZoneDto;
import org.sbm.atm.services.ProcessFilesImpl;
import org.sbm.atm.model.FileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import flexjson.JSONSerializer;


 
@Controller
public class FileUploadController {
	
	
	@Autowired
	FilePathDataService filePathDataService;
	
	@Autowired
	FilePath filePath;
	
	@Autowired
	ProcessFilesImpl processFileImpl;
	
	@Autowired
	DataSupportDao dataSupportDao;
	
	@Autowired
	RegionDao regionDao;
	
	@Autowired
	ZoneDao zoneDao;

	@Autowired
	NetworkDao networkDao;
	
	@Autowired
	BRANCH_DAO branchDao;
	


	public ProcessFilesImpl getProcessFileImpl() {
		return processFileImpl;
	}

	public void setProcessFileImpl(ProcessFilesImpl processFileImpl) {
		this.processFileImpl = processFileImpl;
	}

	public FilePathDataService getFilePathDataService() {
		return filePathDataService;
	}

	public void setFilePathDataService(FilePathDataService filePathDataService) {
		this.filePathDataService = filePathDataService;
	}

	public FilePath getFilePath() {
		return filePath;
	}

	public void setFilePath(FilePath filePath) {
		this.filePath = filePath;
	}


	
     
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String displayForm() {
        return "file_upload_form";
    }
     
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {
         
        List<MultipartFile> files = uploadForm.getFiles();
 
        List<String> fileNames = new ArrayList<String>();
         
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                
                String type = multipartFile.getName();
             
                
                fileNames.add(fileName);

                
                String filePath = System.getProperty("user.home") + File.separator  + fileName;
                
                try {
					
                	/*File file = new File(filePath); 
                	if(file.exists() && !file.isDirectory()) { file.delete(); }*/
                	
                	multipartFile.transferTo(new File(filePath));
					
					 //saving file name into database
					
					switch (type) {
					case "files[0]":
						this.filePath.setFileType(Constant.RECORD_TYPE_3197_3199);
						break;
					case "files[1]":
						this.filePath.setFileType(Constant.RECORD_TYPE_ATMADMIN);
						break;
					case "files[2]":
						this.filePath.setFileType(Constant.RECORD_TYPE_3198);
						break;
					case "files[3]":
						this.filePath.setFileType(Constant.RECORD_TYPE_ATM_MAPPING);
						break;
					case "files[4]":
						this.filePath.setFileType(Constant.RECORD_TYPE_BRANCH__REGION_ZONE_NETWORK_MAPPING);
						break;

					default:
						break;
					}
					
				
					this.filePath.setPath(filePath);
					
					filePathDataService.deleteAllRow();
					filePathDataService.insertRow(this.filePath);
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                
            }
        }
         
        map.addAttribute("files", fileNames);
        
       
        
        
        return "file_upload_success";
    }
    
    @RequestMapping(value = "/processFile", method = RequestMethod.POST)
    public String processFile(ModelMap model)
    {
    	
    	processFileImpl.processFile();
      	
     	return "result";
    }
    
   
    
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
	 public @ResponseBody String getData(@RequestParam Map<String,String> allRequestParams, ModelMap model)
	 {
	    	
    		String branchCode = allRequestParams.get("branchCode");
    		List<DataClass> list = dataSupportDao.getData(branchCode);
	    	
	    	   return new JSONSerializer().exclude("*.class").serialize(list);
	    	  
	    	 
	    	 
	  
	 }
    
    @RequestMapping(value = "/getBgl3197List", method = RequestMethod.GET)
	 public @ResponseBody String getBgl3197List(@RequestParam Map<String,String> allRequestParams, ModelMap model)
	 {
	    	
   		String branchCode = allRequestParams.get("branchCode");
   		List<DataClassForBglNumbers> list = dataSupportDao.getBgl_3197_3199_ListByBranchCode(branchCode);
	    	
	    	   return new JSONSerializer().exclude("*.class").serialize(list);
	    	  
	    	 
	    	 
	  
	 }
    

    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	 public String homePage(ModelMap model)
	 {
	    	
	      	
	    	//return "index";
    	return "redirect:/pages/home.html";
	 }
    
    @RequestMapping(value = "/get3198TxnDetails", method = RequestMethod.GET)
 	 public String get3198TxnDetails(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
 	    	
    		String bglAccountNumber = allRequestParams.get("bglAccountNumber");
    		String txnDate = allRequestParams.get("txnDate");
    		String totalDebitInAgencyAccount = allRequestParams.get("TotalDebit");
    		Date date = null;
    		
    		try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(txnDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
    	    		    	
 	    model.addAttribute("bglAccountNumber", bglAccountNumber);
 	    model.addAttribute("txnDate", txnDate);
 	    model.addAttribute("TotalDebit",totalDebitInAgencyAccount);
 	    
 	    Map<String,List<BGL_3198_TRANSACTION>> bgl3198Txns = new TreeMap<String, List<BGL_3198_TRANSACTION>>();
 	   HashMap<String,List<ATM_TRANSACTION>> AtmTxns = new HashMap<String, List<ATM_TRANSACTION>>();
 	   
 	   
 	       
 	    List<ATM> AtmList = dataSupportDao.getBgl3198List(bglAccountNumber);
 	    Collections.sort(AtmList, new SortBgl3198Comparator());
  	    model.put("atmList", AtmList);
 	    for(ATM atm : AtmList){
 	    	
 	    	
 	    	bgl3198Txns.put(atm.getBgl_3198().getBglAccountNumber(), dataSupportDao.get3198TxnList(atm, date));
 	    	AtmTxns.put(atm.getAtmId(), dataSupportDao.getAtmTxnList(atm, date));
 	    }
 	    
 	    
 	    
 	    model.put("bgl3198Txns", bgl3198Txns);
 	    model.put("AtmTxns", AtmTxns);
 	    return "get3198TxnDetails";
 	      
 	       
 	       
 	  
 	 }
    
    @RequestMapping(value = "/getRegionData", method = RequestMethod.GET)
  	 public @ResponseBody String getRegionData(@RequestParam Map<String,String> allRequestParams, ModelMap model)
  	 {
  	    	
      		String regionName = allRequestParams.get("regionName");
      		String cutOfDate = allRequestParams.get("cutOfDate");
      		Date date = null;
      		
      		//converting string date into date.
      		try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(cutOfDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		
      		List<RegionDto> regionDtoList = regionDao.getRegionData(regionName, date);
      		
      		String output = "Region Name=" + regionName + "  cut of Date= " + cutOfDate ;
      		System.out.println(output);
      		
      		
      		
      		
  	    	/*List<Double> sum = dataSupportDao.getSum();*/
  	    	   return new JSONSerializer().exclude("*.class").serialize(regionDtoList);
  	    	  
  	    	     	 
  	  
  	 }
    
    @RequestMapping(value = "/getRegionNames", method = RequestMethod.GET)
 	public @ResponseBody String getRegionNames(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
    		//System.out.println("Region Name asked");
     		     		
 	    	List<String> regionNames = regionDao.getRegionNames();
 	    	
 	    	return new JSONSerializer().exclude("*.class").serialize(regionNames);
 	    	  
   
 	 }
    @RequestMapping(value = "/getZoneNames", method = RequestMethod.GET)
 	public @ResponseBody String getZoneNames(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
 	    	
     		     		
 	    	List<String> zoneNameList = zoneDao.getZoneNames();
 	    	return new JSONSerializer().exclude("*.class").serialize(zoneNameList);
 	    	  
   
 	 }
    
    @RequestMapping(value = "/getZoneData", method = RequestMethod.GET)
 	 public @ResponseBody String getZoneData(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
 	    	
     		String zoneName = allRequestParams.get("zoneName");
     		String cutOfDate = allRequestParams.get("cutOfDate");
     		Date date = null;
     		
     		//converting string date into date.
     		try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(cutOfDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		
     		List<ZoneDto> zoneDtoList = zoneDao.getZoneData(zoneName, date);
     		
     		String output = "Zone Name=" + zoneName + "  cut of Date= " + cutOfDate ;
     		System.out.println(output);
     		
     		
     		
     		
 	    	/*List<Double> sum = dataSupportDao.getSum();*/
 	    	   return new JSONSerializer().exclude("*.class").serialize(zoneDtoList);
 	
 	 }
    
    @RequestMapping(value = "/getNetworkNames", method = RequestMethod.GET)
 	public @ResponseBody String getNetworkNames(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
 	    	
     		     		
 	    	List<String> networkNameList = networkDao.getNetworkNames();
 	    	return new JSONSerializer().exclude("*.class").serialize(networkNameList);
 	    	  
   
 	 }
    
    @RequestMapping(value = "/getNetworkData", method = RequestMethod.GET)
	 public @ResponseBody String getNetworkData(@RequestParam Map<String,String> allRequestParams, ModelMap model)
	 {
	    	
    		String networkName = allRequestParams.get("networkName");
    		String cutOfDate = allRequestParams.get("cutOfDate");
    		Date date = null;
    		
    		//converting string date into date.
    		try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(cutOfDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		List<NetworkDto> networkDtoList = networkDao.getNetworkData(networkName, date);
    		
    		String output = "Zone Name=" + networkName + "  cut of Date= " + cutOfDate ;
    		System.out.println(output);
    		
    		
    		
    		
	    	/*List<Double> sum = dataSupportDao.getSum();*/
	    	   return new JSONSerializer().exclude("*.class").serialize(networkDtoList);
	
	 }
    
    @RequestMapping(value = "/getBankData", method = RequestMethod.GET)
 	public @ResponseBody String getBankData(@RequestParam Map<String,String> allRequestParams, ModelMap model)
 	 {
    	
		String cutOfDate = allRequestParams.get("cutOfDate");
		Date date = null;
		
		//converting string date into date.
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(cutOfDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<BankDto> bankDtoList = networkDao.getBankData(date);
		
		
		
    	/*List<Double> sum = dataSupportDao.getSum();*/
    	   return new JSONSerializer().exclude("*.class").serialize(bankDtoList);
   
 	 }
    
    @RequestMapping(value = "/getBranchName", method = RequestMethod.GET)
	 public @ResponseBody String getBranchName(@RequestParam Map<String,String> allRequestParams, ModelMap model)
	 {
	    	
   		String branchCode = allRequestParams.get("branchCode");
   		
   		
   		String branchName = branchDao.getBranchByBranchCode(branchCode).getBranchName();
   		
   		
	    	/*List<Double> sum = dataSupportDao.getSum();*/
	    	   return new JSONSerializer().exclude("*.class").serialize(branchName);
	
	 }
    
    
}
   
    
