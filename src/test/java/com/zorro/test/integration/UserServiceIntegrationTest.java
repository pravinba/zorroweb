package com.zorro.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.zorro.ZorrowebApplication;
import com.zorro.backend.persistence.domain.backend.Plan;
import com.zorro.backend.persistence.domain.backend.Role;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.persistence.domain.backend.UserRole;
import com.zorro.backend.service.UserService;
import com.zorro.enums.PlansEnum;
import com.zorro.enums.RolesEnum;
import com.zorro.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZorrowebApplication.class)
public class UserServiceIntegrationTest {
	
	@Rule 
	public TestName testName = new TestName();
	
	@Autowired
	UserService userService;
	
	@Test
	public void testCreateNewUser() throws Exception{
		
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@zorromail.com";

		User basicUser = UserUtils.createBasicUser("ServiceUser","ServiceUser@email.com");
		
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
		
				
		User newUser = userService.CreateUser(basicUser, PlansEnum.BASIC, userRoles);
		
		Assert.notNull(newUser);
		Assert.notNull(newUser.getId());
		
	}

}
