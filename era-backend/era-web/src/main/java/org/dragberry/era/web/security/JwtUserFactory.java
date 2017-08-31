package org.dragberry.era.web.security;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.dragberry.era.domain.Role;
import org.dragberry.era.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

	private static final String ROLE_PREFIX = "ROLE_";
	
	private final static Map<Role, SimpleGrantedAuthority> ROLES_CASH = new ConcurrentHashMap<>();
    
	private JwtUserFactory() {
    }

    public static JwtUser create(UserAccount user) {
        return new JwtUser(
                user.getEntityKey(),
                user.getCustomer().getEntityKey(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> authorities) {
        return authorities.stream().map(
        		authority -> ROLES_CASH.computeIfAbsent(authority, 
                		role -> new SimpleGrantedAuthority(ROLE_PREFIX + authority.getModule() + authority.getAction())))
                .collect(Collectors.toList());
    }
}
