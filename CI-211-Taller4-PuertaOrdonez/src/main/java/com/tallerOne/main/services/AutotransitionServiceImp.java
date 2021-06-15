package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tallerOne.main.daos.AutotransitionDao;
import com.tallerOne.main.daos.EstadoDao;
import com.tallerOne.main.daos.InstitutionDao;
import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.exceptions.StringValidationException;
import com.tallerOne.main.model.Autotransition;

@Service
public class AutotransitionServiceImp implements AutotransitionService{
	
	private AutotransitionDao repositoryDAO;
	private InstitutionDao insRepositoryDAO;
	private EstadoDao estEpiRepositoryDAO;
	
	//public AutotransitionServiceImp() {}
	
	@Autowired
	public AutotransitionServiceImp(AutotransitionDao repositoryDAO, InstitutionDao insRepositoryDAO, EstadoDao estEpiRepositoryDAO) {
		this.repositoryDAO = repositoryDAO;
		this.insRepositoryDAO = insRepositoryDAO;
		this.estEpiRepositoryDAO = estEpiRepositoryDAO;
	}
	
	public Autotransition save(Autotransition autotransition) {
		repositoryDAO.saveAuto(autotransition);
		return autotransition;
	}
	
	public Autotransition saveAutotransition(Autotransition autotransition) throws StringValidationException {
		if(!(autotransition.getAutotranName().equals(""))) {
			if(autotransition.getAutotranIsactive().equals("Y") || autotransition.getAutotranIsactive().equals("N")) {
				if(autotransition.getAutotranLogicaloperand().equals("AND") || autotransition.getAutotranLogicaloperand().equals("OR")) {
					repositoryDAO.saveAuto(autotransition);
					return autotransition;
				}else {
					throw new StringValidationException("Operador logico invalido");
				}
			}else {
				throw new StringValidationException("Activo invalido");
			}
		}else {
			throw new StringValidationException("Nombre de la transicion vacio");
		}
	}
	
	public Autotransition editAutotransition(Autotransition autotransition) throws ElementDoesntExistException, StringValidationException {
		if(repositoryDAO.findById(autotransition.getAutotranId()).isPresent()) {
			if(!(autotransition.getAutotranName().equals(""))) {
				if(autotransition.getAutotranIsactive().equals("Y") || autotransition.getAutotranIsactive().equals("N")) {
					if(autotransition.getAutotranLogicaloperand().equals("AND") || autotransition.getAutotranLogicaloperand().equals("OR")) {
						repositoryDAO.saveAuto(autotransition);
						return autotransition;
					}else {
						throw new StringValidationException("Operador logico invalido");
					}
				}else {
					throw new StringValidationException("Activo invalido");
				}
			}else {
				throw new StringValidationException("Nombre de la transicion vacio");
			}
		}else {
			throw new ElementDoesntExistException("La autotransicion que quiere editar no existe");
		}
	}
	
	public Iterable<Autotransition> findAll() {
		return repositoryDAO.findAll();
	}
	
	@Override
	@Transactional
	public Optional<Autotransition> findAutotransition(long autoId) {
		try {
			Optional<Autotransition> aux = repositoryDAO.findById(autoId);
			if(aux.isPresent()) {
				return repositoryDAO.findById(autoId);
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	@Transactional
	public void deleteAuto(long aut) throws ElementDoesntExistException{
		try {
			repositoryDAO.deleteAuto(repositoryDAO.findById(aut).get());
		}catch(Exception e) {
			throw new ElementDoesntExistException(e.getMessage());
		}
	}

	public AutotransitionDao getRepositoryDAO() {
		return repositoryDAO;
	}

	public void setRepositoryDAO(AutotransitionDao repositoryDAO) {
		this.repositoryDAO = repositoryDAO;
	}

	public InstitutionDao getInsRepositoryDAO() {
		return insRepositoryDAO;
	}

	public void setInsRepositoryDAO(InstitutionDao insRepositoryDAO) {
		this.insRepositoryDAO = insRepositoryDAO;
	}

	public EstadoDao getEstEpiRepositoryDAO() {
		return estEpiRepositoryDAO;
	}

	public void setEstEpiRepositoryDAO(EstadoDao estEpiRepositoryDAO) {
		this.estEpiRepositoryDAO = estEpiRepositoryDAO;
	}

	
	
	
	
}
