package org.sbm.atm.comparators;

import java.util.Comparator;

import org.sbm.atm.model.BGL_3198_TRANSACTION;

public class BGL_3198_Txn_Comparator implements Comparator<BGL_3198_TRANSACTION>{

	@Override
	public int compare(BGL_3198_TRANSACTION o1, BGL_3198_TRANSACTION o2) {
		
		return o1.getBglAccountNumber().getBglAccountNumber().compareTo(o2.getBglAccountNumber().getBglAccountNumber());
	
	}
	
	

}
