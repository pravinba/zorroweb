package com.zorro.test.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.zorro.ZorrowebApplication;
import com.zorro.backend.persistence.domain.backend.Plan;
import com.zorro.backend.persistence.domain.backend.Role;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.persistence.domain.backend.UserRole;
import com.zorro.backend.persistence.repositories.PlanRepository;
import com.zorro.backend.persistence.repositories.RoleRepository;
import com.zorro.backend.persistence.repositories.UserRepository;
import com.zorro.enums.PlansEnum;
import com.zorro.enums.RolesEnum;
import com.zorro.utils.UserUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZorrowebApplication.class)
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest{

	@Rule 
	public TestName testName = new TestName();
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(userRepository);
		Assert.assertNotNull(roleRepository);
		
	}
	
	@Test
	public void testCreateNewPlan() throws Exception{
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan);
	}

	
	
	
	@Test
	public void testCreateNewRole() throws Exception{
		Role basicRole = createRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}

	
	
	@Test
	public void testDeleteUser() throws Exception{
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@zorromail.com";

		User basicUser = createUser(username, email);
		userRepository.delete(basicUser.getId());
	}

	
	@Test
	public void createNewUser() throws Exception {
		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@zorromail.com";

		User basicUser = createUser(username, email);

		User newlyCreatedUser = userRepository.findOne(basicUser.getId());
		//System.out.println(newlyCreatedUser.getFirstName());
		Assert.assertNotNull(newlyCreatedUser);
		Assert.assertTrue(newlyCreatedUser.getId()!=0);
		Assert.assertNotNull(newlyCreatedUser.getPlan());
		Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
		
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		
		for(UserRole ncr : newlyCreatedUserRoles ) {
			Assert.assertNotNull(ncr.getRole());
			Assert.assertNotNull(ncr.getRole().getId());
			
		}
		userRepository.delete(basicUser.getId());
		
	}
	
	@Test 
	public void testGetUserByEmail() throws Exception{
		User newlyCreatedUser = createUser(testName);
		User newlyFoundUser = userRepository.findByEmail(newlyCreatedUser.getEmail());
		
		Assert.assertNotNull(newlyFoundUser);
		Assert.assertNotNull(newlyFoundUser.getId());
	}
	
	 @Test
	    public void testUpdateUserPassword() throws Exception {
	        User user = createUser(testName);
	        Assert.assertNotNull(user);
	        Assert.assertNotNull(user.getId());

	        String newPassword = UUID.randomUUID().toString();

	        userRepository.updateUserPassword(user.getId(), newPassword);

	        user = userRepository.findOne(user.getId());
	        Assert.assertEquals(newPassword, user.getPassword());

	    }
	
	 
	/*
	protected User createUser(TestName testName) {
        return createUser(testName.getMethodName(), testName.getMethodName() + "@zorromail.com");
    }*/
	

}	


// OLD VERSION
///* Create a Plan and populate it to POJO*/
//Plan basicPlan = createPlan(PlansEnum.BASIC);
//		
///* Create a User - Populate plan & other attributes to the User POJO*/
//User basicUser = UserUtils.createBasicUser();
//basicUser.setPlan(basicPlan);
//basicUser.setUsername("IntgTestUser");
//basicUser.setEmail("IntgTestUser@email.com");
//
///* Create a Role*/
//Role basicRole = createRole(RolesEnum.BASIC);			
//
///* Add the role & user to a HashSet*/		
//Set<UserRole> userRoles = new HashSet<>();
//UserRole userRole = new UserRole(basicUser, basicRole);		
//userRoles.add(userRole);
//
///* Populate UserRole to User POJO */
//basicUser.getUserRoles().addAll(userRoles);
//
///*Start persisting in DB*/
//
///* 1 - Save Plan to Plan table */
//planRepository.save(basicPlan);
//
///* 2 - Save new Role in Roles table */
//for(UserRole ur : userRoles) {
//	roleRepository.save(ur.getRole());
//}
//
//	
///* 3 - Save User in User table */
//basicUser = userRepository.save(basicUser);

/* 4 - Pull the added user and assert if User/Plan/Role properties are read successfully */
//User newlyCreatedUser = userRepository.findByUsername(basicUser.getUsername());