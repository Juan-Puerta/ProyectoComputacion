package com.tallerOne.main.rest.controller.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.tallerOne.main.model.Autotransition;

public interface AutoRestController {

	public Iterable<Autotransition> getAutotransitions();
	public Autotransition getAutotransition(@PathVariable long autId);
	public void addAutotransition(@RequestBody Autotransition aut);
	public void deleteAutotransition(@PathVariable long autId);
	public void editAutotransition(@RequestBody Autotransition aut);
	
}
