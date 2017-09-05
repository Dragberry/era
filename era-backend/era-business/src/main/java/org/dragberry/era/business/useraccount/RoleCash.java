package org.dragberry.era.business.useraccount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dragberry.era.common.useraccount.RoleHolderTO;
import org.dragberry.era.dao.RoleDao;
import org.dragberry.era.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleCash {
	
	@Autowired
	private RoleDao roleDao;
	
	private final Map<String, Role> CASH = new ConcurrentHashMap<>();
	
	public Role getRoleByFullName(String fullRoleName) {
		return CASH.computeIfAbsent(fullRoleName, key -> {
			String[] roleParts = key.split("_");
			return roleDao.findByModuleAndAction(roleParts[1], roleParts[2]);
		});
	}
	
	public Role getRole(RoleHolderTO roleHolder) {
		return getRoleByFullName(roleHolder.getRole());
	}

}
