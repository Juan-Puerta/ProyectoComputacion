package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.model.Eventstatus;

public interface EstadoDao {

	public void save(Eventstatus est);
	public void edit(Eventstatus est);
	public void delete(Eventstatus est);
	public Optional<Eventstatus> findById(long id);
	public List<Eventstatus> findAll();
	public List<Eventstatus> deleteAll();
	
}
