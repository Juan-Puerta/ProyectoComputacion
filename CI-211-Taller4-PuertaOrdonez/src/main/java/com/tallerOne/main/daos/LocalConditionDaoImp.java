package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerOne.main.exceptions.ElementDoesntExistException;
import com.tallerOne.main.model.Localcondition;

@Repository
public class LocalConditionDaoImp implements LocalConditionDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void saveLoc(Localcondition loc) {
		// TODO Auto-generated method stub
		entityManager.persist(loc);
	}

	@Override
	@Transactional
	public void editLoc(Localcondition loc) {
		// TODO Auto-generated method stub
		entityManager.merge(loc);
	}

	@Override
	@Transactional
	public void deleteLoc(Localcondition loc) {
		// TODO Auto-generated method stub
		entityManager.remove(loc);
	}

	@Override
	@Transactional
	public Optional<Localcondition> findById(long id) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		if(entityManager.find(Localcondition.class, id)!=null) {
			return Optional.of(entityManager.find(Localcondition.class, id));
		}else {
			throw new ElementDoesntExistException("No existe Localcondition con ese ID");
		}
	}

	@Override
	@Transactional
	public List<Localcondition> findByName(String name) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Localcondition x WHERE x.loconTablename = '"+name+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Localcondition> findByTypeValue(String typeValue) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Localcondition x WHERE x.loconValuetype = '"+typeValue+"'").getResultList();
	}

	@Override
	@Transactional
	public List<Localcondition> findByIdPrecondition(long id) throws ElementDoesntExistException {
		// TODO Auto-generated method stub
		return entityManager.createQuery("SELECT x FROM Localcondition x WHERE x.precondition.getPreconId() = '"+id+"'").getResultList();
	}
	
	@Override
	@Transactional
	public List<Localcondition> findAll() {
		String jpql = "Select a FROM Localcondition a";
		return entityManager.createQuery(jpql, Localcondition.class).getResultList();
	}

	@Override
	@Transactional
	public List<Localcondition> deleteAll() {
		entityManager.createQuery("DELETE From Localcondition").executeUpdate();
		return findAll();
	}

}
