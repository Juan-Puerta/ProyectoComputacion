package com.tallerOne.main.repositories;

import org.springframework.data.repository.CrudRepository;

import com.tallerOne.main.model.Person;


public interface PersonRepository extends CrudRepository<Person, Long>{

}
