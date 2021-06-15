package com.tallerOne.main.services;

import java.util.Optional;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Userr;



public interface UserrService {

	public Userr saveUser(Userr user) throws ElementDoesntExistException;
	public Optional<Userr> editUserr(Userr user) throws ElementDoesntExistException;
	public void deleteUserr(long userId);
	public Optional<Userr> findUserr(Userr user);
	
}
