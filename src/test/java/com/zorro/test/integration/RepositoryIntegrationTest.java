package com.zorro.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
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
import com.zorro.backend.persistence.repositories.PlanRepository;
import com.zorro.backend.persistence.repositories.RoleRepository;
import com.zorro.backend.persistence.repositories.UserRepository;
import com.zorro.enums.PlansEnum;
import com.zorro.enums.RolesEnum;
import com.zorro.utils.UserUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZorrowebApplication.class)
public class RepositoryIntegrationTest {

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
		Assert.notNull(planRepository);
		Assert.notNull(userRepository);
		Assert.notNull(roleRepository);
		
	}
	
	@Test
	public void testCreateNewPlan() throws Exception{
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(PlansEnum.BASIC.getId());
		Assert.notNull(retrievedPlan);
	}

	private Plan createPlan(PlansEnum plansEnum) {
		// TODO Auto-generated method stub		
		return new Plan(plansEnum);
	}
	
	
	@Test
	public void testCreateNewRole() throws Exception{
		Role basicRole = createRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());
		Assert.notNull(retrievedRole);
	}

	private Role createRole(RolesEnum rolesEnum) {
		// TODO Auto-generated method stub
		return new Role(rolesEnum);
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


		// OLD VERSION
//		/* Create a Plan and populate it to POJO*/
//		Plan basicPlan = createPlan(PlansEnum.BASIC);
//				
//		/* Create a User - Populate plan & other attributes to the User POJO*/
//		User basicUser = UserUtils.createBasicUser();
//		basicUser.setPlan(basicPlan);
//		basicUser.setUsername("IntgTestUser");
//		basicUser.setEmail("IntgTestUser@email.com");
//		
//		/* Create a Role*/
//		Role basicRole = createRole(RolesEnum.BASIC);			
//		
//		/* Add the role & user to a HashSet*/		
//		Set<UserRole> userRoles = new HashSet<>();
//		UserRole userRole = new UserRole(basicUser, basicRole);		
//		userRoles.add(userRole);
//		
//		/* Populate UserRole to User POJO */
//		basicUser.getUserRoles().addAll(userRoles);
//		
//		/*Start persisting in DB*/
//		
//		/* 1 - Save Plan to Plan table */
//		planRepository.save(basicPlan);
//		
//		/* 2 - Save new Role in Roles table */
//		for(UserRole ur : userRoles) {
//			roleRepository.save(ur.getRole());
//		}
//
//			
//		/* 3 - Save User in User table */
//		basicUser = userRepository.save(basicUser);
		
		/* 4 - Pull the added user and assert if User/Plan/Role properties are read successfully */
//		User newlyCreatedUser = userRepository.findByUsername(basicUser.getUsername());
		User basicUser = createUser(username, email);

		User newlyCreatedUser = userRepository.findOne(basicUser.getId());
		//System.out.println(newlyCreatedUser.getFirstName());
		Assert.notNull(newlyCreatedUser);
		Assert.isTrue(newlyCreatedUser.getId()!=0);
		Assert.notNull(newlyCreatedUser.getPlan());
		Assert.notNull(newlyCreatedUser.getPlan().getId());
		
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		
		for(UserRole ncr : newlyCreatedUserRoles ) {
			Assert.notNull(ncr.getRole());
			Assert.notNull(ncr.getRole().getId());
			
		}
		userRepository.delete(basicUser.getId());
		
	}
	
	private User createUser(String username, String email) {
		/* Create a Plan and populate it to POJO*/
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		
		/* Save Plan to Plan table */
		planRepository.save(basicPlan);
				
		/* Create a User - Populate plan & other attributes to the User POJO*/
		User basicUser = UserUtils.createBasicUser(username, email);
		basicUser.setPlan(basicPlan);
		
		
		/* Create a Role*/
		Role basicRole = createRole(RolesEnum.BASIC);			
		
		/* Save Role to table */
		roleRepository.save(basicRole);
		
		/* Add the role & user to a HashSet*/		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);		
		userRoles.add(userRole);
		
		/* Populate UserRole to User POJO */
		basicUser.getUserRoles().addAll(userRoles);
		basicUser = userRepository.save(basicUser);
		return basicUser;
	}
	

}	


