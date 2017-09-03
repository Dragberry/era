package org.dragberry.era.business.useraccount;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.useraccount.UserAccountTO;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountServiceBean implements UserAccountService {

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public List<UserAccountTO> getListForCustomer(Long customerKey) {
		return userAccountDao.findAccountsForCustomer(customerKey).stream().map(entity -> {
			UserAccountTO to = new UserAccountTO();
			to.setUsername(entity.getUsername());
			to.setId(entity.getEntityKey());
			to.setFirstName(entity.getFirstName());
			to.setLastName(entity.getLastName());
			to.setEmail(entity.getEmail());
			to.setRoles(entity.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
			return to;
		}).collect(Collectors.toList());
	}

}
