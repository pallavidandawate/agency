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
public class Zone {
	
	
	@Id
	@Column
	String zoneName;
	
	@OneToMany(mappedBy="zoneName")
	private List<Region>  region = new ArrayList<Region>();
	
	
	@ManyToOne
	@JoinColumn(name="networkName")
	private Network networkName;


	public String getZoneName() {
		return zoneName;
	}


	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}


	public List<Region> getRegion() {
		return region;
	}


	public void setRegion(List<Region> region) {
		this.region = region;
	}


	public Network getNetworkName() {
		return networkName;
	}


	public void setNetworkName(Network networkName) {
		this.networkName = networkName;
	}

	

}
