package com.tallerOne.main.rest.controller.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tallerOne.main.model.Threshold;

public interface ThrRestController {

	public Iterable<Threshold> getThresholds();
	public Threshold getThreshold(@PathVariable long thrId);
	public void addThreshold(@RequestBody Threshold thr);
	public void deleteThreshold(@PathVariable long thrId);
	public void editThreshold(@RequestBody Threshold thr);
	
}
