package org.dragberry.era.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.dragberry.era.dao.BenefitDao;
import org.dragberry.era.dao.impl.AbstractDao;
import org.dragberry.era.domain.Benefit;

public abstract class BenefitDaoImpl<B extends Benefit> extends AbstractDao<B> implements BenefitDao<B>{

	public BenefitDaoImpl(Class<B> entityType) {
		super(entityType);
	}

	@Override
	public List<B> fetchActiveBenefits() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<B> cq = cb.createQuery(getEntityType());
		Root<B> root = cq.from(getEntityType());
		cq.where(cb.equal(root.get("deleted"), false));
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	@Override
	public List<B> fetchByKeys(List<Long> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptyList();
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<B> cq = cb.createQuery(getEntityType());
		Root<B> root = cq.from(getEntityType());
		cq.where(root.get("entityKey").in(keys));
		return getEntityManager().createQuery(cq).getResultList();
	}
}
