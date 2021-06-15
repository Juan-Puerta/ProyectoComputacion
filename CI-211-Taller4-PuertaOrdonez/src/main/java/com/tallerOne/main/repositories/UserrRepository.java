package com.tallerOne.main.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tallerOne.main.model.Userr;

public interface UserrRepository extends CrudRepository<Userr, Long>{

	public List<Userr> findByUserName(String userName);
	
}
