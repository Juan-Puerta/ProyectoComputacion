package com.tallerOne.main.delegate.interfaces;

import java.util.List;

import com.tallerOne.main.model.Localcondition;

public interface LocalConditionDelegate {

	public List<Localcondition> getLocalconditions();
	public Localcondition getLocalcondition(long locId);
	public Localcondition addLocalcondition(Localcondition loc);
	public List<Localcondition> deleteLocalcondition(long locId);
	public Localcondition editLocalcondition(Localcondition loc);
	
}
