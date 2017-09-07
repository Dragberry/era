package org.dragberry.era.business.useraccount;

import java.util.List;

import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.useraccount.UserAccountCRUDTO;
import org.dragberry.era.common.useraccount.UserAccountTO;

public interface UserAccountService {
	
	List<UserAccountTO> getListForCustomer(Long customerKey);

	ResultTO<UserAccountCRUDTO> create(UserAccountCRUDTO userAccount);

	ResultTO<UserAccountCRUDTO> delete(UserAccountCRUDTO userAccount);

	ResultTO<UserAccountCRUDTO> getDetails(UserAccountCRUDTO request);

	ResultTO<UserAccountCRUDTO> update(UserAccountCRUDTO userAccount);

}
