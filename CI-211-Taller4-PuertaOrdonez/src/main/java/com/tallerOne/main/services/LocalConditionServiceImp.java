package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.daos.LocalConditionDao;
import com.tallerOne.main.daos.PreconditionDao;
import com.tallerOne.main.daos.ThresholdDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;
import com.tallerOne.main.repositories.CondicionesLocalesRepository;
import com.tallerOne.main.repositories.PrecondicionesRepository;
import com.tallerOne.main.repositories.ThresholdsRepository;

@Service
public class LocalConditionServiceImp implements LocalConditionService{

	private LocalConditionDao repositoryDao;
	private PreconditionDao preRepositoryDao;
	private ThresholdDao thrRepositoryDao;
	
	@Autowired
	public LocalConditionServiceImp(LocalConditionDao repositoryDao, PreconditionDao preRepositoryDao,
			ThresholdDao thrRepositoryDao) {
		super();
		this.repositoryDao = repositoryDao;
		this.preRepositoryDao = preRepositoryDao;
		this.thrRepositoryDao = thrRepositoryDao;
	}
	
	public Localcondition save(Localcondition co) {
		repositoryDao.saveLoc(co);
		return co;
	}
	
	public Localcondition saveLocalCondition(Localcondition localcondition) throws StringValidationException, ElementDoesntExistException {
		
		if((preRepositoryDao.findById(localcondition.getPrecondition().getPreconId()).isPresent()) && (thrRepositoryDao.findById(localcondition.getThreshold().getThresId()).isPresent())) {
			if(!localcondition.getLoconKeycolumn().contains(" ") && !localcondition.getLoconTablename().contains(" ") && !localcondition.getLoconQuerystring().contains(" ")) {
				if(localcondition.getLoconOperator().equals("<>") || localcondition.getLoconOperator().equals("< >") || localcondition.getLoconOperator().equals("==")) {
					repositoryDao.saveLoc(localcondition);
					return localcondition;
				}else {
					throw new StringValidationException("El operador no es <> o < > o ==");
				}
			}else {
				throw new StringValidationException("La tabla o la clave de columna o el nombre contienen espacios");
			}
		}else {
			throw new ElementDoesntExistException("No existe la precondición o el threshold");
		}
		
	}

	public Localcondition editLocalCondition(Localcondition localcondition) throws ElementDoesntExistException, StringValidationException {
		
		if(repositoryDao.findById(localcondition.getLoconId()).isPresent()) {
			if(preRepositoryDao.findById(localcondition.getPrecondition().getPreconId()).isPresent() && thrRepositoryDao.findById(localcondition.getThreshold().getThresId()).isPresent()) {
				if(!localcondition.getLoconKeycolumn().contains(" ") && !localcondition.getLoconTablename().contains(" ") && !localcondition.getLoconQuerystring().contains(" ")) {
					if(localcondition.getLoconOperator().equals("<>") || localcondition.getLoconOperator().equals("< >") || localcondition.getLoconOperator().equals("==")) {
						repositoryDao.saveLoc(localcondition);
						return localcondition;
					}else {
						throw new StringValidationException("El operador no es <> o < > o ==");
					}
				}else {
					throw new StringValidationException("la tabla o la clave de columna o el nombre contienen espacios");
				}
			}else {
				throw new ElementDoesntExistException("No existe el localcondition o el threshold");
			}
		}else {
			throw new ElementDoesntExistException("El objeto el cual quiere editar no existe");
		}
		
	}
	
	@Override
	public Iterable<Localcondition> findAll() {
		return repositoryDao.findAll();
	}
	
	@Override
	public Optional<Localcondition> findLocalcondition(long id) {
		try {
			return repositoryDao.findById(id);
		} catch (ElementDoesntExistException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public void deleteLocalcondition(Localcondition localcondition) {
		repositoryDao.deleteLoc(localcondition);
	}

	public LocalConditionDao getRepositoryDao() {
		return repositoryDao;
	}

	public void setRepositoryDao(LocalConditionDao repositoryDao) {
		this.repositoryDao = repositoryDao;
	}

	public PreconditionDao getPreRepositoryDao() {
		return preRepositoryDao;
	}

	public void setPreRepositoryDao(PreconditionDao preRepositoryDao) {
		this.preRepositoryDao = preRepositoryDao;
	}

	public ThresholdDao getThrRepositoryDao() {
		return thrRepositoryDao;
	}

	public void setThrRepositoryDao(ThresholdDao thrRepositoryDao) {
		this.thrRepositoryDao = thrRepositoryDao;
	}

	
	
}
