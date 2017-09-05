package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.RoleDao;
import org.dragberry.era.domain.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
	
	private static final String MODULE = "module";
	private static final String ACTION = "action";

	public RoleDaoImpl() {
		super(Role.class);
	}
	
	@Override
	public Role findByModuleAndAction(String module, String action) {
		return getEntityManager().createNamedQuery(Role.FIND_BY_MODULE_AND_ACTION_QUERY, getEntityType())
				.setParameter(MODULE, module)
				.setParameter(ACTION, action)
				.getSingleResult();
	}

}
