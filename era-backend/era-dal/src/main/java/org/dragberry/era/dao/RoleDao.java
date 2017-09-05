package org.dragberry.era.dao;

import org.dragberry.era.domain.Role;

public interface RoleDao extends DataAccessObject<Role, Long> {

	Role findByModuleAndAction(String module, String action);

}
