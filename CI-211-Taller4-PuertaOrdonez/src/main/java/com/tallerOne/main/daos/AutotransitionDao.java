package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Autotransition;

public interface AutotransitionDao {

	public void saveAuto(Autotransition auto);
	public void editAuto(Autotransition auto);
	public void deleteAuto(Autotransition auto);
	public Optional<Autotransition> findById(long id) throws ElementDoesntExistException;
	public List<Autotransition> findByName(String name) throws ElementDoesntExistException;
	public List<Autotransition> findByInsId(long id) throws ElementDoesntExistException;
	public List<Autotransition> findByIsActive(String isActive) throws ElementDoesntExistException;
	public List<Autotransition> findAll();
	public List<Autotransition> deleteAll();
	
}
