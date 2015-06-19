package org.sbm.atm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FilePath {
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Id; 
	
	
	 public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	@Column
	 private String fileType;  
	  
	 
	 @Column(name = "path")  
	 private String path;
	 
	 @Column(name = "processed")
	 private boolean processed;



	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	 

}
