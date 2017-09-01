package org.dragberry.era.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.dragberry.era.dao.DataAccessObject;
import org.dragberry.era.domain.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDao<E extends AbstractEntity> implements DataAccessObject<E, Long> {
	
	public static final String LIKE = "%";
	
	protected static final String ENTITY_KEY = "entityKey";
	protected static final String CUSTOMER_KEY = "customerKey";
	
	private final String FETCH_LIST_QUERY;
	private final String COUNT_QUERY;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private final Class<E> entityType;
	
	public AbstractDao(Class<E> entityType) {
		this.entityType = entityType;
		this.FETCH_LIST_QUERY = "FROM " + getEntityName();
		this.COUNT_QUERY = "SELECT COUNT(e) FROM " + getEntityName() + " e";
	}
	
	protected String getEntityName() {
		return entityType.getName();
	}
	
	protected Class<E> getEntityType() {
		return entityType;
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public E findOne(Long entityKey) {
		return entityManager.find(entityType, entityKey);
	}
	
	@Override
	public List<E> fetchList() {
		return getEntityManager().createQuery(FETCH_LIST_QUERY, entityType).getResultList();
	}
	
	@Override
	public Long count() {
		return getEntityManager().createQuery(COUNT_QUERY, Long.class).getSingleResult();
	}
	
	@Override
	@Transactional
	public E create(E entity) {
		entityManager.persist(entity);
		return entity; 
	}
	
	@Override
	@Transactional
	public E update(E entity) {
		return entityManager.merge(entity);
	}
	
	@Override
	@Transactional
	public E delete(Long entityKey) {
		E entity = entityManager.find(entityType, entityKey);
		entityManager.remove(entity);
		return entity;
	}
	
	protected <T> TypedQuery<T> prepateFetchQuery(String hql, Map<String, Object> params, Class<T> entityType) {
		TypedQuery<T> typedQuery = getEntityManager().createQuery(hql, entityType);
		params.entrySet().forEach(entry -> {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		});
		return typedQuery;
	}
	
	protected static String forLike(String exp) {
		return exp + LIKE;
	}
	
}
