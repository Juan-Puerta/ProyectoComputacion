package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Threshold;

public interface ThresholdService {

	public Threshold saveThreshold(Threshold threshold) throws ElementDoesntExistException, StringValidationException;
	public Threshold editThreshold(Threshold threshold) throws ElementDoesntExistException, StringValidationException;
	public Iterable<Threshold> findAll();
	public Optional<Threshold> findThreshold(long id);
	public Threshold save(Threshold threshold);
	
}
