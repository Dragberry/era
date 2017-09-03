package org.dragberry.era.dao.impl;

import java.util.List;

import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountDaoImpl extends AbstractDao<UserAccount> implements UserAccountDao {

	private static final String USERNAME = "username";
	
	public UserAccountDaoImpl() {
		super(UserAccount.class);
	}

	@Override
	public UserAccount findByUsername(String name) {
		List<UserAccount> result = getEntityManager().createNamedQuery(UserAccount.FIND_BY_USERNAME_QUERY, getEntityType())
			.setParameter(USERNAME, name)
			.getResultList();
		return result.size() > 0  ? result.get(0) : null;
	}
	
	@Override
	public List<UserAccount> findAccountsForCustomer(Long customerKey) {
		return getEntityManager().createNamedQuery(UserAccount.FIND_ACCOUNTS_FOR_CUSTOMER_QUERY, getEntityType())
				.setParameter(CUSTOMER_KEY, customerKey)
				.getResultList();
	}
}
