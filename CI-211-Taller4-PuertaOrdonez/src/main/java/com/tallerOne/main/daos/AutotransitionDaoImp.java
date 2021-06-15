package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Autotransition;

@Repository
public class AutotransitionDaoImp implements AutotransitionDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void saveAuto(Autotransition auto) {
		// TODO Auto-generated method stub
		entityManager.persist(auto);
	}

	@Override
	@Transactional
	public void editAuto(Autotransition auto) {
		// TODO Auto-generated method stub
		entityManager.merge(auto);
	}

	@Override
	@Transactional
	public void deleteAuto(Autotransition auto) {
		// TODO Auto-generated method stub
		entityManager.remove(auto);
	}

	@Override
	@Transactional
	public Optional<Autotransition> findById(long id) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		if(entityManager.find(Autotransition.class, id)!=null) {
			return Optional.of(entityManager.find(Autotransition.class, id));
		}else {
			throw new ElementDoesntExistException("No existe Autotransition con ese ID");
		}
	}

	@Override
	@Transactional
	public List<Autotransition> findByName(String name) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Autotransition x WHERE x.autotranName = '"+name+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Autotransition> findByInsId(long id) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Autotransition x WHERE x.instInstId = '"+id+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Autotransition> findByIsActive(String isActive) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Autotransition x WHERE x.autotranIsactive = '"+isActive+"'").getResultList();
	}
	
	@Override
	@Transactional
	public List<Autotransition> findAll() {
		String jpql = "Select a FROM Autotransition a";
		return entityManager.createQuery(jpql, Autotransition.class).getResultList();
	}

	@Override
	@Transactional
	public List<Autotransition> deleteAll() {
		entityManager.createQuery("DELETE From Autotransition").executeUpdate();
		return findAll();
	}

}
