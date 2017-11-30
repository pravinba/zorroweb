package com.zorro.test.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZorrowebApplication.class)
public class RepositoryIntegrationTest {

	private static final int BASIC_PLAN_ID = 1;
	private static final int BASIC_ROLE_ID = 1;
	
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
		Plan basicPlan = createBasicPlan();
		planRepository.save(basicPlan);
		Plan retrievedPlan = planRepository.findOne(BASIC_PLAN_ID);
		Assert.notNull(retrievedPlan);
	}

	private Plan createBasicPlan() {
		// TODO Auto-generated method stub
		Plan plan = new Plan();
		plan.setId(BASIC_PLAN_ID);
		plan.setName("Basic");
		return plan;
	}
	
	
	@Test
	public void testCreateNewRole() throws Exception{
		Role basicRole = createBasicRole();
		roleRepository.save(basicRole);
		Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);
		Assert.notNull(retrievedRole);
	}

	private Role createBasicRole() {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setId(BASIC_ROLE_ID);
		role.setName("ROLE_USER");
		return role;
	}
	
	
	public User createBasicUser() {		
		User user = new User();
		user.setUsername("basicUser");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPassword("secret");
		user.setEmail("me@test.com");
		user.setPhoneNumber("1234567890");
		user.setCountry("GB");
		user.setDescription("A basic user");		
		user.setEnabled(true);
		user.setProfileImageUrl("http://bla@bla.com");	
		
		return user;
	}
	
	@Test
	public void createNewUser() throws Exception {
		
	
		/* Create a Plan and populate it to POJO*/
		Plan basicPlan = createBasicPlan();
				
		/* Create a User - Populate plan & other attributes to the User POJO*/
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);
		
		/* Create a Role*/
		Role basicRole = createBasicRole();			
		
		/* Add the role & user to a HashSet*/		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setRole(basicRole);
		userRole.setUser(basicUser);
		userRoles.add(userRole);
		
		/* Populate UserRole to User POJO */
		basicUser.getUserRoles().addAll(userRoles);
		
		/*Start persisting in DB*/
		
		/* 1 - Save Plan to Plan table */
		planRepository.save(basicPlan);
		
		/* 2 - Save new Role in Roles table */
		for(UserRole ur : userRoles) {
			roleRepository.save(ur.getRole());
		}
		
		/* 3 - Save User in User table */
		basicUser = userRepository.save(basicUser);
		
		/* 4 - Pull the added user and assert if User/Plan/Role properties are read successfully */
		User newlyCreatedUser = userRepository.findByUsername(basicUser.getUsername());
//		User newlyCreatedUser = userRepository.findOne((int) basicUser.getId());
		System.out.println(newlyCreatedUser.getFirstName());
		Assert.notNull(newlyCreatedUser);
		Assert.isTrue(newlyCreatedUser.getId()!=0);
		Assert.notNull(newlyCreatedUser.getPlan());
		Assert.notNull(newlyCreatedUser.getPlan().getId());
		
		Set<UserRole> newlyCreatedUserRoles = newlyCreatedUser.getUserRoles();
		
		for(UserRole ncr : newlyCreatedUserRoles ) {
			Assert.notNull(ncr.getRole());
			Assert.notNull(ncr.getRole().getId());
		}
		
		
	}
	
}	
