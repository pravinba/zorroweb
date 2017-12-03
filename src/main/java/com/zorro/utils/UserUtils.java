package com.zorro.utils;

import com.zorro.backend.persistence.domain.backend.User;

public class UserUtils {
	
	private UserUtils () {
		throw new AssertionError("Non instantiable !");
	}
	
	public static User createBasicUser(String username, String email) {
		
		User user = new User();
		user.setUsername(username);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPassword("secret");
		user.setEmail(email);
		user.setPhoneNumber("1234567890");
		user.setCountry("GB");
		user.setDescription("A basic user");		
		user.setEnabled(true);
		user.setProfileImageUrl("http://bla@bla.com");
		
		return user;
	}

}
