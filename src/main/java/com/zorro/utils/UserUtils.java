package com.zorro.utils;

import javax.servlet.http.HttpServletRequest;

import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.web.controllers.ForgotMyPasswordController;
import com.zorro.web.domain.frontend.BasicAccountPayload;

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
	
	

	/**
     * Builds and returns the URL to reset the user password.
     * @param request The Http Servlet Request
     * @param userId The user id
     * @param token The token
     * @return the URL to reset the user password.
     */
    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
        String passwordResetUrl =
                request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                        "?id=" +
                        userId +
                        "&token=" +
                        token;

        return passwordResetUrl;
    }
    
    public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T frontendPayload) {
        User user = new User();
        user.setUsername(frontendPayload.getUsername());
        user.setPassword(frontendPayload.getPassword());
        user.setFirstName(frontendPayload.getFirstName());
        user.setLastName(frontendPayload.getLastName());
        user.setEmail(frontendPayload.getEmail());
        user.setPhoneNumber(frontendPayload.getPhoneNumber());
        user.setCountry(frontendPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(frontendPayload.getDescription());

        return user;
    }

	public static <T extends BasicAccountPayload> User fromWebToDomainUser(T frontendPayload) {

		User user = new User();
		user.setUsername(frontendPayload.getUsername());
        user.setPassword(frontendPayload.getPassword());
        user.setFirstName(frontendPayload.getFirstName());
        user.setLastName(frontendPayload.getLastName());
        user.setEmail(frontendPayload.getEmail());
        user.setPhoneNumber(frontendPayload.getPhoneNumber());
        user.setCountry(frontendPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(frontendPayload.getDescription());
        
		
		return user;
	}

}
