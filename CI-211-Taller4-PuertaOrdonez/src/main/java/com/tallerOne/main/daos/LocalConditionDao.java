package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Localcondition;

public interface LocalConditionDao {

	public void saveLoc(Localcondition loc);
	public void editLoc(Localcondition loc);
	public void deleteLoc(Localcondition loc);
	public Optional<Localcondition> findById(long id) throws ElementDoesntExistException;
	public List<Localcondition> findByName(String name) throws ElementDoesntExistException;
	public List<Localcondition> findByTypeValue(String typeValue) throws ElementDoesntExistException;
	public List<Localcondition> findByIdPrecondition(long id) throws ElementDoesntExistException;
	public List<Localcondition> findAll();
	public List<Localcondition> deleteAll();
	
}
