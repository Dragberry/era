package org.dragberry.era.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SRASSecurityContext {

	private SRASSecurityContext() {}
	
	public static UserAccountDetails getLoggedCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserAccountDetails) {
				return (UserAccountDetails) principal;
			}
		}
		return null;
	}
	
	public static Long getLoggedCustomerKey() {
		UserAccountDetails details = getLoggedCustomer();
		return details == null ? UserAccountDetails.ANONYMOUS_ID : details.getId();
	}
}
