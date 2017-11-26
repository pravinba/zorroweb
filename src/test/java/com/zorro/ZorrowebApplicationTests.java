package com.zorro;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.zorro.web.i18n.I18NService;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
//@SpringApplicationConfiguration(classes=ZorrowebApplication.class)
@WebAppConfiguration
@SpringBootTest
public class ZorrowebApplicationTests {

	
	
	@Autowired
	private I18NService i18NService;
	@Test
	public void testMessageByLocaleService() throws Exception{
		String expectedResult = "ZorroWeb Spring & Stripe App";
		String messageId = "index.main.callout";
		String actual = i18NService.getMessage(messageId);
		Assert.assertEquals("The actual string does not match with expected string", expectedResult, actual);
		
		
		
	}

}
