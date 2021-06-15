package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Precondition;

public interface PreconditionDao {

	public void savePre(Precondition pre);
	public void editPre(Precondition pre);
	public void deletePre(Precondition pre);
	public List<Precondition> findAll();
	public List<Precondition> deleteAll();
	public Optional<Precondition> findById(long id) throws ElementDoesntExistException;
	
}
