package com.zorro.test.unit;

import java.lang.reflect.Type;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.zorro.backend.persistence.domain.backend.User;
import com.zorro.utils.UserUtils;
import com.zorro.web.controllers.ForgotMyPasswordController;
import com.zorro.web.domain.frontend.BasicAccountPayload;

import uk.co.jemos.podam.api.ClassInfoStrategy;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


import org.junit.Assert;
import org.junit.Before;

public class UserUtilsUnitTest {
	
	private MockHttpServletRequest mockHttpServletRequest;
	private PodamFactory podamFactory; 
	
	
	@Before
	public void init()
	{
		mockHttpServletRequest = new MockHttpServletRequest();
		podamFactory = new PodamFactoryImpl();
	}
	
	@Test
	public void testPasswordResetEmailUrlConstruction() throws Exception{
		mockHttpServletRequest.setServerPort(8090);
		String token = UUID.randomUUID().toString();
		long userId = 123456;
		
		String expectedUrl = "http://localhost:8090" + ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" 
								+ userId + "&token=" + token;
		String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest,userId,token);
		Assert.assertEquals(expectedUrl, actualUrl);
	}
	
	@Test
    public void mapWebUserToDomainUser() {

        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail("me@example.com");

        User user = UserUtils.fromWebUserToDomainUser(webUser);
        Assert.assertNotNull(user);

        Assert.assertEquals(webUser.getUsername(), user.getUsername());
        Assert.assertEquals(webUser.getPassword(), user.getPassword());
        Assert.assertEquals(webUser.getFirstName(), user.getFirstName());
        Assert.assertEquals(webUser.getLastName(), user.getLastName());
        Assert.assertEquals(webUser.getEmail(), user.getEmail());
        Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(webUser.getCountry(), user.getCountry());
        Assert.assertEquals(webUser.getDescription(), user.getDescription());

    }
}
