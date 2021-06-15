package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Localcondition;
import com.tallerOne.main.model.Precondition;

@Repository
public class PreconditionDaoImp implements PreconditionDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void savePre(Precondition pre) {
		// TODO Auto-generated method stub
		entityManager.persist(pre);
	}

	@Override
	@Transactional
	public void editPre(Precondition pre) {
		// TODO Auto-generated method stub
		entityManager.merge(pre);
	}

	@Override
	@Transactional
	public void deletePre(Precondition pre) {
		// TODO Auto-generated method stub
		entityManager.remove(pre);
	}
	
	@Override
	@Transactional
	public List<Precondition> findAll() {
		String jpql = "Select a FROM Precondition a";
		return entityManager.createQuery(jpql, Precondition.class).getResultList();
	}

	@Override
	@Transactional
	public List<Precondition> deleteAll() {
		entityManager.createQuery("DELETE From Precondition").executeUpdate();
		return findAll();
	}
	
	@Override
	@Transactional
	public Optional<Precondition> findById(long id) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		if(entityManager.find(Precondition.class, id)!=null) {
			return Optional.of(entityManager.find(Precondition.class, id));
		}else {
			throw new ElementDoesntExistException("No existe Localcondition con ese ID");
		}
	}

}
