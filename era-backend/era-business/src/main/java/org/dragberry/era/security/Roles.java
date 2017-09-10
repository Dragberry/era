package org.dragberry.era.security;

public interface Roles {
	
	interface Registrations {
		String VIEW = "ROLE_REGISTRATIONS_VIEW";
		String CREATE = "ROLE_REGISTRATIONS_CREATE";
		String UPDATE = "ROLE_REGISTRATIONS_UPDATE";
		String DELETE = "ROLE_REGISTRATIONS_DELETE";
	}
	
	interface UserAccounts {
		String VIEW = "ROLE_USERACCOUNTS_VIEW";
		String CREATE = "ROLE_USERACCOUNTS_CREATE";
		String UPDATE = "ROLE_USERACCOUNTS_UPDATE";
		String DELETE = "ROLE_USERACCOUNTS_DELETE";
	}

}
