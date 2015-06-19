package org.sbm.atm.services;  
  
import java.util.List;  
import org.sbm.atm.model.FilePath;
  
public interface FilePathDataService {  
 public void insertRow(FilePath filePath);  
  
 public List<FilePath> getList();  
  
 public FilePath getRowById(int id);  
  
 public int updateRow(FilePath filePath);  
  
 public int deleteRow(int id);  
 
 public void deleteAllRow();
 
  
}  
  
