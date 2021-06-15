package com.tallerOne.main.daos;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tallerOne.main.model.Eventstatus;

@Repository
public class EstadoDaoImp implements EstadoDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void save(Eventstatus est) {
		// TODO Auto-generated method stub
		entityManager.persist(est);
	}

	@Override
	@Transactional
	public void edit(Eventstatus est) {
		// TODO Auto-generated method stub
		entityManager.merge(est);
	}

	@Override
	@Transactional
	public void delete(Eventstatus est) {
		// TODO Auto-generated method stub
		entityManager.remove(est);
	}

	@Override
	@Transactional
	public Optional<Eventstatus> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.of(entityManager.find(Eventstatus.class, id));
	}

	@Override
	@Transactional
	public List<Eventstatus> findAll() {
		// TODO Auto-generated method stub
		String jpql = "Select a FROM Eventstatus a";
		return entityManager.createQuery(jpql, Eventstatus.class).getResultList();
	}

	@Override
	@Transactional
	public List<Eventstatus> deleteAll() {
		// TODO Auto-generated method stub
		entityManager.createQuery("DELETE From Eventstatus").executeUpdate();
		return findAll();
	}

}
