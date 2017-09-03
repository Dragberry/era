package org.dragberry.era.business.useraccount;

import java.util.List;
import java.util.stream.Collectors;

import org.dragberry.era.common.useraccount.UserAccountTO;
import org.dragberry.era.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountServiceBean implements UserAccountService {

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public List<UserAccountTO> getListForCustomer(Long customerKey) {
		return userAccountDao.fetchList().stream().map(entity -> {
			UserAccountTO to = new UserAccountTO();
			to.setUsername(entity.getUsername());
			to.setId(entity.getEntityKey());
			to.setFirstName(entity.getFirstName());
			to.setLastName(entity.getLastName());
			to.setEmail(entity.getEmail());
			return to;
		}).collect(Collectors.toList());
	}

}
