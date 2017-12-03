package com.zorro.backend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zorro.backend.persistence.domain.backend.Plan;
import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.backend.persistence.domain.backend.UserRole;
import com.zorro.backend.persistence.repositories.PlanRepository;
import com.zorro.backend.persistence.repositories.RoleRepository;
import com.zorro.backend.persistence.repositories.UserRepository;
import com.zorro.enums.PlansEnum;

@Service
@Transactional(readOnly=true)
public class UserService {
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public User CreateUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {
		
		/* Encode the user password and set it in the POJO */
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		/* Create a plan & persist to DB */
		
		Plan plan = new Plan(plansEnum);
		if(!planRepository.exists(plansEnum.getId()))
		{
			plan = planRepository.save(plan);
		}		
			
		/* Set Role & Plan to User Object */
		user.setPlan(plan);
		
		for(UserRole usr : userRoles) {
			roleRepository.save(usr.getRole());
		}		
		user.getUserRoles().addAll(userRoles);
		
		user = userRepository.save(user);
		
		return user;
	}

}
