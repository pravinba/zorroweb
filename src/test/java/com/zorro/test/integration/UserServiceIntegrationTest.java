package com.zorro.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zorro.ZorrowebApplication;
import com.zorro.backend.persistence.domain.backend.Role;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.persistence.domain.backend.UserRole;
import com.zorro.backend.service.UserService;
import com.zorro.enums.PlansEnum;
import com.zorro.enums.RolesEnum;
import com.zorro.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZorrowebApplication.class)
public class UserServiceIntegrationTest extends AbstractIntegrationTest{
	
	@Rule 
	public TestName testName = new TestName();
	
	@Autowired
	protected UserService userService;
	
	@Test
	public void testCreateNewUser() throws Exception{
		
		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		
	}

	protected User createUser(TestName testname) {
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@zorromail.com";

		User basicUser = UserUtils.createBasicUser(username,email);
		
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
				
		return userService.createUser(basicUser, PlansEnum.BASIC, userRoles);

	}

}
