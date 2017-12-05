package com.zorro.test.unit;

import java.util.UUID;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;

import com.zorro.utils.UserUtils;
import com.zorro.web.controllers.ForgotMyPasswordController;

import org.junit.Assert;
import org.junit.Before;

public class UserUtilsUnitTest {
	
	private MockHttpServletRequest mockHttpServletRequest;
	
	@Before
	public void init()
	{
		mockHttpServletRequest.setServerPort(8090);
		String token = UUID.randomUUID().toString();
		long userId = 123456;
		
		String expectedUrl = "http://localhost:8090" + ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" 
								+ userId + "&token=" + token;
		String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest,userId,token);
		Assert.assertEquals(expectedUrl, actualUrl);
	}
}
