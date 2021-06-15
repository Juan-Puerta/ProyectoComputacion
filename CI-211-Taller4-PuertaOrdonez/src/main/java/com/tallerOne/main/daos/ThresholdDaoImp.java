package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Threshold;

@Repository
public class ThresholdDaoImp implements ThresholdDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void saveThr(Threshold thr) {
		// TODO Auto-generated method stub
		entityManager.persist(thr);
	}

	@Override
	@Transactional
	public void editThr(Threshold thr) {
		// TODO Auto-generated method stub
		entityManager.merge(thr);
	}

	@Override
	@Transactional
	public void deleteThr(Threshold thr) {
		// TODO Auto-generated method stub
		entityManager.remove(thr);
	}

	@Override
	@Transactional
	public Optional<Threshold> findById(long id) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		if(entityManager.find(Threshold.class, id)!=null) {
			return Optional.of(entityManager.find(Threshold.class, id));
		}else {
			throw new ElementDoesntExistException("No existe Autotransition con ese ID");
		}
	}

	@Override
	@Transactional
	public List<Threshold> findByName(String name) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Threshold x WHERE x.thresName = '"+name+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Threshold> findByValue(String value) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Threshold x WHERE x.thresValue = '"+value+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Threshold> findByType(String type) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Threshold x WHERE x.thresValuetype = '"+type+"'").getResultList();
	}
	
	@Override
	@Transactional
	public List<Threshold> findAll() {
		String jpql = "Select a FROM Threshold a";
		return entityManager.createQuery(jpql, Threshold.class).getResultList();
	}

	@Override
	@Transactional
	public List<Threshold> deleteAll() {
		entityManager.createQuery("DELETE From Threshold").executeUpdate();
		return findAll();
	}

}
