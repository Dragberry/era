package org.dragberry.era.business.audit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dragberry.era.dao.AuditRecordDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.AuditRecord;
import org.dragberry.era.domain.AuditRecord.Action;
import org.dragberry.era.domain.Role;
import org.dragberry.era.domain.UserAccount;
import org.dragberry.era.security.AccessControl;
import org.dragberry.era.security.JwtUser;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.container.SetChange;
import org.javers.core.json.JsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuditRecord.class)
public class AuditServiceBeanTest {
	
	private static final Logger LOG = LogManager.getLogger(AuditServiceBeanTest.class);

	@Mock
	private AuditRecordDao auditRecordDao;
	@Mock
	private AccessControl accessControl;
	@Mock
	private UserAccountDao userAccountDao;

	@InjectMocks
	private AuditServiceBean auditServiceBean = new AuditServiceBean();

	private Long uaEntityKey = 1000l;

	private JwtUser user;

	private UserAccount account;

	@Before
	public void setup() {
		user = new JwtUser(uaEntityKey, 1L, "user", "f", "l", "m", "p", Collections.emptyList(), true,
				LocalDateTime.now());
		account = new UserAccount();
		account.setEntityKey(uaEntityKey);
	}

	@Test
	public void testEntityDiff() throws Exception {
		UserAccount old = new UserAccount();
		old.setEntityKey(1L);
		old.setUsername("oldName");
		Role role1 = new Role();
		role1.setEntityKey(1L);
		role1.setModule("M1");
		role1.setModule("A1");
		Role role2 = new Role();
		role2.setEntityKey(2L);
		role2.setModule("M2");
		role2.setModule("A2");
		old.setRoles(new HashSet<Role>(Arrays.asList(role1, role2)));
		UserAccount upd = new UserAccount();
		upd.setEntityKey(1L);
		upd.setUsername("updName");
		old.setRoles(new HashSet<Role>(Arrays.asList(role2)));
		
		Javers javers = JaversBuilder.javers().build();
		JsonConverter converter = javers.getJsonConverter();
		Diff diff = javers.compare(old, upd);
		LOG.info(converter.toJson(diff));
	}

}
