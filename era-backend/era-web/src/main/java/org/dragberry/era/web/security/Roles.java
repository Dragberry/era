package org.dragberry.era.web.security;

public interface Roles {
	
	interface Registrations {
		String VIEW = "ROLE_REGISTRATIONS_VIEW";
		String CREATE = "ROLE_REGISTRATIONS_CREATE";
		String UPDATE = "ROLE_REGISTRATIONS_UPDATE";
		String DELETE = "ROLE_REGISTRATIONS_DELETE";
	}

}
