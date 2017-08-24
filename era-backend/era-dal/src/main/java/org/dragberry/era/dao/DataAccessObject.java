package org.dragberry.era.dao;

import java.io.Serializable;
import java.util.List;

import org.dragberry.era.domain.AbstractEntity;

/**
 * 
 * @author Maksim Drahun
 *
 */
public interface DataAccessObject<E extends AbstractEntity, ID extends Serializable> {

	E findOne(ID entityKey);
	
	List<E> fetchList();
	
	Long count();
	
	E create(E entity);
	
	E update(E entity);
	
	E delete(ID entityKey);
	
}
