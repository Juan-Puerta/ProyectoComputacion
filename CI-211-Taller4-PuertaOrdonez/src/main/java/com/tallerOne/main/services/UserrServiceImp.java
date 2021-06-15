package com.tallerOne.main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Userr;
import com.tallerOne.main.repositories.UserrRepository;


@Service
public class UserrServiceImp implements UserrService{

private UserrRepository userrRep;
	
	@Autowired
	public UserrServiceImp(UserrRepository userRep) {
		this.userrRep = userRep;
	}

	@Override
	public Userr saveUser(Userr user) throws ElementDoesntExistException {
		return userrRep.save(user);
	}

	@Override
	public Optional<Userr> editUserr(Userr user) throws ElementDoesntExistException {
		if(userrRep.findById(user.getUserId()) != null) {
			Userr act=userrRep.findById(user.getUserId()).get();
			userrRep.save(act);
			return userrRep.findById(user.getUserId());
		}else {
			throw new ElementDoesntExistException("");
		}
	}

	@Override
	public void deleteUserr(long userId) {
		userrRep.deleteById(userId);
	}

	@Override
	public Optional<Userr> findUserr(Userr user) {
		return userrRep.findById(user.getUserId());
	}
	
	
}
