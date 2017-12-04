package com.zorro.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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

public abstract class AbstractIntegrationTest {
	
	@Autowired
	protected PlanRepository planRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected RoleRepository roleRepository;
	
	protected Plan createPlan(PlansEnum plansEnum) {
		// TODO Auto-generated method stub		
		return new Plan(plansEnum);
	}
	
	protected Role createRole(RolesEnum rolesEnum) {
		// TODO Auto-generated method stub
		return new Role(rolesEnum);
	}
	
	protected User createUser(String username, String email) {
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
