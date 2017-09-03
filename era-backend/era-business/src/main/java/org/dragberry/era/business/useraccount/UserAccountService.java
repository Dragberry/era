package org.dragberry.era.business.useraccount;

import java.util.List;

import org.dragberry.era.common.useraccount.UserAccountTO;

public interface UserAccountService {
	
	List<UserAccountTO> getListForCustomer(Long customerKey);

}
