package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.model.Eventstatus;
import com.tallerOne.main.model.Institution;

public interface EstadoService {

	public Eventstatus save(Eventstatus eventstatus);
	public Optional<Eventstatus> findEventstatus(long id);
	public Iterable<Eventstatus> findAll();
	
}
