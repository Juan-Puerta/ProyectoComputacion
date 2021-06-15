package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Localcondition;

public interface LocalConditionService {

	public Localcondition saveLocalCondition(Localcondition localcondition) throws ElementDoesntExistException, StringValidationException;
	public Localcondition editLocalCondition(Localcondition localcondition) throws ElementDoesntExistException, StringValidationException;
	public Iterable<Localcondition> findAll();
	public Optional<Localcondition> findLocalcondition(long id);
	
}
