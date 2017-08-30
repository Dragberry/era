package org.dragberry.era.web.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dragberry.era.web.exception.AccessDeniedException;
import org.dragberry.era.web.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessContoll {
	
	@Autowired
	private HttpServletRequest request;
	
	public void checkPermission(List<String> rolesToCheck) {
		rolesToCheck.forEach((role) -> {
			checkPermission(role);
		});
	}
	
	public void checkPermission(String roleTocheck) {
		if (!request.isUserInRole(roleTocheck)) {
			throw new AccessDeniedException();
		}
	}
	
	public JwtUser getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof JwtUser) {
				return (JwtUser) principal;
			}
		}
		throw new UnauthorizedException();
	}
	
	public void checkLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof JwtUser) {
				return;
			}
		}
		throw new UnauthorizedException();
	}
	
}
