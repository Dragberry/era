package org.dragberry.era.web.security;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Role;
import org.dragberry.era.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerSecurityService implements UserDetailsService {
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private static final Map<String, String> ROLES_HASH = new ConcurrentHashMap<>();
	
	private final UserAccountDao userAccountDao;
	
	@Autowired
	public CustomerSecurityService(UserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountDao.findByUsername(username);
		if (userAccount != null) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			for (Role role : userAccount.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(getSpringRole(role)));
			}
			return new UserAccountDetails(userAccount.getEntityKey(), userAccount.getUsername(), userAccount.getPassword(), authorities,
					userAccount.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
		}
		throw new UsernameNotFoundException(MessageFormat.format("The customer '%s' is not found", username));
	}

	private static final String getSpringRole(Role role) {
		return ROLES_HASH.computeIfAbsent(role.getRoleName(), r -> ROLE_PREFIX + role.getRoleName()); 
	}

}