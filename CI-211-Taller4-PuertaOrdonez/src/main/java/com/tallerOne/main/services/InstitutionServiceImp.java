package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.model.Institution;
import com.tallerOne.main.repositories.InstitucionesRepository;

@Service
public class InstitutionServiceImp implements InstitutionService{

	private InstitutionDao repositoryDao;

	@Autowired
	public InstitutionServiceImp(InstitutionDao repositoryDao) {
		super();
		this.repositoryDao = repositoryDao;
	}
	
	public Institution saveInstitution(Institution institution) {
		repositoryDao.saveIns(institution);
		return institution;
	}
	
	public Optional<Institution> findInstitution(long instId) {
		return repositoryDao.findById(instId);
	}
	
	public Iterable<Institution> findAll() {
		return repositoryDao.findAll();
	}
	
}
