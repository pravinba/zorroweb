package com.zorro.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	private static final String LOGIN_VIEW_NAME = "user/login";
	
	@RequestMapping("/login")
	public String login()
	{
		return LOGIN_VIEW_NAME;
	}

}