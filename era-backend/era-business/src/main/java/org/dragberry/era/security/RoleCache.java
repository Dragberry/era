package org.dragberry.era.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dragberry.era.common.useraccount.RoleHolderTO;
import org.dragberry.era.dao.RoleDao;
import org.dragberry.era.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleCache {

	private static final String UNDERSCORE = "_";

	@Autowired
	private RoleDao roleDao;

	private final Map<String, Role> CACHE = new ConcurrentHashMap<>();
	private final Map<Role, SimpleGrantedAuthority> ROLES_GA_CACHE = new ConcurrentHashMap<>();

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
		return getGrantedAuthority(role).getAuthority();
	}
	
	public GrantedAuthority getGrantedAuthority(Role role) {
        return ROLES_GA_CACHE.computeIfAbsent(role, key -> new SimpleGrantedAuthority(role.toString()));
    }

}
