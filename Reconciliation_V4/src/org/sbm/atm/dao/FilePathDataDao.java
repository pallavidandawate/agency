package org.sbm.atm.dao;
  
  
import java.util.List;

import org.sbm.atm.model.FilePath;
  
public interface FilePathDataDao {  
 public void insertRow(FilePath filePath);  
  
 public List<FilePath> getList();  
  
 public FilePath getRowById(int id);  
  
 public int updateRow(FilePath filePath);  
  
 public int deleteRow(int id);  
 
 public void deleteAllRow();
  
}  
