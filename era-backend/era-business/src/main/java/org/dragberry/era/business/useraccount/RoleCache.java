package org.dragberry.era.business.useraccount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dragberry.era.common.useraccount.RoleHolderTO;
import org.dragberry.era.dao.RoleDao;
import org.dragberry.era.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleCache {

	private static final String ROLE_PREFIX = "ROLE_";
	private static final String UNDERSCORE = "_";

	@Autowired
	private RoleDao roleDao;

	private final Map<String, Role> CACHE = new ConcurrentHashMap<>();
	private final Map<Role, String> REVERSE_CACHE = new ConcurrentHashMap<>();

	public Role getRoleByFullName(String fullRoleName) {
		return CACHE.computeIfAbsent(fullRoleName, key -> {
			String[] roleParts = key.split(UNDERSCORE);
			return roleDao.findByModuleAndAction(roleParts[1], roleParts[2]);
		});
	}

	public Role getRole(RoleHolderTO roleHolder) {
		return getRoleByFullName(roleHolder.getRole());
	}

	public String getRoleName(Role role) {
		return REVERSE_CACHE.computeIfAbsent(role, key -> {
			return ROLE_PREFIX + role.getModule() + UNDERSCORE + role.getAction();
		});
	}

}
