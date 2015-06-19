package org.sbm.atm.comparators;

import java.util.Comparator;

import org.sbm.atm.model.ATM;

public class SortBgl3198Comparator implements Comparator<ATM> {

	@Override
	public int compare(ATM o1, ATM o2)
	{
		
			return o1.getBgl_3198().getBglAccountNumber().compareTo(o2.getBgl_3198().getBglAccountNumber());
	}
	

}
	
