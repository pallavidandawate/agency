package org.sbm.atm.dao;
  
  
import java.io.Serializable;
import java.util.List;


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.sbm.atm.model.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
  
  
public class FilePathDataDaoImpl implements FilePathDataDao {  
  
 @Autowired  
 SessionFactory sessionFactory;  
  
 @Override  
 @Transactional  
 public void  insertRow(FilePath filePath) {  
  Session session = sessionFactory.openSession();  
  Transaction tx = session.beginTransaction();  
  session.save(filePath);  
  
 // Serializable id = session.getIdentifier(filePath);  
  
  session.flush();
  session.clear();
  tx.commit();  
  session.close();  

  //return (String) id;
 }  
  
 @Override
public void deleteAllRow() {
	 Session session = sessionFactory.openSession();  
	  Transaction tx = session.beginTransaction();  
	  session.createQuery("DELETE FROM FilePath").executeUpdate();
	 
	  tx.commit();  
	  session.close();  
	   
}

@Override  
 public List<FilePath> getList() {  
  Session session = sessionFactory.openSession();  
  @SuppressWarnings("unchecked")  
  List<FilePath> filePathList = session.createQuery("from FilePath")  
    .list();  
  session.close();  
  return filePathList;  
 }  
  
 @Override  
 public FilePath getRowById(int id) {  
  Session session = sessionFactory.openSession();  
  FilePath employee = (FilePath) session.load(FilePath.class, id);  
  return employee;  
 }  
  
 @Override  
 public int updateRow(FilePath filePath) {  
  Session session = sessionFactory.openSession();  
  Transaction tx = session.beginTransaction();  
  session.saveOrUpdate(filePath);  
  tx.commit();  
  Serializable id = session.getIdentifier(filePath);  
  session.close();  
  return (Integer) id;  
 }  
  
 @Override  
 public int deleteRow(int id) {  
  Session session = sessionFactory.openSession();  
  Transaction tx = session.beginTransaction();  
  FilePath filePath = (FilePath) session.load(FilePath.class, id);  
  session.delete(filePath);  
  tx.commit();  
  Serializable ids = session.getIdentifier(filePath);  
  session.close();  
  return (Integer) ids;  
 }  
  
}  
