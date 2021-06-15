package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Autotransition;

public interface AutotransitionService {

	public Autotransition saveAutotransition(Autotransition autotransition) throws StringValidationException;
	public Autotransition editAutotransition(Autotransition autotransition) throws ElementDoesntExistException, StringValidationException;
	public Iterable<Autotransition> findAll();
	public Optional<Autotransition> findAutotransition(long autoId);
	//public Autotransition save(Autotransition autotransition);
	public Autotransition save(Autotransition autotransition);
	void deleteAuto(long aut) throws ElementDoesntExistException;
	
}
