package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.UserAccount;

public interface UserAccountDao extends DataAccessObject<UserAccount, Long> {

	UserAccount findByUsername(String name);
	
	UserAccount findByEmail(String email);
	
	List<UserAccount> findAccountsForCustomer(Long customerKey);

}
