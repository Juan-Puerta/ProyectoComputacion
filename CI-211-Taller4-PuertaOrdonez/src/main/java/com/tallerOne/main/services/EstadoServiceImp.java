package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.daos.EstadoDao;
import com.tallerOne.main.model.Eventstatus;
import com.tallerOne.main.repositories.EstadosEpidemiologicosRepository;

@Service
public class EstadoServiceImp implements EstadoService{
	
	private EstadoDao repositoryDao;

	@Autowired
	public EstadoServiceImp(EstadoDao repositoryDao) {
		super();
		this.repositoryDao = repositoryDao;
	}
	
	public Eventstatus save(Eventstatus eventstatus) {
		repositoryDao.save(eventstatus);
		return eventstatus;
	}
	
	public Optional<Eventstatus> findEventstatus(long id) {
		return repositoryDao.findById(id);
	}
	
	public Iterable<Eventstatus> findAll() {
		return repositoryDao.findAll();
	}

}
