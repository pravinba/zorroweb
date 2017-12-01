package com.zorro.utils;

import com.zorro.backend.persistence.domain.backend.User;

public class UsersUtil {
	
	private UsersUtil () {
		throw new AssertionError("Non instantiable !");
	}
	
	public static User createBasicUser() {
		
		User user = new User();
		user.setUsername("basicUser");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPassword("secret");
		user.setEmail("me@example.com");
		user.setPhoneNumber("1234567890");
		user.setCountry("GB");
		user.setDescription("A basic user");		
		user.setEnabled(true);
		user.setProfileImageUrl("http://bla@bla.com");
		
		return user;
	}

}
