package org.sbm.atm.services;

import java.util.List;

import org.sbm.atm.dao.FilePathDataDaoImpl;
import org.sbm.atm.dao.FileProcessorDao;
import org.sbm.atm.model.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.sbm.atm.constant.Constant;


public class ProcessFilesImpl implements ProcessFiles {

	

	@Autowired
	private FilePathDataDaoImpl filePathDataDaoImpl;
	
	@Autowired
	private FileProcessorDao fileProcessorDao;
	
	public FilePathDataDaoImpl getFilePathDataDaoImpl() {
		return filePathDataDaoImpl;
	}


	public void setFilePathDataDaoImpl(FilePathDataDaoImpl filePathDataDaoImpl) {
		this.filePathDataDaoImpl = filePathDataDaoImpl;
	}


	public FileProcessorDao getFileProcessorDao() {
		return fileProcessorDao;
	}


	public void setFileProcessorDao(FileProcessorDao fileProcessorDao) {
		this.fileProcessorDao = fileProcessorDao;
	}



	
	
	@Override
	public void processFile() {
		
		List<FilePath> list = filePathDataDaoImpl.getList();
		
		for (FilePath file : list) {
			
			
			String type = file.getFileType();
			String path = file.getPath();
			
			switch (type) {
				case  Constant.RECORD_TYPE_3197_3199 :
					fileProcessorDao.processBGL_3197_3199_transaction(path);
					
				break;
				case Constant.RECORD_TYPE_ATMADMIN :
					fileProcessorDao.processRecordTypeAdminTxn(path);
					break;
				case Constant.RECORD_TYPE_3198:
					fileProcessorDao.processRecordType3198(path);
					break;
				case Constant.RECORD_TYPE_ATM_MAPPING:
					fileProcessorDao.processATMRelationship(path);
					break;
				case Constant.RECORD_TYPE_BRANCH__REGION_ZONE_NETWORK_MAPPING:
					fileProcessorDao.processBranchRegionZoneNetwork(path);
					break;
				default:
					break;
			}
		
			
		}
		
		
		
	}

}
