package org.dragberry.era.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dragberry.era.web.security.UserAccountDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public UserAccountDetails login(HttpServletRequest request, Principal principal) {
		if (principal != null) {
			if (principal instanceof AbstractAuthenticationToken) {
				return (UserAccountDetails) ((AbstractAuthenticationToken) principal).getPrincipal();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/logged-user", method = RequestMethod.GET)
	@ResponseBody
	public UserAccountDetails getLoggedUser(HttpServletRequest request, Principal principal) {
		return login(request, principal);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(req, res, null);
	}

}
