package org.dragberry.era.business.useraccount;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.useraccount.UserAccountCreateTO;
import org.dragberry.era.common.useraccount.UserAccountTO;

public interface UserAccountService {
	
	List<UserAccountTO> getListForCustomer(Long customerKey);

	ResultTO<UserAccountCreateTO> create(UserAccountCreateTO userAccount);

}
