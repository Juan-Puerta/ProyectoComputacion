package com.tallerOne.main.rest.controller.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tallerOne.main.model.Precondition;


public interface PreRestController {

	public Iterable<Precondition> getPreconditions();
	public Precondition getPrecondition(@PathVariable long preId);
	public void addPrecondition(@RequestBody Precondition pre);
	public void deletePrecondition(@PathVariable long preId);
	public void editPrecondition(@RequestBody Precondition pre);
	
}
