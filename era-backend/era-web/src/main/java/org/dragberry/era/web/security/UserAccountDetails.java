package org.dragberry.era.web.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserAccountDetails extends User {

	private static final long serialVersionUID = -2611246446268300417L;
	
	public static final String ANONYMOUS_ROLE = "ANONYMOUS";
	public static final Long ANONYMOUS_ID = 0L;
	
	private Long id;
	
	private Set<String> roles;

	public UserAccountDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Set<String> roles) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.roles = roles;
	}
	
	public UserAccountDetails(Long id, String customerName, String password, List<GrantedAuthority> authorities, Set<String> roles) {
		this(id, customerName, password, true, true, true, true, authorities, roles);
	}

	public Long getId() {
		return id;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

}