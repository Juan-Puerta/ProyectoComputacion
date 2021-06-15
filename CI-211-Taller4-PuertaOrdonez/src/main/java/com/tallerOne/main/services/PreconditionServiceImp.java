package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.daos.AutotransitionDao;
import com.tallerOne.main.daos.PreconditionDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Autotransition;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.repositories.AutotransicionesRepository;
import com.tallerOne.main.repositories.PrecondicionesRepository;

@Service
public class PreconditionServiceImp implements PreconditionService{

	private PreconditionDao repositoryDao;
	private AutotransitionDao autoRepositoryDao;
	
	@Autowired
	public PreconditionServiceImp(PreconditionDao repositoryDao, AutotransitionDao autoRepositoryDao) {
		super();
		this.repositoryDao = repositoryDao;
		this.autoRepositoryDao = autoRepositoryDao;
	}
	
	public Precondition save(Precondition precondition) {
		repositoryDao.savePre(precondition);
		return precondition;
	}

	public Precondition savePrecondition(Precondition precondition) throws ElementDoesntExistException, StringValidationException {
		if(autoRepositoryDao.findById(precondition.getAutotransition().getAutotranId()).isPresent()) {
			if(precondition.getPreconLogicaloperand().equals("OR") || precondition.getPreconLogicaloperand().equals("AND")) {
				repositoryDao.savePre(precondition);
				return precondition;
			}else {
				throw new StringValidationException("La precondicion no contiene el operador logico AND o OR");
			}
		}else {
			throw new ElementDoesntExistException("La autotransición no existe");
		}
	}
	
	public Precondition editPrecondition(Precondition precondition) throws ElementDoesntExistException, StringValidationException {
		if(repositoryDao.findById(precondition.getPreconId()).isPresent()) {
			if(autoRepositoryDao.findById(precondition.getAutotransition().getAutotranId()).isPresent()) {
				if(precondition.getPreconLogicaloperand().equals("OR") || precondition.getPreconLogicaloperand().equals("AND")) {
					repositoryDao.savePre(precondition);
					return precondition;
				}else {
					throw new StringValidationException("La precondicion no contiene el operador logico AND o OR");
				}
			}else {
				throw new ElementDoesntExistException("La autotransición no existe");
			}
		}else {
			throw new ElementDoesntExistException("La precondition la cual quiere editar no existe");
		}
	}
	
	@Override
	public Iterable<Precondition> findAll() {
		return repositoryDao.findAll();
	}
	
	public Optional<Precondition> findPrecondition(long id) {
		try {
			return repositoryDao.findById(id);
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public void deletePrecondition(Precondition precondition) {
		repositoryDao.deletePre(precondition);
	}

	public PreconditionDao getRepositoryDao() {
		return repositoryDao;
	}

	public void setRepositoryDao(PreconditionDao repositoryDao) {
		this.repositoryDao = repositoryDao;
	}

	public AutotransitionDao getAutoRepositoryDao() {
		return autoRepositoryDao;
	}

	public void setAutoRepositoryDao(AutotransitionDao autoRepositoryDao) {
		this.autoRepositoryDao = autoRepositoryDao;
	}

	
	
}
