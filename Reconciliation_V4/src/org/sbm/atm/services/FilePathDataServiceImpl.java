package org.sbm.atm.services;  
  
import java.util.List;  
  


import org.sbm.atm.dao.FilePathDataDao;
import org.sbm.atm.model.FilePath;
import org.springframework.beans.factory.annotation.Autowired;  
  

  
public class FilePathDataServiceImpl implements FilePathDataService {  
   
 @Autowired  
 FilePathDataDao filePathDataDao;  
  
 @Override  
 public void  insertRow(FilePath employee) {  
   filePathDataDao.insertRow(employee);  
 }  
  
 @Override
public void deleteAllRow() {
	 filePathDataDao.deleteAllRow();
	
}

@Override  
 public List<FilePath> getList() {  
  return filePathDataDao.getList();  
 }  
  
 @Override  
 public FilePath getRowById(int id) {  
  return filePathDataDao.getRowById(id);  
 }  
  
 @Override  
 public int updateRow(FilePath employee) {  
  return filePathDataDao.updateRow(employee);  
 }  
  
 @Override  
 public int deleteRow(int id) {  
  return filePathDataDao.deleteRow(id);  
 }  
  
}  
  
