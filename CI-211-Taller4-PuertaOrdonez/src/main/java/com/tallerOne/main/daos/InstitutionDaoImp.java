package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerOne.main.model.Institution;

@Repository
public class InstitutionDaoImp implements InstitutionDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void saveIns(Institution ins) {
		// TODO Auto-generated method stub
		entityManager.persist(ins);
	}

	@Override
	@Transactional
	public void editIns(Institution ins) {
		// TODO Auto-generated method stub
		entityManager.merge(ins);
	}

	@Override
	@Transactional
	public void deleteIns(Institution ins) {
		// TODO Auto-generated method stub
		entityManager.remove(ins);
	}

	@Override
	@Transactional
	public Optional<Institution> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.of(entityManager.find(Institution.class, id));
	}

	@Override
	@Transactional
	public List<Institution> findAll() {
		// TODO Auto-generated method stub
		String jpql = "Select a FROM Institution a";
		return entityManager.createQuery(jpql, Institution.class).getResultList();
	}

	@Override
	@Transactional
	public List<Institution> deleteAll() {
		// TODO Auto-generated method stub
		entityManager.createQuery("DELETE From Institution").executeUpdate();
		return findAll();
	}

}
