package com.tallerOne.main.rest.controller.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.tallerOne.main.model.Localcondition;

public interface LocalRestController {

	public Iterable<Localcondition> getLocalconditions();
	public Localcondition getLocalcondition(@PathVariable long locId);
	public void addLocalcondition(@RequestBody Localcondition loc);
	public void deleteLocalcondition(@PathVariable Localcondition locId);
	public void editLocalcondition(@RequestBody Localcondition loc);
	//void deleteLocalcondition(Localcondition locId);
	
}
