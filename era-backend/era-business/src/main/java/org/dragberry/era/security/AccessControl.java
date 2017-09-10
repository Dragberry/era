package org.dragberry.era.security;

import java.util.List;

public interface AccessControl {

	void checkPermission(List<String> rolesToCheck);

	void checkPermission(String roleTocheck);

	JwtUser getLoggedUser();

	void checkLoggedUser();
}
