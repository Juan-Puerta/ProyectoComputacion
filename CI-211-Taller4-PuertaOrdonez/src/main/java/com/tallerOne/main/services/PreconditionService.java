package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;

public interface PreconditionService {

	public Precondition savePrecondition(Precondition precondition) throws ElementDoesntExistException, StringValidationException;
	public Precondition editPrecondition(Precondition precondition) throws ElementDoesntExistException, StringValidationException;
	public void deletePrecondition(Precondition precondition);
	public Iterable<Precondition> findAll();
	public Optional<Precondition> findPrecondition(long id);
	public Precondition save(Precondition precondition);

}
