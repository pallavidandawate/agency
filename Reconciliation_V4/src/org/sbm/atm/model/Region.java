package org.sbm.atm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Region {
	
	
	@Id
	@Column
	String regionName;
	
	@OneToMany(mappedBy="regionName")
	private List<Branch>  branch = new ArrayList<Branch>();
	
	@ManyToOne
	@JoinColumn(name="zoneName")
	private Zone zoneName;
	
	public Zone getZoneName() {
		return zoneName;
	}

	public void setZoneName(Zone zoneName) {
		this.zoneName = zoneName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public List<Branch> getBranch() {
		return branch;
	}

	public void setBranch(List<Branch> branch) {
		this.branch = branch;
	}

	

}
