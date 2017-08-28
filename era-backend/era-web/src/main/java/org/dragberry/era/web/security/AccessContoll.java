package org.dragberry.era.web.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dragberry.era.web.exception.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
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

}
