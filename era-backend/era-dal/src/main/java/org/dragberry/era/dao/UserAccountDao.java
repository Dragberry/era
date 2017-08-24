package org.dragberry.era.dao;

import org.dragberry.era.domain.UserAccount;

public interface UserAccountDao extends DataAccessObject<UserAccount, Long> {

	UserAccount findByUsername(String name);

}
