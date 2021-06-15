package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Threshold;

public interface ThresholdDao {

	public void saveThr(Threshold thr);
	public void editThr(Threshold thr);
	public void deleteThr(Threshold thr);
	public Optional<Threshold> findById(long id) throws ElementDoesntExistException;
	public List<Threshold> findByName(String name) throws ElementDoesntExistException;
	public List<Threshold> findByValue(String value) throws ElementDoesntExistException;
	public List<Threshold> findByType(String type) throws ElementDoesntExistException;
	public List<Threshold> findAll();
	public List<Threshold> deleteAll();
	
}
