package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.model.Institution;

public interface InstitutionService {

	public Institution saveInstitution(Institution institution);
	public Optional<Institution> findInstitution(long instId);
	public Iterable<Institution> findAll();
	
}
