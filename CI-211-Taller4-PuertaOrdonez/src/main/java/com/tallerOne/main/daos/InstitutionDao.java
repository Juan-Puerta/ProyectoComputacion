package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.model.Institution;

public interface InstitutionDao {

	public void saveIns(Institution ins);
	public void editIns(Institution ins);
	public void deleteIns(Institution ins);
	public Optional<Institution> findById(long id);
	public List<Institution> findAll();
	public List<Institution> deleteAll();
	
}
