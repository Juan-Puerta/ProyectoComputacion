package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.daos.ThresholdDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Threshold;
import com.tallerOne.main.repositories.InstitucionesRepository;
import com.tallerOne.main.repositories.ThresholdsRepository;

@Service
public class ThresholdServiceImp implements ThresholdService{

	private ThresholdDao repositoryDao;
	private InstitutionDao insRepositoryDao;
	
	@Autowired
	public ThresholdServiceImp(ThresholdDao repositoryDao, InstitutionDao insRepositoryDao) {
		super();
		this.repositoryDao = repositoryDao;
		this.insRepositoryDao = insRepositoryDao;
	}

	public Threshold save(Threshold threshold) {
		repositoryDao.saveThr(threshold);
		return threshold;
	}
	
	public Threshold saveThreshold(Threshold threshold) throws ElementDoesntExistException, StringValidationException {
		if(insRepositoryDao.findById(threshold.getInstInstId().longValue()).isPresent()) {
			if(!(threshold.getThresName().equals("")) && !(threshold.getThresValue().equals(""))) {
				if(threshold.getThresValuetype().equals("String") || threshold.getThresValuetype().equals("Int") || threshold.getThresValuetype().equals("Float") || threshold.getThresValuetype().equals("Boolean") || threshold.getThresValuetype().equals("Char")) {
					repositoryDao.saveThr(threshold);
					return threshold;
				}else {
					throw new StringValidationException("El tipo del valor no es: String, Int, Float, Boolean o Char");
				}
			}else {
				throw new StringValidationException("El nombre o el valor son vacios");
			}
		}else {
			throw new ElementDoesntExistException("La institución no existe");
		}
	}
	
	public Threshold editThreshold(Threshold threshold) throws ElementDoesntExistException, StringValidationException {
		if(repositoryDao.findById(threshold.getThresId()).isPresent()) {
			if(insRepositoryDao.findById(threshold.getInstInstId().longValue()).isPresent()) {
				if(!(threshold.getThresName().equals("")) && !(threshold.getThresValue().equals(""))) {
					if(threshold.getThresValuetype().equals("String") || threshold.getThresValuetype().equals("Int") || threshold.getThresValuetype().equals("Float") || threshold.getThresValuetype().equals("Boolean") || threshold.getThresValuetype().equals("Char")) {
						repositoryDao.saveThr(threshold);
						return threshold;
					}else {
						throw new StringValidationException("El tipo del valor no es: String, Int, Float, Boolean o Char");
					}
				}else {
					throw new StringValidationException("El nombre o el valor son vacios");
				}
			}else {
				throw new ElementDoesntExistException("La institución no existe");
			}
		}else {
			throw new ElementDoesntExistException("El threshold que quiere editar no existe");
		}
	}
	
	public Optional<Threshold> findThreshold(long id) {
		try {
			return repositoryDao.findById(id);
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public Iterable<Threshold> findAll() {
		return repositoryDao.findAll();
	}

	public ThresholdDao getRepositoryDao() {
		return repositoryDao;
	}

	public void setRepositoryDao(ThresholdDao repositoryDao) {
		this.repositoryDao = repositoryDao;
	}

	public InstitutionDao getInsRepositoryDao() {
		return insRepositoryDao;
	}

	public void setInsRepositoryDao(InstitutionDao insRepositoryDao) {
		this.insRepositoryDao = insRepositoryDao;
	}

	
	
}
